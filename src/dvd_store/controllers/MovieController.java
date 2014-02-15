package dvd_store.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import dvd_store.entities.Crew;
import dvd_store.entities.Movie;
import dvd_store.service.CrewService;
import dvd_store.service.MovieService;

@ManagedBean
@ViewScoped
public class MovieController implements Serializable {

	private static final long serialVersionUID = -988234378830629291L;
	// http://stackoverflow.com/a/10691832/281545
	private Movie movie;
	@EJB
	// do not inject stateful beans !
	// @Inject
	private MovieService service;
	@EJB
	private CrewService cs;
	private Crew crewMember;
	private List<Crew> allCrew; // FIXME ! LAZY LOAD

	public void setCrewMember(Crew crewMember) {
		this.crewMember = crewMember;
	}

	public Crew getCrewMember() {
		return crewMember;
	}

	public Movie getMovie() {
		return movie;
	}

	@PostConstruct
	void init() {
		// http://stackoverflow.com/questions/3406555/why-use-postconstruct
		movie = new Movie();
		allCrew = cs.allCrew();
	}

	public String add() {
		FacesContext context = FacesContext.getCurrentInstance();
		movie.setAvailable(movie.getNumberOfCopies());
		System.out.println("PRICE: " + movie.getPrice());
		System.out.println("YEAR: " + movie.getYearOfRelease());
		System.out.println("RATING: " + movie.getRating());
		movie = service.add(movie);
		if (movie.getIdmovie() == 0) {
			context.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_ERROR, "Adding movie failed", null));
			return null;
		}
		// what if no session?
		context.getExternalContext().getSessionMap().put("movie", movie);
		return "/admin/add_movie_crew.xhtml?faces-redirect=true&id="
			+ movie.getIdmovie();
	}

	public List<Crew> getAllCrew() {
		return allCrew;
	}

	public String addCrewMember() {
		service.addCrew(movie, crewMember);
		return "admin/add_movie_crew.xhtml?id=" + movie.getIdmovie();
	}

	@ManagedBean
	@RequestScoped
	public static class UniqueTitleValidator implements Validator {

		// Can't use a Validator (no injection) - see:
		// http://stackoverflow.com/a/7572413/281545
		@EJB
		private MovieService service;

		@Override
		public void validate(FacesContext context, UIComponent component,
				Object value) throws ValidatorException {
			if (value == null) return; // Let required="true" handle, if any.
			boolean titleUnique = false;
			try {
				titleUnique = service.isTitleUnique((String) value);
			} catch (Exception ignore) {} finally {
				if (!titleUnique) {
					throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"There is a movie with this title", null));
				}
			}
		}
	}

	// @FacesConverter(forClass = Crew.class) // no injection
	@ManagedBean
	@ViewScoped
	public static class CrewConverter implements Converter {

		@EJB
		private CrewService cs;

		@Override
		public String getAsString(FacesContext context, UIComponent component,
				Object value) {
			return (value instanceof Crew) ? ((Crew) value).getName() : null;
			// return (value instanceof Crew) ? new String(((Crew) value)
			// .getName().getBytes(Charset.defaultCharset()),
			// StandardCharsets.UTF_8) : null; // no use
		}

		@Override
		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
			if (value == null) {
				return null;
			}
			System.out.println("value : " + value);
			List<Crew> allCrew = cs.allCrew();
			for (Crew crew : allCrew) {
				if (crew.getName().equals(value)) {
					System.out.println("value found: " + value);
					return crew;
				}
			}
			throw new ConverterException(new FacesMessage(String.format(
				"Cannot convert %s to Crew", value)));
		}
	}
}

package dvd_store.controllers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import dvd_store.entities.Category;
import dvd_store.entities.Crew;
import dvd_store.entities.Movie;
import dvd_store.entities.Role;
import dvd_store.service.CategoriesService;
import dvd_store.service.CrewService;
import dvd_store.service.MovieService;
import dvd_store.service.RolesService;

import static dvd_store.faces.utils.Utils.sessionGet;
import static dvd_store.faces.utils.Utils.sessionPut;

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
	@EJB
	private CategoriesService cats;
	@EJB
	private RolesService rs;
	private Crew crewMember;
	private Role role;
	private List<Crew> allCrew; // FIXME ! LAZY LOAD
	private List<Role> allRoles;
	private Map<Crew, Set<Role>> movieCrew;

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
		movieCrew = new HashMap<>();
		allCrew = cs.allCrew(); // FIXME ! LAZY LOAD
		allRoles = rs.all();
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
		sessionPut("movie", movie);
		return "/admin/add_movie_crew.xhtml?faces-redirect=true&id="
			+ movie.getIdmovie();
	}

	public List<Crew> getAllCrew() {
		return allCrew;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public List<Category> getAllCategories() {
		return cats.all();
	}

	public String addCrewMember() {
		if (movie.getIdmovie() == 0) {
			movie = (Movie) sessionGet("movie");
			System.out.println("Hola: movie id: " + movie.getIdmovie());
		}
		Set<Role> set = movieCrew.get(crewMember);
		if (set != null && set.contains(role)) {
			throw new ValidatorException(new FacesMessage(
				FacesMessage.SEVERITY_ERROR, crewMember.getName()
					+ " has this role (" + role.getRoleName()
					+ ") in this movie", null));
		} else if (set == null) {
			set = new HashSet<>();
		}
		set.add(role);
		movieCrew.put(crewMember, set);
		service.addCrew(movie, crewMember, role);
		return null; // stay in the same view
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	// @ManagedBean
	// @RequestScoped
	// public static class RoleValidator implements Validator {
	//
	// // TODO use a Validator (no injection) MULTIPLE FIELD VALIDATOR
	// @Override
	// public void validate(FacesContext context, UIComponent component,
	// Object value) throws ValidatorException {}
	// }
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

	@ManagedBean
	@ViewScoped
	public static class CategoryConverter implements Converter {

		@EJB
		private CategoriesService cats;

		@Override
		public String getAsString(FacesContext context, UIComponent component,
				Object value) {
			return (value instanceof Category) ? ((Category) value).getName()
					: null;
		}

		@Override
		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
			if (value == null) {
				return null;
			}
			System.out.println("value : " + value);
			List<Category> categories = cats.all();
			for (Category crew : categories) {
				if (crew.getName().equals(value)) {
					System.out.println("value found: " + value);
					return crew;
				}
			}
			throw new ConverterException(new FacesMessage(String.format(
				"Cannot convert %s to Category", value)));
		}
	}

	@ManagedBean
	@ViewScoped
	public static class RolesConverter implements Converter {

		@EJB
		private RolesService rs;

		@Override
		public String getAsString(FacesContext context, UIComponent component,
				Object value) {
			return (value instanceof Role) ? ((Role) value).getRoleName()
					: null;
		}

		@Override
		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
			if (value == null) {
				return null;
			}
			System.out.println("value : " + value);
			List<Role> roles = rs.all();
			for (Role role : roles) {
				if (role.getRoleName().equals(value)) {
					System.out.println("value found: " + value);
					return role;
				}
			}
			throw new ConverterException(new FacesMessage(String.format(
				"Cannot convert %s to Role", value)));
		}
	}
}

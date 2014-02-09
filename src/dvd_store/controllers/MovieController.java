package dvd_store.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import dvd_store.entities.Movie;
import dvd_store.service.MovieService;

@ManagedBean
@ViewScoped
public class MovieController {

	// http://stackoverflow.com/a/10691832/281545
	private Movie movie;
	@EJB
	// do not inject stateful beans !
	// @Inject
	private MovieService service;

	public Movie getMovie() {
		return movie;
	}

	@PostConstruct
	void init() {
		// http://stackoverflow.com/questions/3406555/why-use-postconstruct
		movie = new Movie();
	}

	public String add() {
		FacesContext context = FacesContext.getCurrentInstance();
		movie.setAvailable(movie.getNumberOfCopies());
		movie = service.add(movie);
		if (movie.getIdmovie() == 0) {
			context.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_ERROR, "Adding movie failed", null));
			return null;
		}
		context.getExternalContext().getSessionMap().put("movie", movie);
		return "/index.xhtml?faces-redirect=true"; // TODO movie page
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
			try {
				if (!service.isTitleUnique((String) value)) {
					throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"There is a movie with this title", null));
				}
			} catch (Exception ignore) {}
		}
	}
}

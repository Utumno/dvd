package dvd_store.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dvd_store.entities.Category;
import dvd_store.entities.Movie;
import dvd_store.service.MovieService;

@ManagedBean
@ViewScoped
public class MovieDisplayController implements Serializable {

	private static final long serialVersionUID = 1275013182632840746L;
	@EJB
	private MovieService ms;
	private Movie movie;
	private int id;
	private int quantity;

	public void init() {
		// TODO: do not query the DB if coming from search results
		if (getId() == 0) {
			String message = "Bad request. Please use a link from within the system.";
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
			return;
		}
		movie = ms.find(getId());
		if (movie == null) {
			String message = "Bad request. Unknown movie.";
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
			return;
		}
		// hack to load the categories TODO
		// see: https://community.oracle.com/thread/173733
		movie.getCategories().size();
	}

	public String printCategories() {
		StringBuilder sb = new StringBuilder();
		boolean notfirst = false;
		for (Category cat : movie.getCategories()) {
			if (notfirst) {
				sb.append(", ");
			} else notfirst = true;
			sb.append(cat.toString());
		}
		return sb.toString();
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

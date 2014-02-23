package dvd_store.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import dvd_store.entities.Category;
import dvd_store.entities.Movie;
import dvd_store.service.MovieService;

import static dvd_store.faces.utils.Utils.msgError;
import static dvd_store.faces.utils.Utils.sessionPut;

@ManagedBean
@ViewScoped
public class MovieDisplayController implements Serializable {

	private static final long serialVersionUID = 1275013182632840746L;
	@EJB
	private MovieService ms;
	private Movie movie;
	private int id;
	private int quantity = 1;
	@ManagedProperty(value = "#{cartController}")
	private CartController cartController;

	// @PostConstruct // init() called in a f:viewParam
	public void init() {
		// TODO: do not query the DB if coming from search results
		if (getId() == 0) {
			String message = "Bad request. Please use a link from within the system.";
			msgError(message);
			return;
		}
		movie = ms.find(getId());
		if (movie == null) {
			String message = "Bad request. Unknown movie.";
			msgError(message);
			return;
		}
		// hack to load the categories TODO
		// see: https://community.oracle.com/thread/173733
		movie.getCategories().size();
		sessionPut("movie", movie);
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

	public int getAvailableForThisUser() {
		// if (getCartController().getCart().get(movie) == null) return 0;
		// System.out.println("IIIIIII: " +
		// getCartController().getCart().get(movie) + "\n");
		return movie.getAvailable()
			- ((getCartController().getCart().containsKey(movie)) ? getCartController().getCart().get(movie) : 0);
	}

	public boolean hasOrdered() {
		return getCartController().getCart().containsKey(movie);
	}

	public boolean getMovieAvailable() {
		return getAvailableForThisUser() > 0;
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

	public CartController getCartController() {
		return cartController;
	}

	public void setCartController(CartController cartController) {
		this.cartController = cartController;
	}
}

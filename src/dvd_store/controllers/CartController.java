package dvd_store.controllers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dvd_store.entities.Movie;
import dvd_store.service.CategoriesService;
import dvd_store.service.CrewService;
import dvd_store.service.MovieService;

import static dvd_store.faces.utils.Utils.msgError;

@ManagedBean
@SessionScoped
public class CartController implements Serializable {

	private static final long serialVersionUID = -9051381425793781006L;
	@EJB
	private MovieService service;
	@EJB
	private CrewService cs;
	@EJB
	private CategoriesService cats;
	private Map<Movie, Integer> cart = new LinkedHashMap<>();
	private int items;
	private BigDecimal amount = new BigDecimal(0);

	public void mergeMovieToCart(Movie movie, Integer quantity) {
		if (quantity > movie.getAvailable()) {
			String message = "Available copies are " + movie.getAvailable();
			msgError(message);
			return;
		}
		updateAmountAndItems(movie, quantity);
		cart.put(movie, quantity);
	}

	public void removeMovieFromCart(Movie movie) {
		updateAmountAndItems(movie, 0);
		cart.remove(movie);
	}

	private void updateAmountAndItems(Movie movie, Integer quantity) {
		Integer ordered = cart.get(movie); // TODO: availability - events ?
		if (ordered != null) {
			items -= ordered;
			amount = amount.subtract(movie.getPrice().multiply(
				BigDecimal.valueOf(ordered)));
		}
		items += quantity;
		amount = amount.add(movie.getPrice().multiply(
			BigDecimal.valueOf(quantity))); // verbosen
	}

	public String cartMessage() {
		if (cart.isEmpty()) {
			return "Your cart is empty";
		}
		return "You have " + items + " item" + (items == 1 ? "" : "s")
			+ " in your cart worth $" + amount;
	}

	public String checkout() {
		return null;
	}

	public void emptyCart() {
		cart.clear();
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public Map<Movie, Integer> getCart() {
		return new LinkedHashMap<>(cart); // defensive copying, must not be
											// modified from outside
	}
}

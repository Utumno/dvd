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

	public String addMovieToCart(Movie movie, Integer quantity) {
		Integer ordered = cart.get(movie); // TODO: availability - events ?
		if (ordered != null) {
			ordered += quantity;
		} else ordered = quantity;
		items += quantity;
		amount = amount.add(movie.getPrice().multiply(
			BigDecimal.valueOf(quantity))); // verbosen
		cart.put(movie, ordered);
		return null;
	}

	public String cartMessage() {
		if (cart.isEmpty()) {
			return "Your cart is empty";
		}
		return "You have " + items + " items in your cart worth $" + amount;
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public Map<Movie, Integer> getCart() {
		return cart;
	}
}

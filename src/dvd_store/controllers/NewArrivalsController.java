package dvd_store.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dvd_store.entities.Movie;
import dvd_store.service.MovieService;

import static dvd_store.faces.utils.Utils.msgInfo;

@ManagedBean
@ViewScoped
public class NewArrivalsController implements Serializable {

	private static final long serialVersionUID = 1905217715663229991L;
	private int arrivals;
	@EJB
	private MovieService ms;

	public void increaseCopies(Movie m) {
		ms.increaseCopies(m, getArrivals());
		msgInfo("Successfully added " + getArrivals() + " in DB");
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public int getArrivals() {
		return arrivals;
	}

	public void setArrivals(int arrivals) {
		this.arrivals = arrivals;
	}
}

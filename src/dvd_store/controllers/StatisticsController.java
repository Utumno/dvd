package dvd_store.controllers;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.ServletException;

import dvd_store.entities.Crew;
import dvd_store.entities.Movie;
import dvd_store.service.MovieService;

@ManagedBean
@SessionScoped
public class StatisticsController implements Serializable {

	private static final long serialVersionUID = -9071934465612816015L;
	private static final String[] TYPES = new String[] { "actors", "directors",
			"movies" };
	private int howMany;
	private String type;
	@EJB
	private MovieService ms;
	private List<Movie> popMovies;
	private List<Crew> popActors;
	private List<Crew> popDirectors;

	public void mostPopular() throws ServletException {
		switch (type) {
		case "actors":
			setPopActors(ms.popActors(getHowMany()));
			break;
		case "directors":
			setPopDirectors(ms.popDirectors(getHowMany()));
			break;
		case "movies":
			setPopMovies(ms.popularMovies(getHowMany()));
			break;
		default:
			throw new ServletException("Invalid search type");
		}
	}

	public String[] getTypes() {
		return TYPES;
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public List<Movie> getPopMovies() {
		return popMovies;
	}

	public void setPopMovies(List<Movie> popMovies) {
		this.popMovies = popMovies;
	}

	public List<Crew> getPopActors() {
		return popActors;
	}

	public void setPopActors(List<Crew> popActors) {
		this.popActors = popActors;
	}

	public List<Crew> getPopDirectors() {
		return popDirectors;
	}

	public void setPopDirectors(List<Crew> popDirectors) {
		this.popDirectors = popDirectors;
	}

	public int getHowMany() {
		return howMany;
	}

	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

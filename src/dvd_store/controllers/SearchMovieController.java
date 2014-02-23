package dvd_store.controllers;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletException;

import dvd_store.entities.Movie;
import dvd_store.service.MovieService;

@ManagedBean
@ViewScoped
public class SearchMovieController implements Serializable {

	private static final long serialVersionUID = -5288482676770494491L;
	private String query;
	private String type = "Title";
	private static final String[] TYPES = { "Title", "Director", "Category" };
	@EJB
	private MovieService ms;
	private List<Movie> searchResults;

	public String[] getBrowseBy() {
		return TYPES;
	}

	public String search() throws ServletException {
		switch (type) {
		case "Title":
			setSearchResults(ms.searchTitles(query)); // FIXME !
			break;
		case "Director":
			setSearchResults(ms.searchDirector(query));
			break;
		case "Category":
			setSearchResults(ms.searchCategory(query));
			break;
		default:
			throw new ServletException("Invalid search type");
		}
		return null;
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Movie> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<Movie> searchTitles) {
		this.searchResults = searchTitles;
	}
}

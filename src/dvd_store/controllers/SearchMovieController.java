package dvd_store.controllers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletException;

import dvd_store.entities.Movie;
import dvd_store.entities.Movie.Rating;
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
	private List<Movie> advSearchResults;
	private String advTitle;
	private String advActors;
	private String advDirectors;
	private Rating advRating;
	private String advSortType;
	private final static String[] SORTINGS = new String[] { "Year",
			"Movie title", "Name of Director" };
	private final static Map<String, Integer> SORTINGS_SHOULD_BE_DAO = new HashMap<>();
	static {
		int i = 0;
		for (String sort : SORTINGS) {
			SORTINGS_SHOULD_BE_DAO.put(sort, i++);
		}
	}

	public String[] getBrowseBy() {
		return TYPES;
	}

	public String[] getSortBy() {
		return SORTINGS;
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

	public void advancedSearch() {
		this.advSearchResults = ms.andvancedSearch(
			SORTINGS_SHOULD_BE_DAO.get(advSortType), advActors, advDirectors,
			advTitle, null); // advRating
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

	public String getAdvTitle() {
		return advTitle;
	}

	public void setAdvTitle(String advTitle) {
		this.advTitle = advTitle;
	}

	public String getAdvActors() {
		return advActors;
	}

	public void setAdvActors(String advActors) {
		this.advActors = advActors;
	}

	public String getAdvDirectors() {
		return advDirectors;
	}

	public void setAdvDirectors(String advDirectors) {
		this.advDirectors = advDirectors;
	}

	public Rating getAdvRating() {
		return advRating;
	}

	public void setAdvRating(Rating advRating) {
		this.advRating = advRating;
	}

	public String getAdvSortType() {
		return advSortType;
	}

	public void setAdvSortType(String advSortType) {
		this.advSortType = advSortType;
	}

	public static String[] getSortings() {
		return SORTINGS;
	}

	public List<Movie> getAdvSearchResults() {
		return advSearchResults;
	}

	public void setAdvSearchResults(List<Movie> advSearchResults) {
		this.advSearchResults = advSearchResults;
	}
}

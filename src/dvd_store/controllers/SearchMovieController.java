package dvd_store.controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletException;

import dvd_store.service.MovieService;

@ManagedBean
@ViewScoped
public class SearchMovieController {

	private String query;
	private String type = "Title";
	private static final String[] TYPES = { "Title", "Director", "Category" };
	@EJB
	private MovieService ms;

	public String[] getBrowseBy() {
		return TYPES;
	}

	public String search() throws ServletException {
		switch (type) {
		case "Title":
			ms.searchTitles(query);
			break;
		case "Director":
			break;
		case "Category":
			break;
		default:
			throw new ServletException("Invalid search type");
		}
		return null;
	}

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
}

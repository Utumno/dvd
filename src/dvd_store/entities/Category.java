package dvd_store.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the categories database table.
 */
@Entity
@Table(name = "categories")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int idcategory;
	private String name;
	// bi-directional many-to-many association to Movy
	@ManyToMany(mappedBy = "categories")
	private List<Movie> movies;

	public Category() {}

	public int getIdcategory() {
		return this.idcategory;
	}

	public void setIdcategory(int idcategory) {
		this.idcategory = idcategory;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Movie> getMovies() {
		return this.movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
}

package dvd_store.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categories database table.
 * 
 */
@Entity
@Table(name="categories")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	//bi-directional many-to-many association to Movy
	@ManyToMany(mappedBy="categories")
	private List<Movy> movies;

	public Category() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Movy> getMovies() {
		return this.movies;
	}

	public void setMovies(List<Movy> movies) {
		this.movies = movies;
	}

}
package dvd_store.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the crew database table.
 * 
 */
@Entity
@NamedQuery(name="Crew.findAll", query="SELECT c FROM Crew c")
public class Crew implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idcrew;

	private String name;

	//bi-directional many-to-many association to Movy
	@ManyToMany(mappedBy="crews")
	private List<Movy> movies;

	public Crew() {
	}

	public int getIdcrew() {
		return this.idcrew;
	}

	public void setIdcrew(int idcrew) {
		this.idcrew = idcrew;
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
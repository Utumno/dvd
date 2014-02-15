package dvd_store.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * The persistent class for the crew database table. Uses the id to define
 * equals and hashCode - keep this in mind when using in maps. TODO investigate
 */
@Entity
@NamedQuery(name = "Crew.findAll", query = "SELECT c FROM Crew c")
public class Crew implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int idcrew;
	@NotNull(message = "Please provide a name")
	private String name;
	// bi-directional many-to-one association to MoviesHasCrew
	@OneToMany(mappedBy = "crew")
	private List<MoviesHasCrew> moviesHasCrews;

	public Crew() {}

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

	@Override
	public int hashCode() {
		return 31 + getIdcrew();
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj)
			|| (obj instanceof Crew && getIdcrew() != ((Crew) obj).getIdcrew());
	}

	public List<MoviesHasCrew> getMoviesHasCrews() {
		return this.moviesHasCrews;
	}

	public void setMoviesHasCrews(List<MoviesHasCrew> moviesHasCrews) {
		this.moviesHasCrews = moviesHasCrews;
	}

	public MoviesHasCrew addMoviesHasCrew(MoviesHasCrew moviesHasCrew) {
		getMoviesHasCrews().add(moviesHasCrew);
		moviesHasCrew.setCrew(this);
		return moviesHasCrew;
	}

	public MoviesHasCrew removeMoviesHasCrew(MoviesHasCrew moviesHasCrew) {
		getMoviesHasCrews().remove(moviesHasCrew);
		moviesHasCrew.setCrew(null);
		return moviesHasCrew;
	}
}

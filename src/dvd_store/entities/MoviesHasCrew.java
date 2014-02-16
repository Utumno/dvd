package dvd_store.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the movies_has_crew database table.
 */
@Entity
@Table(name = "movies_has_crew")
@NamedQuery(name = "MoviesHasCrew.findAll",
		query = "SELECT m FROM MoviesHasCrew m")
public class MoviesHasCrew implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private MoviesHasCrewPK id;
	// bi-directional many-to-one association to Crew
	@ManyToOne
	private Crew crew;
	// bi-directional many-to-one association to Movy
	@ManyToOne
	@JoinColumn(name = "movies_idmovie")
	private Movie movy;
	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name = "roles_idrole")
	private Role role;

	public MoviesHasCrew() {}

	@Override
	public String toString() {
		return "MoviesHasCrewPK: " + id;
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public MoviesHasCrewPK getId() {
		return this.id;
	}

	public void setId(MoviesHasCrewPK id) {
		this.id = id;
	}

	public Crew getCrew() {
		return this.crew;
	}

	public void setCrew(Crew crew) {
		this.crew = crew;
	}

	public Movie getMovy() {
		return this.movy;
	}

	public void setMovy(Movie movy) {
		this.movy = movy;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}

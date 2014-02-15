package dvd_store.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the movies_has_crew database table.
 */
@Embeddable
public class MoviesHasCrewPK implements Serializable {

	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	@Column(name = "movies_idmovie", insertable = false, updatable = false)
	private int moviesIdmovie;
	@Column(name = "crew_idcrew", insertable = false, updatable = false)
	private int crewIdcrew;
	@Column(name = "roles_idrole", insertable = false, updatable = false)
	private int rolesIdrole;

	public MoviesHasCrewPK() {}

	public int getMoviesIdmovie() {
		return this.moviesIdmovie;
	}

	public void setMoviesIdmovie(int moviesIdmovie) {
		this.moviesIdmovie = moviesIdmovie;
	}

	public int getCrewIdcrew() {
		return this.crewIdcrew;
	}

	public void setCrewIdcrew(int crewIdcrew) {
		this.crewIdcrew = crewIdcrew;
	}

	public int getRolesIdrole() {
		return this.rolesIdrole;
	}

	public void setRolesIdrole(int rolesIdrole) {
		this.rolesIdrole = rolesIdrole;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MoviesHasCrewPK)) {
			return false;
		}
		MoviesHasCrewPK castOther = (MoviesHasCrewPK) other;
		return (this.moviesIdmovie == castOther.moviesIdmovie)
			&& (this.crewIdcrew == castOther.crewIdcrew)
			&& (this.rolesIdrole == castOther.rolesIdrole);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.moviesIdmovie;
		hash = hash * prime + this.crewIdcrew;
		hash = hash * prime + this.rolesIdrole;
		return hash;
	}
}

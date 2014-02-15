package dvd_store.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the movie_crew database table.
 */
@Entity
@Table(name = "movie_crew")
@NamedQuery(name = "MovieCrew.findAll", query = "SELECT m FROM MovieCrew m")
public class MovieCrew implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "crew_idcrew")
	private int crewIdcrew;
	@Column(name = "roles_idrole")
	private int rolesIdrole;

	public MovieCrew() {}

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
}

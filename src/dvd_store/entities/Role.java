package dvd_store.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the roles database table.
 */
@Entity
@Table(name = "roles")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int idrole;
	@Column(name = "role_name")
	private String roleName;
	// bi-directional many-to-one association to MoviesHasCrew
	@OneToMany(mappedBy = "role")
	private List<MoviesHasCrew> moviesHasCrews;

	public Role() {}

	public int getIdrole() {
		return this.idrole;
	}

	public void setIdrole(int idrole) {
		this.idrole = idrole;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<MoviesHasCrew> getMoviesHasCrews() {
		return this.moviesHasCrews;
	}

	public void setMoviesHasCrews(List<MoviesHasCrew> moviesHasCrews) {
		this.moviesHasCrews = moviesHasCrews;
	}

	public MoviesHasCrew addMoviesHasCrew(MoviesHasCrew moviesHasCrew) {
		getMoviesHasCrews().add(moviesHasCrew);
		moviesHasCrew.setRole(this);
		return moviesHasCrew;
	}

	public MoviesHasCrew removeMoviesHasCrew(MoviesHasCrew moviesHasCrew) {
		getMoviesHasCrews().remove(moviesHasCrew);
		moviesHasCrew.setRole(null);
		return moviesHasCrew;
	}
}

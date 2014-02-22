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
	public static Role DEFAUT_ROLE;
	static {
		DEFAUT_ROLE = new Role();
		DEFAUT_ROLE.idrole = 5;
		DEFAUT_ROLE.roleName = "Actor";
	}

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

	@Override
	public String toString() {
		return /* "Role [roleName=" + */roleName /* + "]" */;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idrole;
		result = prime * result
			+ ((roleName == null) ? 0 : roleName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Role)) return false;
		Role other = (Role) obj;
		if (idrole != other.idrole) return false;
		if (roleName == null) {
			if (other.roleName != null) return false;
		} else if (!roleName.equals(other.roleName)) return false;
		return true;
	}
}

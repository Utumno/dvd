package dvd_store.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * The persistent class for the admins database table.
 */
@Entity
@Table(name = "admins")
@NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a")
public class Admin implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int idadmin;
	// bi-directional one-to-one association to User
	@OneToOne
	@PrimaryKeyJoinColumn(name = "idadmin")
	private User user;

	public Admin() {}

	public int getIdadmin() {
		return this.idadmin;
	}

	public void setIdadmin(int idadmin) {
		this.idadmin = idadmin;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

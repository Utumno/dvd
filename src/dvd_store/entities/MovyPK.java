package dvd_store.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the movies database table.
 * 
 */
@Embeddable
public class MovyPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idmovie;

	private String title;

	public MovyPK() {
	}
	public int getIdmovie() {
		return this.idmovie;
	}
	public void setIdmovie(int idmovie) {
		this.idmovie = idmovie;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MovyPK)) {
			return false;
		}
		MovyPK castOther = (MovyPK)other;
		return 
			(this.idmovie == castOther.idmovie)
			&& this.title.equals(castOther.title);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idmovie;
		hash = hash * prime + this.title.hashCode();
		
		return hash;
	}
}
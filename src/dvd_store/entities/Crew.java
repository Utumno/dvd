package dvd_store.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
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
}

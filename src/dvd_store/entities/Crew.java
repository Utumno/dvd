package dvd_store.entities;

import java.io.Serializable;
import javax.persistence.*;


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

}
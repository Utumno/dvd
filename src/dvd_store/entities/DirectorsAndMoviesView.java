package dvd_store.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the directors_and_movies_view database table.
 */
@Entity
@Table(name = "directors_and_movies_view")
@NamedQuery(name = "DirectorsAndMoviesView.findAll",
		query = "SELECT d FROM DirectorsAndMoviesView d")
public class DirectorsAndMoviesView implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idcrew;
	@Column(name = "movies_idmovie")
	private int moviesIdmovie;
	private String name;

	public DirectorsAndMoviesView() {}

	public int getIdcrew() {
		return this.idcrew;
	}

	public void setIdcrew(int idcrew) {
		this.idcrew = idcrew;
	}

	public int getMoviesIdmovie() {
		return this.moviesIdmovie;
	}

	public void setMoviesIdmovie(int moviesIdmovie) {
		this.moviesIdmovie = moviesIdmovie;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

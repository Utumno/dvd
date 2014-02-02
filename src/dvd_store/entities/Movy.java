package dvd_store.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the movies database table.
 * 
 */
@Entity
@Table(name="movies")
@NamedQuery(name="Movy.findAll", query="SELECT m FROM Movy m")
public class Movy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idmovie;

	private int available;

	@Column(name="number_of_copies")
	private int numberOfCopies;

	private BigDecimal price;

	private String rating;

	private String title;

	@Temporal(TemporalType.DATE)
	@Column(name="year_of_release")
	private Date yearOfRelease;

	//uni-directional many-to-many association to Category
	@ManyToMany
	@JoinTable(
		name="movies_has_categories"
		, joinColumns={
			@JoinColumn(name="movies_idmovie")
			}
		, inverseJoinColumns={
			@JoinColumn(name="categories_idcategory")
			}
		)
	private List<Category> categories;

	//uni-directional many-to-many association to Crew
	@ManyToMany
	@JoinTable(
		name="movies_has_crew"
		, joinColumns={
			@JoinColumn(name="movies_idmovie")
			}
		, inverseJoinColumns={
			@JoinColumn(name="crew_idcrew")
			}
		)
	private List<Crew> crews;

	public Movy() {
	}

	public int getIdmovie() {
		return this.idmovie;
	}

	public void setIdmovie(int idmovie) {
		this.idmovie = idmovie;
	}

	public int getAvailable() {
		return this.available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public int getNumberOfCopies() {
		return this.numberOfCopies;
	}

	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getYearOfRelease() {
		return this.yearOfRelease;
	}

	public void setYearOfRelease(Date yearOfRelease) {
		this.yearOfRelease = yearOfRelease;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Crew> getCrews() {
		return this.crews;
	}

	public void setCrews(List<Crew> crews) {
		this.crews = crews;
	}

}
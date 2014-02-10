package dvd_store.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The persistent class for the movies database table.
 *
 */
@Entity
@Table(name = "movies")
@NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m")
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idmovie;
	private int available;
	@Column(name = "number_of_copies")
	@NotNull(message = "Please enter number of copies")
	private int numberOfCopies;
	@Digits(fraction = 2, integer = 3)
	private BigDecimal price;
	@Enumerated(EnumType.STRING)
	private Rating rating;
	@NotNull(message = "Please enter the title of the movie")
	@Size(max = 200, message = "Max 200 chars")
	private String title;
	@Column(name = "year_of_release")
	@NotNull(message = "Please enter the year of the movie")
	@Min(1900)
	@Max(2014)
	private short yearOfRelease;
	// uni-directional many-to-many association to Category
	@ManyToMany
	@JoinTable(name = "movies_has_categories", joinColumns = { @JoinColumn(
			name = "movies_idmovie") }, inverseJoinColumns = { @JoinColumn(
			name = "categories_idcategory") })
	private List<Category> categories;
	// uni-directional many-to-many association to Crew
	@ManyToMany
	@JoinTable(name = "movies_has_crew", joinColumns = { @JoinColumn(
			name = "movies_idmovie") }, inverseJoinColumns = { @JoinColumn(
			name = "crew_idcrew") })
	private List<Crew> crews;

	public Movie() {}

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

	public Rating getRating() {
		return this.rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public short getYearOfRelease() {
		return this.yearOfRelease;
	}

	public void setYearOfRelease(short yearOfRelease) {
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

	public static enum Rating {
		G("G"), NC_17("NC-17"), R("R"), PG("PG"), PG_13(
				"PG-13");

		private String label;

		private Rating(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	public Rating[] getRatings() {
		return Rating.values();
	}
}

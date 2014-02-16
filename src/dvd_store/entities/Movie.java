package dvd_store.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
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
	private int numberOfCopies = 1; // or Integer to avoid seeing value in form
	// PRICE
	@Digits(fraction = 2, integer = 3)
	private BigDecimal price;
	// RATING
	@Enumerated(EnumType.STRING)
	private Rating rating;
	@NotNull(message = "Please enter the title of the movie")
	@Size(max = 200, message = "Max 200 chars")
	// TITLE
	private String title;
	// YEAR
	private static final short MIN_YEAR = 1901;
	private static final short MAX_YEAR = 2014; // (short) new
	// java.util.Date().getYear(); // must be interned to use in message
	private static final List<Short> YEARS = new ArrayList<>(MAX_YEAR
		- MIN_YEAR + 1);
	static {
		for (short i = MIN_YEAR; i <= MAX_YEAR; ++i) {
			YEARS.add(i);
		}
	}
	private static final String MIN_MSG = "Min release year: " + MIN_YEAR;
	private static final String MAX_MSG = "Max release year: " + MAX_YEAR;
	@Column(name = "year_of_release")
	@NotNull(message = "Please enter the year of release of the movie")
	@Min(value = 1901, message = MIN_MSG)
	@Max(value = 2014, message = MAX_MSG)
	private short yearOfRelease = 2014; // Short to avoid seeing value in form
	// =========================================================================
	// Associations
	// =========================================================================
	// bi-directional many-to-many association to Category
	@ManyToMany
	@JoinTable(name = "movies_has_categories", joinColumns = { @JoinColumn(
			name = "movies_idmovie") }, inverseJoinColumns = { @JoinColumn(
			name = "categories_idcategory") })
	private List<Category> categories;
	// bi-directional many-to-one association to MoviesHasCrew
	@OneToMany(mappedBy = "movy", cascade = CascadeType.PERSIST)
	private List<MoviesHasCrew> moviesHasCrews;
	// bi-directional many-to-one association to OrdersHasMovy
	@OneToMany(mappedBy = "movy")
	private List<OrdersHasMovy> ordersHasMovies;

	public Movie() {}

	// =========================================================================
	// Getters Setters - Movie
	// =========================================================================
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

	// =========================================================================
	// Getters Setters Associations
	// =========================================================================
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<MoviesHasCrew> getMoviesHasCrews() {
		return this.moviesHasCrews;
	}

	public void setMoviesHasCrews(List<MoviesHasCrew> moviesHasCrews) {
		this.moviesHasCrews = moviesHasCrews;
	}

	public MoviesHasCrew addMoviesHasCrew(MoviesHasCrew moviesHasCrew) {
		getMoviesHasCrews().add(moviesHasCrew);
		moviesHasCrew.setMovy(this);
		return moviesHasCrew;
	}

	public MoviesHasCrew removeMoviesHasCrew(MoviesHasCrew moviesHasCrew) {
		getMoviesHasCrews().remove(moviesHasCrew);
		moviesHasCrew.setMovy(null);
		return moviesHasCrew;
	}

	public List<OrdersHasMovy> getOrdersHasMovies() {
		return this.ordersHasMovies;
	}

	public void setOrdersHasMovies(List<OrdersHasMovy> ordersHasMovies) {
		this.ordersHasMovies = ordersHasMovies;
	}

	public OrdersHasMovy addOrdersHasMovy(OrdersHasMovy ordersHasMovy) {
		getOrdersHasMovies().add(ordersHasMovy);
		ordersHasMovy.setMovy(this);
		return ordersHasMovy;
	}

	public OrdersHasMovy removeOrdersHasMovy(OrdersHasMovy ordersHasMovy) {
		getOrdersHasMovies().remove(ordersHasMovy);
		ordersHasMovy.setMovy(null);
		return ordersHasMovy;
	}

	// =========================================================================
	// Helpers
	// =========================================================================
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

		@Override
		public String toString() {
			return label;
		}
	}

	public Rating[] getRatings() {
		return Rating.values();
	}

	public List<Short> getYears() {
		return YEARS;
	}
}

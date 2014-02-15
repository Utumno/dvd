package dvd_store.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the orders_has_movies database table.
 */
@Embeddable
public class OrdersHasMovyPK implements Serializable {

	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	@Column(name = "orders_idorder", insertable = false, updatable = false)
	private int ordersIdorder;
	@Column(name = "movies_idmovie", insertable = false, updatable = false)
	private int moviesIdmovie;

	public OrdersHasMovyPK() {}

	public int getOrdersIdorder() {
		return this.ordersIdorder;
	}

	public void setOrdersIdorder(int ordersIdorder) {
		this.ordersIdorder = ordersIdorder;
	}

	public int getMoviesIdmovie() {
		return this.moviesIdmovie;
	}

	public void setMoviesIdmovie(int moviesIdmovie) {
		this.moviesIdmovie = moviesIdmovie;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrdersHasMovyPK)) {
			return false;
		}
		OrdersHasMovyPK castOther = (OrdersHasMovyPK) other;
		return (this.ordersIdorder == castOther.ordersIdorder)
			&& (this.moviesIdmovie == castOther.moviesIdmovie);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ordersIdorder;
		hash = hash * prime + this.moviesIdmovie;
		return hash;
	}
}

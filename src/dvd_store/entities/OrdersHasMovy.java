package dvd_store.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the orders_has_movies database table.
 */
@Entity
@Table(name = "orders_has_movies")
@NamedQuery(name = "OrdersHasMovy.findAll",
		query = "SELECT o FROM OrdersHasMovy o")
public class OrdersHasMovy implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private OrdersHasMovyPK id;
	private int quantity;
	// bi-directional many-to-one association to Movy
	@ManyToOne
	@JoinColumn(name = "movies_idmovie")
	private Movie movy;
	// bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name = "orders_idorder")
	private Order order;

	public OrdersHasMovy() {}

	public OrdersHasMovyPK getId() {
		return this.id;
	}

	public void setId(OrdersHasMovyPK id) {
		this.id = id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Movie getMovy() {
		return this.movy;
	}

	public void setMovy(Movie movy) {
		this.movy = movy;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}

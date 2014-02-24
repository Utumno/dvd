package dvd_store.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the orders database table.
 */
@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int idorder;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Column(name = "shipping_info")
	@Enumerated(EnumType.STRING)
	private ShippingInfo shippingInfo;
	// bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name = "addresses_idaddress")
	private Address address;
	// bi-directional many-to-one association to CreditCard
	@ManyToOne
	@JoinColumn(name = "credit_cards_credit_card_number")
	private CreditCard creditCard;
	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "users_iduser")
	private User user;
	// bi-directional many-to-one association to OrdersHasMovy
	@OneToMany(mappedBy = "order")
	private List<OrdersHasMovy> ordersHasMovies;

	public Order() {}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public int getIdorder() {
		return this.idorder;
	}

	public void setIdorder(int idorder) {
		this.idorder = idorder;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ShippingInfo getShippingInfo() {
		return this.shippingInfo;
	}

	public void setShippingInfo(ShippingInfo shippingInfo) {
		this.shippingInfo = shippingInfo;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrdersHasMovy> getOrdersHasMovies() {
		return this.ordersHasMovies;
	}

	public void setOrdersHasMovies(List<OrdersHasMovy> ordersHasMovies) {
		this.ordersHasMovies = ordersHasMovies;
	}

	public OrdersHasMovy addOrdersHasMovy(OrdersHasMovy ordersHasMovy) {
		getOrdersHasMovies().add(ordersHasMovy);
		ordersHasMovy.setOrder(this);
		return ordersHasMovy;
	}

	public OrdersHasMovy removeOrdersHasMovy(OrdersHasMovy ordersHasMovy) {
		getOrdersHasMovies().remove(ordersHasMovy);
		ordersHasMovy.setOrder(null);
		return ordersHasMovy;
	}

	// =========================================================================
	// Helpers
	// =========================================================================
	public static enum ShippingInfo {
		COURRIER("COURRIER"), SIMPLE("SIMPLE");

		private String label;

		private ShippingInfo(String label) {
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
}

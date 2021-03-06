package dvd_store.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The persistent class for the credit_cards database table.
 */
@Entity
@Table(name = "credit_cards")
@NamedQuery(name = "CreditCard.findAll", query = "SELECT c FROM CreditCard c")
public class CreditCard implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "credit_card_number")
	// FIXME validation
	// @Min(value = 1);
	// @Positive ?
	@NotNull(message = "Please provide a credit card number")
	@Digits(integer = 16, message = "At most sixteen digits", fraction = 0)
	private BigInteger creditCardNumber;
	@Column(name = "credit_card_type")
	@Size(max = 45, message = "Max 45 chars")
	private String creditCardType;
	// bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name = "addresses_idaddress")
	private Address address;
	// bi-directional many-to-one association to Order
	@OneToMany(mappedBy = "creditCard")
	private List<Order> orders;
	// bi-directional many-to-many association to User
	@ManyToMany(mappedBy = "creditCards")
	private List<User> users;

	public CreditCard() {}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public BigInteger getCreditCardNumber() {
		return this.creditCardNumber;
	}

	public void setCreditCardNumber(BigInteger creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardType() {
		return this.creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setCreditCard(this);
		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setCreditCard(null);
		return order;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}

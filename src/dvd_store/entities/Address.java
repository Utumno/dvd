package dvd_store.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the addresses database table.
 */
@Entity
@Table(name = "addresses")
@NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int idaddress;
	private String city;
	@Column(name = "postal_code")
	private String postalCode;
	private String street;
	// bi-directional many-to-one association to CreditCard
	@OneToMany(mappedBy = "address")
	private List<CreditCard> creditCards;
	// bi-directional many-to-one association to Order
	@OneToMany(mappedBy = "address")
	private List<Order> orders;
	// bi-directional many-to-many association to User
	@ManyToMany(mappedBy = "addresses")
	private List<User> users;

	public Address() {}

	public int getIdaddress() {
		return this.idaddress;
	}

	public void setIdaddress(int idaddress) {
		this.idaddress = idaddress;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public List<CreditCard> getCreditCards() {
		return this.creditCards;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	public CreditCard addCreditCard(CreditCard creditCard) {
		getCreditCards().add(creditCard);
		creditCard.setAddress(this);
		return creditCard;
	}

	public CreditCard removeCreditCard(CreditCard creditCard) {
		getCreditCards().remove(creditCard);
		creditCard.setAddress(null);
		return creditCard;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setAddress(this);
		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setAddress(null);
		return order;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}

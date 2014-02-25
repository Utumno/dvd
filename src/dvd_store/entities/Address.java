package dvd_store.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * The persistent class for the addresses database table.
 */
@Entity
@Table(name = "addresses")
@NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idaddress;
	@Size(max = 45, message = "Max 45 chars")
	private String city;
	@Column(name = "postal_code")
	@Size(max = 10, message = "Max 10 chars")
	private String postalCode;
	@Size(max = 45, message = "Max 45 chars")
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
		return this.city;// new String(city.getBytes(), // NPE
		// java.nio.charset.StandardCharsets.ISO_8859_1);
	}

	public void setCity(String city) {
		this.city = city;
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		System.out.println("Get " + street);
		return this.street;
	}

	public void setStreet(String street) {
		System.out.println("Set " + street);
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

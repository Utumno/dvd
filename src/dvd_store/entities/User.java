package dvd_store.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** The persistent class for the users database table. */
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int iduser;
	@Temporal(TemporalType.DATE)
	@Past(message = "Back from the future ?")
	@NotNull(message = "Please enter your date of birth")
	private Date birthdate;
	@NotNull(message = "Please enter your email address")
	@Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)",
			message = "Email is not in valid format")
	@Size(max = 45, message = "Max 45 chars")
	private String email;
	@NotNull(message = "Please enter your name")
	@Size(max = 45, message = "Max 45 chars")
	private String name;
	@NotNull(message = "Please enter a password")
	@Pattern(regexp = ".*(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*",
			message = "Password is not strong enough")
	@Size(max = 45, message = "Max 45 chars")
	private String password;
	@Column(name = "phone_number")
	@Pattern(regexp = "[1-9][0-9]{9}",
			message = "Phone numbers are 10 digit numbers, first one not zero")
	private String phoneNumber;
	@NotNull(message = "Please enter your surname")
	@Size(max = 45, message = "Max 45 chars")
	private String surname;
	@NotNull(message = "Please enter a username")
	@Pattern(regexp = "[A-Za-z0-9_]{6}[A-Za-z0-9_]*",
			message = "Usernames can have latin characters, the underscore and "
				+ "digits and are at least 6 characters")
	@Size(max = 45, message = "Max 45 chars")
	private String username;
	// bi-directional one-to-one association to Admin
	@OneToOne(mappedBy = "user")
	private Admin admin;
	// bi-directional many-to-one association to Order
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	// bi-directional many-to-many association to Address
	// uni-directional many-to-many association to Address
	@ManyToMany
	@JoinTable(name = "users_has_addresses", joinColumns = { @JoinColumn(
			name = "users_iduser") }, inverseJoinColumns = { @JoinColumn(
			name = "addresses_idaddress") })
	private List<Address> addresses;
	// bi-directional many-to-many association to CreditCard
	@ManyToMany
	@JoinTable(name = "users_has_credit_cards", joinColumns = { @JoinColumn(
			name = "users_iduser") }, inverseJoinColumns = { @JoinColumn(
			name = "credit_cards_credit_card_number") })
	private List<CreditCard> creditCards;
	// ROLE
	@Transient
	boolean isAdmin;

	public User() {}

	public int getIduser() {
		return this.iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Admin getAdmin() {
		return this.admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setUser(this);
		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setUser(null);
		return order;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public List<CreditCard> getCreditCards() {
		return this.creditCards;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
}

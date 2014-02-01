package dvd_store.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idorder;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(name="shipping_info")
	private String shippingInfo;

	//bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name="addresses_idaddress")
	private Address address;

	//bi-directional many-to-one association to CreditCard
	@ManyToOne
	@JoinColumn(name="credit_cards_credit_card_number")
	private CreditCard creditCard;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="users_iduser")
	private User user;

	public Order() {
	}

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

	public String getShippingInfo() {
		return this.shippingInfo;
	}

	public void setShippingInfo(String shippingInfo) {
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

}
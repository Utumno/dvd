package dvd_store.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the credit_cards database table.
 * 
 */
@Entity
@Table(name="credit_cards")
@NamedQuery(name="CreditCard.findAll", query="SELECT c FROM CreditCard c")
public class CreditCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="credit_card_number")
	private int creditCardNumber;

	@Column(name="credit_card_type")
	private String creditCardType;

	//uni-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name="addresses_idaddress")
	private Address address;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="creditCard")
	private List<Order> orders;

	public CreditCard() {
	}

	public int getCreditCardNumber() {
		return this.creditCardNumber;
	}

	public void setCreditCardNumber(int creditCardNumber) {
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

}
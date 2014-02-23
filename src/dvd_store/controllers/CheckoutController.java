package dvd_store.controllers;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import dvd_store.entities.Address;
import dvd_store.entities.CreditCard;
import dvd_store.entities.Movie;
import dvd_store.entities.Order;
import dvd_store.entities.User;
import dvd_store.service.OrderService;

import static dvd_store.faces.utils.Utils.sessionGet;

@ManagedBean
@ViewScoped
public class CheckoutController implements Serializable {

	private static final long serialVersionUID = -330518307160114012L;
	private CreditCard cs = new CreditCard();
	private Address adr = new Address();
	private Address csadr = new Address();
	private String shippingInfo;
	@EJB
	private OrderService os;
	@ManagedProperty(value = "#{cartController}")
	private CartController cc;
	private Order order; // UNUSED

	public String checkout() {
		User u = (User) sessionGet("user");
		Map<Movie, Integer> cart = cc.getCart();
		// System.out.println("CAAAAARRRTTT:" + cart + "\n"); // YES!
		os.addOrder(cs, adr, csadr, u, cart); // FIXME - REAL ORDER
		return shippingInfo;
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public CreditCard getCs() {
		return cs;
	}

	public void setCs(CreditCard cs) {
		this.cs = cs;
	}

	public Address getAdr() {
		return adr;
	}

	public void setAdr(Address adr) {
		this.adr = adr;
	}

	public Address getCsadr() {
		return csadr;
	}

	public void setCsadr(Address csadr) {
		this.csadr = csadr;
	}

	public String getShippingInfo() {
		return shippingInfo;
	}

	public void setShippingInfo(String shippingInfo) {
		this.shippingInfo = shippingInfo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public CartController getCc() {
		return cc;
	}

	public void setCc(CartController cc) {
		this.cc = cc;
	}
}

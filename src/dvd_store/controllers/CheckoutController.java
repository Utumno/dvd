package dvd_store.controllers;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import dvd_store.entities.Address;
import dvd_store.entities.CreditCard;
import dvd_store.entities.Movie;
import dvd_store.entities.Order;
import dvd_store.entities.Order.ShippingInfo;
import dvd_store.entities.User;
import dvd_store.service.OrderService;

import static dvd_store.faces.utils.Utils.sessionGet;

@ManagedBean
@ViewScoped
public class CheckoutController implements Serializable {

	private static final long serialVersionUID = -330518307160114012L;
	private CreditCard cd = new CreditCard();
	private Address postalAddresss = new Address();
	private Address ccAddresss = new Address();
	private ShippingInfo shippingInfo;
	@EJB
	private OrderService os;
	@ManagedProperty(value = "#{cartController}")
	private CartController cc;
	private Order order; // UNUSED
	/** this is edited by the view when user edits quantities */
	private Map<Movie, Integer> viewCart;

	@PostConstruct
	void init() {
		setViewCart(cc.getCart());
	}

	public String checkout() {
		User u = (User) sessionGet("user");
		System.out.println("ADDR: " + postalAddresss.getStreet() + " USER "
			+ u.getUsername() + "\n"); // YES!
		// cs = new CreditCard();
		// csadr = adr = new Address();
		// shippingInfo = null;
		if (!cc.getCart().isEmpty()) // hack - I want to stay in checkout
			os.addOrder(cd, postalAddresss, ccAddresss, shippingInfo, u,
				cc.getCart());
		cc.emptyCart();
		return /* "/index.xhtml" */null;
	}

	public void editRowQuantity(Movie movie, Integer quantity) {
		cc.mergeMovieToCart(movie, quantity);
	}

	public void removeRowFromCart(Movie m) {
		viewCart.remove(m);
		cc.removeMovieFromCart(m);
	}

	public ShippingInfo[] getShippingInfos() {
		return ShippingInfo.values();
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public CreditCard getCs() {
		return cd;
	}

	public void setCs(CreditCard cs) {
		this.cd = cs;
	}

	public Address getAdr() {
		return postalAddresss;
	}

	public void setAdr(Address adr) {
		this.postalAddresss = adr;
	}

	public Address getCsadr() {
		return ccAddresss;
	}

	public void setCsadr(Address csadr) {
		this.ccAddresss = csadr;
	}

	public ShippingInfo getShippingInfo() {
		return shippingInfo;
	}

	public void setShippingInfo(ShippingInfo shippingInfo) {
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

	public Map<Movie, Integer> getViewCart() {
		return viewCart;
	}

	public void setViewCart(Map<Movie, Integer> viewCart) {
		this.viewCart = viewCart;
	}
}

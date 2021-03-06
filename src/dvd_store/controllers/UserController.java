package dvd_store.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import dvd_store.entities.Order;
import dvd_store.entities.User;
import dvd_store.service.OrderService;
import dvd_store.service.UserService;

import static dvd_store.faces.utils.Utils.msgError;
import static dvd_store.faces.utils.Utils.sessionGet;
import static dvd_store.faces.utils.Utils.sessionPut;

@ManagedBean
@ViewScoped
public class UserController implements Serializable { // FIXME session and rip

														// register to a view
														// one
	private static final long serialVersionUID = -8565364141850705694L;
	// http://stackoverflow.com/a/10691832/281545
	private User user;
	@EJB
	// @Inject // TODO ! NBdo not inject stateful beans
	private UserService service;
	@EJB
	private OrderService os;

	public User getUser() {
		return user;
	}

	@PostConstruct
	void init() {
		// http://stackoverflow.com/questions/3406555/why-use-postconstruct
		final User sessionUser = (User) sessionGet("user");
		if (sessionUser != null) user = sessionUser;
		else user = new User();
	}

	public String login() {
		try {
			// System.out.println("Pass :" + user.getPassword());
			user = service.login(user.getUsername(), user.getPassword());
			sessionPut("user", user);
			return "/index.xhtml";
		} catch (EJBException e) {
			// System.out.println(Utils.cause(e));
			msgError("Unknown login, please try again");
			return null;
		}
	}

	public String register() {
		user = service.register(user);
		if (user.getIduser() == 0) {
			msgError("Registration failed");
			return null;
		}
		sessionPut("user", user);
		return "/index.xhtml?faces-redirect=true";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext()
			.invalidateSession();
		// System.out.println("logging out...");
		return "/index.xhtml?faces-redirect=true";
	}

	public List<Order> getOrders() {
		return os.getOrders(user);
		// final List<Order> orders = getUser().getOrders();
		// orders.size();
		// return orders; //NOPE
	}

	@ManagedBean
	@RequestScoped
	public static class UniqueUsernameValidator implements Validator {

		// Can't use a Validator (no injection) - see:
		// http://stackoverflow.com/a/7572413/281545
		@EJB
		private UserService service;

		@Override
		public void validate(FacesContext context, UIComponent component,
				Object value) throws ValidatorException {
			if (value == null) return; // Let required="true" handle, if any.
			boolean usernameUnique = false;
			try {
				usernameUnique = service.isUsernameUnique((String) value);
				// I FELL FOR CATCHING EXCEPTION OMG ! Was catching the
				// ValidatorException
			} catch (Exception ignore) {} finally {
				if (!usernameUnique) {
					throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Username is already in use.", null));
				}
			}
		}
	}
}

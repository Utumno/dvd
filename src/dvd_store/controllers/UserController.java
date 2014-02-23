package dvd_store.controllers;

import java.io.Serializable;

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

import dvd_store.entities.User;
import dvd_store.service.UserService;

import static dvd_store.faces.utils.Utils.faces;
import static dvd_store.faces.utils.Utils.sessionPut;

@ManagedBean
@ViewScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = -8565364141850705694L;
	// http://stackoverflow.com/a/10691832/281545
	private User user;
	@EJB
	// do not inject stateful beans !
	// @Inject // TODO !
	private UserService service;

	public User getUser() {
		return user;
	}

	@PostConstruct
	void init() {
		// http://stackoverflow.com/questions/3406555/why-use-postconstruct
		user = new User();
	}

	public String login() {
		try {
			// System.out.println("Pass :" + user.getPassword());
			user = service.login(user.getUsername(), user.getPassword());
			sessionPut("user", user);
			return "/index.xhtml";
		} catch (EJBException e) {
			// System.out.println(Utils.cause(e));
			faces().addMessage(
				null,
				new FacesMessage(
				FacesMessage.SEVERITY_ERROR, "Unknown login, please try again",
				null));
			return null;
		}
	}

	public String register() {
		user = service.register(user);
		if (user.getIduser() == 0) {
			faces().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Registration failed", null));
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

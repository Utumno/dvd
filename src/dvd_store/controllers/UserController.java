package dvd_store.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.NoResultException;

import dvd_store.entities.User;
import dvd_store.service.UserService;

@ManagedBean
@ViewScoped
public class UserController {

	// http://stackoverflow.com/a/10691832/281545
	private User user;
	@EJB // do not inject stateful beans !
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
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			System.out.println("Pass :" + user.getPassword());
			user = service.find(user.getUsername(), user.getPassword());
			context.getExternalContext().getSessionMap().put("user", user);
			return "/index.xhtml?faces-redirect=true";
		} catch (NoResultException e) {
			context.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_ERROR, "Unknown login, please try again",
				null));
			return null;
		}
	}

	public String register() {
		FacesContext context = FacesContext.getCurrentInstance();
		user = service.register(user);
		if (user.getIduser() == 0) {
			context.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_ERROR, "Registration failed", null));
			return null;
		}
		context.getExternalContext().getSessionMap().put("user", user);
		return "/index.xhtml?faces-redirect=true";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext()
			.invalidateSession();
		System.out.println("logging out...");
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
			if (!service.isUsernameUnique((String) value)) {
				throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Username is already in use.",
					null));
			}
		}
	}
}

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
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			// System.out.println("Pass :" + user.getPassword());
			user = service.login(user.getUsername(), user.getPassword());
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
			try {
				if (!service.isUsernameUnique((String) value)) {
					throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Username is already in use.", null));
				}
			} catch (Exception e) {
//				System.out.println(cause(e));
//				Throwable cause = e.getCause();
//				if (cause instanceof PersistenceException) {
//					Throwable cause2 = cause.getCause();
//					// ((PersistenceException)cause) - only superclass methods
//					if (cause2 instanceof DatabaseException) {
//						// now this I call ugly
//						int errorCode = ((DatabaseException) cause2)
//							.getDatabaseErrorCode(); // no java doc in eclipse
//						if (errorCode == 1406)
//							throw new ValidatorException(new FacesMessage(
//								FacesMessage.SEVERITY_ERROR, "Max 45 chars",
//								null));
//					}
//				}
//				// TODO: DEGUG
//				throw new ValidatorException(new FacesMessage(
//					FacesMessage.SEVERITY_ERROR, cause.getMessage(), null));
			}
		}

		private static String cause(Exception e) {
			StringBuilder sb = new StringBuilder("--->\nEXCEPTION:::::MSG\n"
				+ "=================\n");
			for (Throwable t = e; t != null; t = t.getCause())
				sb.append(t.getClass().getSimpleName()).append(":::::")
					.append(t.getMessage()).append("\n");
			sb.append("FIN\n\n");
			return sb.toString();
		}
	}
}

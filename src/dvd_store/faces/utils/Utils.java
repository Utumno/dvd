package dvd_store.faces.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Utils {

	private Utils() {}

	public static FacesContext faces() {
		return FacesContext.getCurrentInstance();
	}

	public static Object sessionGet(String s) {
		return faces().getExternalContext().getSessionMap().get(s);
	}

	public static void sessionPut(String s, Object o) {
		faces().getExternalContext().getSessionMap().put(s, o);
	}

	public static void msgError(String message) {
		faces().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	public static void msgInfo(String message) {
		faces().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
	}
}

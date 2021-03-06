package dvd_store.faces.i18n;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LocaleBean implements Serializable {

	private static final long serialVersionUID = 7904970623767401130L;
	private Locale locale = FacesContext.getCurrentInstance().getViewRoot()
		.getLocale();

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return locale.getLanguage();
	}

	public void setLanguage(String language) {
		this.locale = new Locale(language);
	}
}

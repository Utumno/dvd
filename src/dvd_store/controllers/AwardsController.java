package dvd_store.controllers;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import dvd_store.service.UserService;

@ManagedBean
@SessionScoped
public class AwardsController implements Serializable {

	private static final long serialVersionUID = 694437482769245947L;
	private int howMany;
	@EJB
	private UserService us;
	private List<Integer> users;

	public void mostPopular() {
		setUsers(us.bestUsers(howMany));
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public int getHowMany() {
		return howMany;
	}

	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}

	public List<Integer> getUsers() {
		return users;
	}

	public void setUsers(List<Integer> list) {
		this.users = list;
	}
}

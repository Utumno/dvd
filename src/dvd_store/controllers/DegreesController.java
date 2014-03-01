package dvd_store.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import dvd_store.service.CrewService;

@ManagedBean
@SessionScoped
public class DegreesController implements Serializable {

	private static final long serialVersionUID = -2570521514753664734L;
	private String actor1;
	private String actor2;
	private String degrees;
	@EJB
	private CrewService cs;

	public void degrees() {
		this.degrees = cs.degrees(actor1, actor2);
	}

	// =========================================================================
	// Getters Setters
	// =========================================================================
	public String getDegrees() {
		return degrees;
	}

	public String getActor1() {
		return actor1;
	}

	public void setActor1(String actor1) {
		this.actor1 = actor1;
	}

	public String getActor2() {
		return actor2;
	}

	public void setActor2(String actor2) {
		this.actor2 = actor2;
	}
}

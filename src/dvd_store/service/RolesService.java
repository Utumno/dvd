package dvd_store.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dvd_store.entities.Role;

@Singleton
public class RolesService {

	@PersistenceContext
	private EntityManager em;
	private final static List<Role> ALL = new ArrayList<>();

	@PostConstruct
	void init() {
		ALL.addAll(em.createNamedQuery("Role.findAll", Role.class)
			.getResultList());
	}

	public List<Role> all() {
		return ALL;
	}
}

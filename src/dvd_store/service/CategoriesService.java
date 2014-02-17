package dvd_store.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dvd_store.entities.Category;

@Stateless
public class CategoriesService {

	@PersistenceContext
	private EntityManager em;
	private final static List<Category> ALL = new ArrayList<>();

	@PostConstruct
	void init() {
		ALL.addAll(em.createNamedQuery("Category.findAll", Category.class)
			.getResultList());
	}

	public List<Category> all() {
		return ALL;
	}
}

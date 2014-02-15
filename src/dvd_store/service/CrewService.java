package dvd_store.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dvd_store.entities.Crew;

@Stateless
public class CrewService {

	@PersistenceContext
	private EntityManager em;

	public List<Crew> allCrew() {
		return em.createNamedQuery("Crew.findAll", Crew.class).getResultList();
	}
}

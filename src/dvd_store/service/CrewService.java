package dvd_store.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dvd_store.entities.Crew;

@Stateless
public class CrewService {

	@PersistenceContext
	private EntityManager em;

	public List<Crew> allCrew() {
		return em.createNamedQuery("Crew.findAll", Crew.class).getResultList();
	}

	public String degrees(String actor1, String actor2) {
		Query query = em.createNativeQuery("CALL r8_7_degrees(?,?)");
		short i = 0;
		query.setParameter(++i, actor1);
		query.setParameter(++i, actor2);
		System.out.println(actor1 + actor2);
		final List resultList = query.getResultList();
		StringBuilder sb = new StringBuilder();
		for (Object object :  resultList) {
			System.out.println(object + "");
			sb.append(object);
		}
		return sb.toString();
	}
}

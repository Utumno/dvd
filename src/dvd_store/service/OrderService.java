package dvd_store.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import dvd_store.entities.Address;
import dvd_store.entities.CreditCard;
import dvd_store.entities.Movie;
import dvd_store.entities.User;

@Stateless
public class OrderService {

	@PersistenceContext
	private EntityManager em;

	public Movie add(Movie m) {
		em.persist(m);
		return m;
	}

	public void addOrder(CreditCard cc, Address postalAddresss,
			Address ccAddresss, User u, Map<Movie, Integer> m) {
		StoredProcedureQuery nq = em
			.createStoredProcedureQuery("r9_insert_credit_card");
		nq.registerStoredProcedureParameter("ccnum", BigInteger.class,
			ParameterMode.IN);
		nq.registerStoredProcedureParameter("cctype", String.class,
			ParameterMode.IN);
		nq.registerStoredProcedureParameter("istreet", String.class,
			ParameterMode.IN);
		nq.registerStoredProcedureParameter("icity", String.class,
			ParameterMode.IN);
		nq.registerStoredProcedureParameter("pc", String.class,
			ParameterMode.IN);
		nq.registerStoredProcedureParameter("userid", Integer.class,
			ParameterMode.IN);
		nq.setParameter("ccnum", cc.getCreditCardNumber());
		nq.setParameter("cctype", cc.getCreditCardType());
		nq.setParameter("istreet", ccAddresss.getStreet());
		nq.setParameter("icity", ccAddresss.getCity());
		nq.setParameter("pc", ccAddresss.getPostalCode());
		nq.setParameter("userid", u.getIduser());
		// nq.registerStoredProcedureParameter("_idmovie", Integer.class,
		// ParameterMode.OUT);
		// boolean resultSet = nq.setParameter("str_title", query).execute();
		nq.execute();
	}

	public boolean isTitleUnique(String title) {
		Query query = em
			.createNativeQuery("SELECT * FROM movies WHERE title=?");
		short i = 0;
		query.setParameter(++i, title);
		try {
			query.getSingleResult();
			return false;
		} catch (Exception ignore) {
			return true;
		}
	}

	public Movie find(int id) {
		return em.find(Movie.class, id);
	}

	public List<Movie> searchTitles(String query) {
		StoredProcedureQuery nq = em.createStoredProcedureQuery(
			"r2_browse_movies_by_title", Movie.class);
		nq.registerStoredProcedureParameter("str_title", String.class,
			ParameterMode.IN);
		// nq.registerStoredProcedureParameter("_idmovie", Integer.class,
		// ParameterMode.OUT);
		// boolean resultSet = nq.setParameter("str_title", query).execute();
		return nq.setParameter("str_title", query).getResultList();
	}

	public List<Movie> searchDirector(String query) {
		StoredProcedureQuery nq = em.createStoredProcedureQuery(
			"r2_browse_movies_by_director", Movie.class);
		nq.registerStoredProcedureParameter("str_director", String.class,
			ParameterMode.IN);
		return nq.setParameter("str_director", query).getResultList();
	}

	public List<Movie> searchCategory(String query) {
		StoredProcedureQuery nq = em.createStoredProcedureQuery(
			"r2_browse_movies_by_category", Movie.class);
		nq.registerStoredProcedureParameter("str_category", String.class,
			ParameterMode.IN);
		return nq.setParameter("str_category", query).getResultList();
	}
}

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
		{
			StoredProcedureQuery _sq_ = em
				.createStoredProcedureQuery("r9_insert_credit_card");
			_sq_.registerStoredProcedureParameter("ccnum", BigInteger.class,
				ParameterMode.IN);
			_sq_.registerStoredProcedureParameter("cctype", String.class,
				ParameterMode.IN);
			_sq_.registerStoredProcedureParameter("istreet", String.class,
				ParameterMode.IN);
			_sq_.registerStoredProcedureParameter("icity", String.class,
				ParameterMode.IN);
			_sq_.registerStoredProcedureParameter("pc", String.class,
				ParameterMode.IN);
			_sq_.registerStoredProcedureParameter("userid", Integer.class,
				ParameterMode.IN);
			_sq_.setParameter("ccnum", cc.getCreditCardNumber());
			_sq_.setParameter("cctype", cc.getCreditCardType());
			_sq_.setParameter("istreet", ccAddresss.getStreet());
			_sq_.setParameter("icity", ccAddresss.getCity());
			_sq_.setParameter("pc", ccAddresss.getPostalCode());
			_sq_.setParameter("userid", u.getIduser());
			_sq_.execute();
		}
		{
			StoredProcedureQuery _sq_ = em
				.createStoredProcedureQuery("r9_insert_address");
			_sq_.registerStoredProcedureParameter("istreet", String.class,
				ParameterMode.IN);
			_sq_.registerStoredProcedureParameter("icity", String.class,
				ParameterMode.IN);
			_sq_.registerStoredProcedureParameter("pc", String.class,
				ParameterMode.IN);
			_sq_.registerStoredProcedureParameter("userid", Integer.class,
				ParameterMode.IN);
			_sq_.registerStoredProcedureParameter("adressid", Integer.class,
				ParameterMode.OUT);
			_sq_.setParameter("istreet", postalAddresss.getStreet());
			_sq_.setParameter("icity", postalAddresss.getCity());
			_sq_.setParameter("pc", postalAddresss.getPostalCode());
			_sq_.setParameter("userid", u.getIduser());
			System.out.println("EXECUTE : " + _sq_.execute());
			System.out.println("ADDRID : "
				+ _sq_.getOutputParameterValue("adressid"));
		}
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

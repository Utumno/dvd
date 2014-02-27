package dvd_store.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import dvd_store.entities.Address;
import dvd_store.entities.CreditCard;
import dvd_store.entities.Movie;
import dvd_store.entities.Order;
import dvd_store.entities.Order.ShippingInfo;
import dvd_store.entities.OrdersHasMovy;
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
			Address ccAddresss, ShippingInfo shippingInfo, User u,
			Map<Movie, Integer> m) {
		Order or = new Order();
		or.setAddress(postalAddresss);
		or.setCreditCard(cc);
		or.setDate(new Date()); // automatically on Insert ????
		or.setShippingInfo(shippingInfo);
		or.setUser(u);
		{
			// r9_insert_credit_card //
			// insert ignore into CC - update user_has_credit_cards
			// FIXME overrides address of existing credit card
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
			// r9_insert_address //
			// insert into Address ONLY WHEN ADDRESS DIFFERS - update
			// user_has_addresses
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
			_sq_.execute();
			// final boolean execute = _sq_.execute();
			// System.out.println("EXECUTE : " + execute); // false
			// em.flush(); // nothing
			// for (Parameter<?> p : _sq_.getParameters())
			// System.out.println("P: " + p);
			// System.out.println("ADDRID : "
			// + _sq_.getOutputParameterValue("adressid")); // yes!!!
			postalAddresss.setIdaddress((int) _sq_
				.getOutputParameterValue("adressid"));
		}
		em.persist(or);
		// System.out.println("ORDER ID :" + or.getIdorder()); // 0
		em.flush();
		// System.out.println("ORDER ID AFTER FLUSH :" + or.getIdorder()); // 9
		{
			List<OrdersHasMovy> ordersHasMovies = new ArrayList<>();
			for (Entry<Movie, Integer> movies : m.entrySet()) {
				final OrdersHasMovy ohm = new OrdersHasMovy();
				final Movie movie = movies.getKey();
				System.out.println("MOVIES " + movie.getTitle());
				ohm.setMovy(movie);
				final Integer quantity = movies.getValue();
				ohm.setQuantity(quantity);
				ohm.setOrder(or);
				ordersHasMovies.add(ohm);
				// remove the quantity from available movies
				movie.setAvailable(movie.getAvailable() - quantity); // this
				// check must be moved to the beginning FIXME
				em.merge(movie);
			}
			or.setOrdersHasMovies(ordersHasMovies);
		}
		em.merge(or);
		// em.persist(ccAddresss); // this double persists the address (same
		// // street/city/PC, different id)
		// em.flush();
		// System.out.println("CCADDRESSID :" + ccAddresss.getIdaddress());// ok
		// cc.setAddress(ccAddresss);
		// em.persist(cc); // this throws if I pass the same cc number. merge()
		// // throws if the card does not exist
		// // update user_has_credit_cards
		// em.persist(postalAddresss);
		// // user_has_addresses
		// em.flush();
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

	public List<Order> getOrders(User u) {
		u = em.merge(u); // the parameter u should not be assigned - why merge
							// does not use u as output parameter ?
		em.refresh(u);
		return u.getOrders();
	}
}

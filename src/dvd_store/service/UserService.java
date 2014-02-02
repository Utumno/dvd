package dvd_store.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dvd_store.entities.User;

@Stateless
public class UserService {

	@PersistenceContext
	private EntityManager em;

	public User find(String username, String password) throws NoResultException {
		return em
			.createQuery(
				"SELECT u FROM User u WHERE username = :username AND password = MD5(:password)",
				User.class).setParameter("username", username)
			.setParameter("password", password).getSingleResult();
	}

	public User register(User u) {
		Query query = em.createNativeQuery("SELECT r1_register(?,?,?,?,?,?,?)");
		short i = 0;
		query.setParameter(++i, u.getUsername());
		query.setParameter(++i, u.getPassword());
		query.setParameter(++i, u.getName());
		query.setParameter(++i, u.getSurname());
		query.setParameter(++i, u.getEmail());
		query.setParameter(++i, u.getBirthdate());
		query.setParameter(++i, u.getPhoneNumber());
		System.out.println(query);
		int id = (int) query.getSingleResult();
		System.err.println("IDDDDD : " + id);
		if (id != 0) u.setIduser(id);
		return u;
	}
}

package dvd_store.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

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
}

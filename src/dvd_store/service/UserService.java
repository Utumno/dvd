package dvd_store.service;

import java.util.List;

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

	public User login(String username, String password) {
		System.out.println("!!!!!PASS: " + password + " USER: " + username);
		User u = (User) em
			.createNativeQuery(
				"SELECT * FROM users  WHERE username=? AND password=?",
				User.class).setParameter(1, username).setParameter(2, password)
			.getSingleResult();
		try {
			em.createNativeQuery("SELECT * FROM admins  WHERE idadmin=?")
				.setParameter(1, u.getIduser()).getSingleResult();
			// no exception, user is admin
			u.setAdmin(true);
		} catch (NoResultException e) {
			// user not in the admin table
		}
		return u;
	}

	public User register(User u) {
		// Query query =
		// em.createNativeQuery("SELECT r1_register(?,?,?,?,?,?,?)");
		// short i = 0;
		// query.setParameter(++i, u.getUsername());
		// query.setParameter(++i, u.getPassword());
		// query.setParameter(++i, u.getName());
		// query.setParameter(++i, u.getSurname());
		// query.setParameter(++i, u.getEmail());
		// query.setParameter(++i, u.getBirthdate());
		// query.setParameter(++i, u.getPhoneNumber());
		// // System.out.println(query);
		em.persist(u);
		System.out.println("USER id: " + u.getIduser());
		// em.flush(); // not needed - called on exit
		// int id = (int) query.getSingleResult();
		// // System.err.println("IDDDDD : " + id);
		// if (id != 0) u.setIduser(id);
		return u;
	}

	public boolean isUsernameUnique(String username) {
		Query query = em
			.createNativeQuery("SELECT r1_check_unique_username(?)");
		short i = 0;
		query.setParameter(++i, username);
		return (boolean) query.getSingleResult();
	}

	public List<Integer> bestUsers(int howMany) {
		Query query = em.createNativeQuery("CALL r11_best_customers(?)");
		short i = 0;
		query.setParameter(++i, howMany);
		return query.getResultList();
	}
}

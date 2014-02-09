package dvd_store.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dvd_store.entities.Movie;

@Stateless
public class MovieService {

	@PersistenceContext
	private EntityManager em;

	// public User login(String username, String password)
	// throws NoResultException {
	// System.out.println("!!!!!PASS: " + password + " USER: " + username);
	// User u = (User) em
	// .createNativeQuery(
	// "SELECT * FROM users  WHERE username=? AND password=?",
	// User.class).setParameter(1, username).setParameter(2, password)
	// .getSingleResult();
	// try {
	// em.createNativeQuery("SELECT * FROM admins  WHERE idadmin=?")
	// .setParameter(1, u.getIduser()).getSingleResult();
	// // no exception, user is admin
	// u.setAdmin(true);
	// } catch (NoResultException e) {
	// // user not in the admin table
	// }
	// return u;
	// // javax.servlet.ServletException: viewId:/index.xhtml - View
	// // /index.xhtml could not be restored.
	// }

	public Movie add(Movie m) {
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
		// TODO : transactions, sql injection
		em.persist(m);
		em.flush();
		System.out.println("USER id: " + m.getIdmovie());
		// int id = (int) query.getSingleResult();
		// // System.err.println("IDDDDD : " + id);
		// if (id != 0) u.setIduser(id);
		return m;
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
}

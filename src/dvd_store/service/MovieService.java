package dvd_store.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dvd_store.entities.Crew;
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
		em.persist(m);
		// em.refresh(m); // exception
		// em.flush(); // automatically on return
		// System.out.println("MOVIE id: " + m.getIdmovie());
		return m;
	}

	public void addCrew(Movie m, Crew w) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("movie :" + m);
		List<Crew> crews = m.getCrew();
		if (crews == null) crews = new ArrayList<>();
		crews.add(w);
		em.merge(m);
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

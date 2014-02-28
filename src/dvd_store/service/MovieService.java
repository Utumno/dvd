package dvd_store.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import dvd_store.entities.Crew;
import dvd_store.entities.Movie;
import dvd_store.entities.MoviesHasCrew;
import dvd_store.entities.Role;

@Stateless
public class MovieService {

	@PersistenceContext
	private EntityManager em;

	public Movie add(Movie m) {
		em.persist(m);
		// em.refresh(m); // exception
		// em.flush(); // automatically on return
		// System.out.println("MOVIE id: " + m.getIdmovie());
		return m;
	}

	public void addCrew(Movie m, Crew w, Role role) {
		System.out.println("!!!!!!!!!!!!!!!  Hello again !!!!!!!!!!!!!");
		// System.out.println("movie :" + m.getIdmovie());
		// em.persist(m);
		// em.persist(Role.DEFAUT_ROLE);
		// System.out.println("crew :" + w.getIdcrew()); // 1
		// em.persist(w); // no diff
		// em.flush(); // no diff
		MoviesHasCrew moviesHasCrew = new MoviesHasCrew();
		moviesHasCrew.setCrew(w);
		moviesHasCrew.setMovy(m);
		moviesHasCrew.setRole(role);
		em.persist(moviesHasCrew);
		m.addMoviesHasCrew(moviesHasCrew); // if dropped I get
		// Can not refresh not managed object: dvd_store.entities.Movie@34906e9d
		// em.merge(m);
		// em.merge(w);
		// em.flush();
		// em.refresh(m);
		// em.refresh(w);
		// em.merge(m);
		// List<Crew> crews = m.getCrew();
		// if (crews == null) crews = new ArrayList<>();
		// crews.add(w);
		// em.merge(m);
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

	public void increaseCopies(Movie mov, int arrivals) {
		int available = mov.getAvailable();
		int numberOfCopies = mov.getNumberOfCopies();
		mov.setAvailable(available + arrivals);
		mov.setNumberOfCopies(numberOfCopies + arrivals);
		em.merge(mov);
	}

	// =========================================================================
	// Browse Movies
	// =========================================================================
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

	// =========================================================================
	// Popular Movies/Actors/Directors
	// =========================================================================
	public List<Movie> popularMovies(int howMany) {
		StoredProcedureQuery nq = em.createStoredProcedureQuery(
			"r10_ most_popular_movies", Movie.class);
		nq.registerStoredProcedureParameter("m", Integer.class,
			ParameterMode.IN);
		return nq.setParameter("m", howMany).getResultList();
	}

	public List<Crew> popActors(int howMany) {
		throw new RuntimeException("NOT IMPLEMENTED");
	}

	public List<Crew> popDirectors(int howMany) {
		throw new RuntimeException("NOT IMPLEMENTED");
	}
}

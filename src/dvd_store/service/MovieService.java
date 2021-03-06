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
import dvd_store.entities.Movie.Rating;
import dvd_store.entities.MoviesHasCrew;
import dvd_store.entities.Role;
import dvd_store.entities.User;

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
			"r10_most_popular_movies", Movie.class);
		nq.registerStoredProcedureParameter("m", Integer.class,
			ParameterMode.IN);
		return nq.setParameter("m", howMany).getResultList();
	}

	public List<String> popActors(int howMany) {
		Query query = em.createNativeQuery("CALL r10_most_popular_actors(?)");
		short i = 0;
		query.setParameter(++i, howMany);
		return query.getResultList();
		// StoredProcedureQuery nq = em.createStoredProcedureQuery(
		// "r10_most_popular_actors", String.class);
		// nq.registerStoredProcedureParameter("m", Integer.class,
		// ParameterMode.IN);
		// nq.setParameter("m", howMany);
		// System.out
		// .println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
		// + howMany);
		// final List resultList = nq.getResultList();
		// System.out
		// .println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
		// + resultList);
		// return resultList;
	}

	public List<String> popDirectors(int howMany) {
		Query query = em
			.createNativeQuery("CALL r10_most_popular_directors(?)");
		short i = 0;
		query.setParameter(++i, howMany);
		return query.getResultList();
	}

	public List<Movie> andvancedSearch(int sort_type, String iactors,
			String director, String title_words, Rating irating) {
		Query query = em.createNativeQuery(
			"CALL r6EnhancedBrowsing(?,?,?,?,?)", Movie.class);
		short i = 0;
		query.setParameter(++i, sort_type);
		query.setParameter(++i, (iactors == null) ? "" : iactors);
		query.setParameter(++i, (director == null) ? "" : director);
		query.setParameter(++i, (title_words == null) ? "" : title_words);
		query.setParameter(++i, (irating == null) ? "" : irating);
		final List resultList = query.getResultList();
		System.out.println("****************" + resultList);
		for (Object object : resultList) {
			System.out.println(object);
		}
		// for (Object[] object : (List<Object[]>) resultList) {
		// System.out.println(object);
		// for (Object object2 : object) {
		// System.out.println(object2);
		// }
		// }
		return resultList;
		// StoredProcedureQuery nq = em.createStoredProcedureQuery(
		// "r6EnhancedBrowsing", Movie.class);
		// nq.registerStoredProcedureParameter("sort_type", Integer.class,
		// ParameterMode.IN);
		// nq.registerStoredProcedureParameter("iactors", String.class,
		// ParameterMode.IN);
		// nq.registerStoredProcedureParameter("director", String.class,
		// ParameterMode.IN);
		// nq.registerStoredProcedureParameter("title_words", String.class,
		// ParameterMode.IN);
		// nq.registerStoredProcedureParameter("irating", Rating.class,
		// ParameterMode.IN);
		// nq.setParameter("sort_type", sort_type);
		// nq.setParameter("iactors", (iactors == null) ? "" : iactors);
		// nq.setParameter("director", (director == null) ? "" : director);
		// nq.setParameter("title_words", (title_words == null) ? "" :
		// title_words);
		// nq.setParameter("irating", irating);
	}

	public List<Movie> getSuggetions(Movie movie, User u) {
		StoredProcedureQuery nq = em.createStoredProcedureQuery(
			"r7BuyingSuggestions", Movie.class);
		nq.registerStoredProcedureParameter("userid", Integer.class,
			ParameterMode.IN);
		nq.registerStoredProcedureParameter("movieid", Integer.class,
			ParameterMode.IN);
		return nq.setParameter("userid", u.getIduser())
			.setParameter("movieid", movie.getIdmovie()).getResultList();
		// Query query = em
		// .createNativeQuery("CALL r7BuyingSuggestions(?,?)");
		// short i = 0;
		// query.setParameter(++i, howMany);
		// query.setParameter(++i, howMany);
		// return query.getResultList();
	}
}

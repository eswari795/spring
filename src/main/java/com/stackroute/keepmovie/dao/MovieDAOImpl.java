package com.stackroute.keepmovie.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepmovie.model.Movie;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus 
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */

@Repository
@Transactional
public class MovieDAOImpl implements MovieDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */

	private SessionFactory sessionFactory;

	public MovieDAOImpl() {

	}

	@Autowired
	public MovieDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Save the note in the database(note) table.
	 */

	@Override
	public boolean saveMovie(Movie movie) {
		Session session = sessionFactory.getCurrentSession();
		session.save(movie);
		sessionFactory.getCurrentSession().flush();

		return true;
	}


	/*
	 * Remove the note from the database(note) table.
	 */
	@Override
	public boolean deleteMovie(int movieId) {
		sessionFactory.getCurrentSession().delete(getMovieById(movieId));
		sessionFactory.getCurrentSession().flush();
		return true;

	}


	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	@Override
	public List<Movie> getAllMovies() {
		String hql = "FROM Movie movie";
		Query qry = getSessionFactory().getCurrentSession().createQuery(hql);
		return qry.getResultList();

	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	
	@Override
	public Movie getMovieById(int movieId) {
		Session session = sessionFactory.getCurrentSession();
		Movie note = session.get(Movie.class, movieId);
		sessionFactory.getCurrentSession().flush();
		return note;
	}

	/* Update existing note */
	@Override
	public boolean updateMovie(Movie movie) {
		sessionFactory.getCurrentSession().clear();
		sessionFactory.getCurrentSession().update(movie);
		sessionFactory.getCurrentSession().flush();
		return true;
	}

}

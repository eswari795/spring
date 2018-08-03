package com.stackroute.keepmovie.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepmovie.dao.MovieDAO;
import com.stackroute.keepmovie.model.Movie;

/*
 * Annotate the class with @Controller annotation.@Controller annotation is used to mark 
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 */

@RestController
@RequestMapping(value = "/api/v1/")
public class MovieController {

	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the persistence data. Each note
	 * should contain Note Id, title, content, status and created date. 2. Add a new
	 * note which should contain the note id, title, content and status. 3. Delete
	 * an existing note 4. Update an existing note
	 * 
	 */

	/*
	 * Autowiring should be implemented for the NoteDAO. Create a Note object.
	 * 
	 */
	

	private MovieDAO movieDao;

	@Autowired
	public MovieController(MovieDAO movieDao) {

		this.movieDao = movieDao;
	}

	/*
	 * Define a handler method to read the existing notes from the database and add
	 * it to the ModelMap which is an implementation of Map, used when building
	 * model data for use with views. it should map to the default URL i.e. "/index"
	 */

	@RequestMapping("/")
	public String hello() {
		return "Hi app is working";
	}

	 @RequestMapping(value = "/movies", method = RequestMethod.GET, produces = {"application/json" })
	 public String getAllMovies() {
	
	 return movieDao.getAllMovies().toString();
	
	 }

	/*
	 * Define a handler method which will read the NoteTitle, NoteContent,
	 * NoteStatus from request parameters and save the note in note table in
	 * database. Please note that the CreatedAt should always be auto populated with
	 * system time and should not be accepted from the user. Also, after saving the
	 * note, it should show the same along with existing messages. Hence, reading
	 * note has to be done here again and the retrieved notes object should be sent
	 * back to the view using ModelMap This handler method should map to the URL
	 * "/add".
	 */

	@RequestMapping(value = "/movie", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<?> addNoteToDB(@RequestBody Movie movie) {
		System.out.println("inside post" + movie);

		if (movieDao.saveMovie(movie)) {
			return new ResponseEntity<String>("{ \"message\": \"" + "Success" + "\"}", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("{ \"message\": \"" + "failure" + "\"}", HttpStatus.CONFLICT);
		}

	}

	/*
	 * Define a handler method which will read the NoteId from request parameters
	 * and remove an existing note by calling the deleteNote() method of the
	 * NoteRepository class.This handler method should map to the URL "/delete".
	 */
	@RequestMapping(value = "/movie/{movieId}", method = RequestMethod.DELETE, produces = { "application/json" })
	public ResponseEntity<String> deleteNotefromDB(@PathVariable("movieId") int id) {
		if(movieDao.deleteMovie(id))
		{
		return new ResponseEntity<String>("{ \"message\": \"" + "Deleted" + "\"}", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("{ \"message\": \"" + "failure" + "\"}", HttpStatus.CONFLICT);
				
		}
	}

	/*
	 * Define a handler method which will update the existing note. This handler
	 * method should map to the URL "/note" resource with PUT.
	 */
	@RequestMapping(value = "/movie", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<String> updateNoteToDB(@RequestBody Movie movie) {

		movieDao.updateMovie(movie);

		return new ResponseEntity<String>("{ \"message\": \"" + "Updated" + "\"}", HttpStatus.OK);

	}

	@RequestMapping(value = "/movie", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getById(@RequestParam int movieId) {
		return new ResponseEntity<Movie>(movieDao.getMovieById(movieId), HttpStatus.FOUND);

	}
}

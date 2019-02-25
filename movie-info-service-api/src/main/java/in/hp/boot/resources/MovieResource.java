package in.hp.boot.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.hp.boot.models.Movie;

@RestController
@RequestMapping("/movies")
public class MovieResource {

	@RequestMapping("/{movieId}")
	public Movie getMovie(@PathVariable String movieId) {
		return new Movie(movieId, "Thor");
	}
}

package in.hp.boot.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import in.hp.boot.models.CatalogItem;
import in.hp.boot.models.Movie;
import in.hp.boot.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {
		/*
		 * 1. Get all rated movie ID's
		 * 2. For each movie ID, call movie info service and get details
		 * 3. Combine the response and send
		 * 
		 * For calling another rest service, we use RestTemplate
		 */
		
		// Creating instance of RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		
		// Hardcoding few Ratings for Testing
		List<Rating> ratings = Arrays.asList(
				new Rating("1245", 8),
				new Rating("2345", 7)
		);
		
		// Calling MovieInfoService for each movieId retrieved from Rating service
		/*
		return ratings.stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
			new CatalogItem(movie.getName(), "Description", rating.getRating());	
		})
		.collect(Collectors.toList());
		*/
		List<CatalogItem> returnList = new ArrayList<>();
		ratings.stream().forEach(rating -> {
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
			returnList.add(new CatalogItem(movie.getName(), "Description", rating.getRating()));
		});
		
		return returnList;
		
		/*
		// TODO learn what is Collections.singletonList means
		return Collections.singletonList(
				new CatalogItem("Iron Man", "Marvel's first movie", 9)
				);
		*/
	}
}

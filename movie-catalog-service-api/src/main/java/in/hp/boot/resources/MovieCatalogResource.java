package in.hp.boot.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import in.hp.boot.models.CatalogItem;
import in.hp.boot.models.Movie;
import in.hp.boot.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	/*
	 * Injecting from @Bean annotation
	 */
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * We can use DiscoveryClient interface to provide load balancing as well
	@Autowired
	private DiscoveryClient discoveryClient;
	*/
	
	/**
	 * How to use WebClient to make Rest calls
	 * 
	 * webClientBuilder.build() -> Builder pattern standard
	 * .get() 		-> Implies rest method GET
	 * .uri() 		-> URL to make the rest call
	 * .retrieve() 	-> To retrieve the object
	 * .bodyToMono()-> Reactive way of saying there is an object of specified type coming but sooner or later
	 * .block()		-> Sync or Async, making the process to wait here
	*/
	 /*
	@Autowired
	private WebClient.Builder webClientBuilder;
	 
	Movie movie = webClientBuilder.build()
					.get()
					.uri("http://localhost:8082/movies/" + rating.getMovieId())
					.retrieve()
					.bodyToMono(Movie.class)
					.block();
	*/
	
	/**
	 * 1. Get all rated movies for a supplied userId
	 * 2. For each movie ID, call movie info service and get details
	 * 3. Combine the response and send 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/{userId}")
	@HystrixCommand(fallbackMethod = "getFallBackCatalog")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {

		UserRating userRating= restTemplate.getForObject(
				"http://ratings-data-service/ratingsdata/users/" + userId,
				UserRating.class);

		return userRating.getUserRatings().stream().map(rating -> {
			Movie movie = restTemplate.getForObject(
					"http://movie-info-service/movies/" + rating.getMovieId(),
					Movie.class);
			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
		}).collect(Collectors.toList());
	}
	
	/**
	 * Fallback method, which will be called when circuit is broken
	 * @param userId
	 * @return
	 */
	public List<CatalogItem> getFallBackCatalog(@PathVariable String userId) {
		return Arrays.asList(new CatalogItem("No Movie", "-", -1));
	}
}

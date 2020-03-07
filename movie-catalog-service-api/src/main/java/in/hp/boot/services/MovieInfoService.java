package in.hp.boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import in.hp.boot.models.CatalogItem;
import in.hp.boot.models.Movie;
import in.hp.boot.models.Rating;

@Service
public class MovieInfoService {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Added Hystrix Configuration Parameters
	 * Many other parameters are available, check Netflix Hystrix Wiki
	 * @since 07-MAR-20
	 * @param rating
	 * @return
	 * 
	 * execution.isolation.thread.timeoutInMilliseconds
	 * 	how long to wait until the thread / api request timeouts, default 1sec
	 * 
	 * circuitBreaker.requestVolumeThreshold
	 * 	how many requests to consider for circuit change, default value 20
	 * 
	 * circuitBreaker.errorThresholdPercentage
	 * 	in the requestVolumeThreshold, how much percentage error to tolerate before tripping the circuit
	 * 	default value 50
	 * 
	 * circuitBreaker.sleepWindowInMilliseconds
	 * 	amount of time to wait till to send the next request, after the circuit is tripped
	 */
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem", commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
			})
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}

	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("No Movie Name", "", rating.getRating());
	}

}

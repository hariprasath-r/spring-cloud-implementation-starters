package in.hp.boot.services;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import in.hp.boot.models.Rating;
import in.hp.boot.models.UserRating;

@Service
public class RatingDataService {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @since 07-MAR-20
	 * 
	 * Thread pool configuration to implement bulk head design pattern
	 * Having reserved thread pool size for each service calls
	 * 
	 * coreSize - size of thread pool
	 * maxQueueSize - maximum requests / threads to hold in wait queue
	 * 
	 * @param userId
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "getFallbackUserRating", threadPoolKey = "ratingDataPool",
				threadPoolProperties = {
						@HystrixProperty(name = "coreSize", value = "5"),
						@HystrixProperty(name = "maxQueueSize", value = "5")
				}
			)
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
	}

	public UserRating getFallbackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRatings(Stream.of(new Rating("100", 9), new Rating("200", 8)).collect(Collectors.toList()));
		return userRating;
	}

}

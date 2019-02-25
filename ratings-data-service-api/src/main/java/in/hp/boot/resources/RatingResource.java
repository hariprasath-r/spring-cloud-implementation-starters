package in.hp.boot.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.hp.boot.models.Rating;
import in.hp.boot.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId) {
		return new Rating(movieId, 9);
	}

	/**
	 * Here we are returning an UserRating object instead of returning a List
	 * Because this provides backward compatibility, as we can add new property and consuming code will not break
	 * @param userId
	 * @return
	 */
	@RequestMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable String userId) {
		List<Rating> ratings = Arrays.asList(
				new Rating("1245", 8),
				new Rating("2345", 7)
		);
		UserRating userRating = new UserRating();
		userRating.setUserRatings(ratings);
		return userRating;
	}
}

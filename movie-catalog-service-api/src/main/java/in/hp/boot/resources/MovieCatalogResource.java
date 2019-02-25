package in.hp.boot.resources;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.hp.boot.models.CatalogItem;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {
		/*
		 * TODO learn what is Collections.singletonList means
		 */
		return Collections.singletonList(
				new CatalogItem("Iron Man", "Marvel's first movie", 9)
				);
		
	}
}

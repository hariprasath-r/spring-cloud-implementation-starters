package in.hp.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MovieCatalogServiceApiApplication {

	/**
	 * The @Bean annotation is used to make this particular method singleton
	 * The return type of this method can be autowired
	 * @return
	 */
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * WebClient.Builder is reactive programming class
	 * Which plays around with Mono and Flux objects
	 * It follows builder design pattern
	 * from the dependency springboot-starter-webflux
	 * @return
	 */
	@Bean
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApiApplication.class, args);
	}

}

package in.hp.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * The @EnableEurekaClient is an optional annotation, before it was mandate.
 * 
 * @EnableDiscoveryClient annotation will work seemlessly if the eureka discovery
 * is replaced with some other discovery client
 * 
 * @author Hariprasath
 *
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
public class MovieCatalogServiceApiApplication {

	/**
	 * The @Bean annotation is used to make this particular method singleton
	 * The return type of this method can be autowired
	 * 
	 * @LoadBalanced Annotation provides client side load balancing using Eureka server
	 * 	All we have to do is annotate while getting RestTemplate and pass in the application name mentioned in
	 * 	application.yml file and the RestTemplate takes care of everything
	 * 
	 * The parameter clientHttpRequestFactory is used to timeout the external api call after that interval
	 * We need timeout so that the thread will not wait for a long time
	 * 
	 * Better approach is using circuit breakers
	 * 
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
		
		/*
		 * Adding Connection Timeout
		 * 
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(2000);
		return new RestTemplate(clientHttpRequestFactory);
		*/
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

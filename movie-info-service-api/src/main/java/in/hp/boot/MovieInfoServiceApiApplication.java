package in.hp.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient	
public class MovieInfoServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieInfoServiceApiApplication.class, args);
	}
	
	@Bean
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

}

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.demo.accounts.WebAccountsService;
import com.example.demo.posts.WebPostsService;

@SpringBootApplication
@EnableDiscoveryClient
public class CliApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "cli-application");
		SpringApplication.run(CliApplication.class, args);
	}
	
	/**
     * URL uses the logical name of account-service - upper or lower case, doesn't
     * matter.
     */
    public static final String ACCOUNTS_SERVICE_URL = "http://ACCOUNTS-SERVICE";
    
    /**
     * URL uses the logical name of posts-service - upper or lower case, doesn't
     * matter.
     */
    public static final String POSTS_SERVICE_URL = "http://POSTS-SERVICE";
	
    /**
     * A customized RestTemplate that has the ribbon load balancer build in. Note
     * that prior to the "Brixton"
     * 
     * @return
     */
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * The AccountService encapsulates the interaction with the micro-service.
     * 
     * @return A new service instance.
     */
    @Bean
    public WebAccountsService accountsService() {
        return new WebAccountsService(ACCOUNTS_SERVICE_URL);
    }
    
    /**
     * The PostsService encapsulates the interaction with the micro-service.
     * 
     * @return A new service instance.
     */
    @Bean
    public WebPostsService postsService() {
        return new WebPostsService(POSTS_SERVICE_URL);
    }

}

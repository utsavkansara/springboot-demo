package com.example.demo.posts;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

/**
 * Hide the access to the microservice inside this local service.
 * 
 * @author Aayushi Raval
 */
public class WebPostsService {

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(WebPostsService.class.getName());

    public WebPostsService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
    }

    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show this.
     */
    @PostConstruct
    public void demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("The RestTemplate request factory is " + restTemplate.getRequestFactory().getClass());
    }
    
    public Post findPostById(String postId) {
    	return restTemplate.getForObject(serviceUrl + "/posts/{postId}", Post.class, postId);
    }

//    public Account findByNumber(String accountNumber) {
//
//        logger.info("findByNumber() invoked: for " + accountNumber);
//        try {
//            return restTemplate.getForObject(serviceUrl + "/accounts/{number}", Account.class, accountNumber);
//        } catch (Exception e) {
//            logger.severe(e.getClass() + ": " + e.getLocalizedMessage());
//            return null;
//        }
//
//    }
//
//    public List<Account> byOwnerContains(String name) {
//        logger.info("byOwnerContains() invoked:  for " + name);
//        Account[] accounts = null;
//
//        try {
//            accounts = restTemplate.getForObject(serviceUrl + "/accounts/owner/{name}", Account[].class, name);
//        } catch (HttpClientErrorException e) { // 404
//            // Nothing found
//        }
//
//        if (accounts == null || accounts.length == 0)
//            return null;
//        else
//            return Arrays.asList(accounts);
//    }
//
//    public Account getByNumber(String accountNumber) {
//        Account account = restTemplate.getForObject(serviceUrl + "/accounts/{number}", Account.class, accountNumber);
//
//        if (account == null)
//            throw new AccountNotFoundException(accountNumber);
//        else
//            return account;
//    }
}

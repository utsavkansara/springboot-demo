package com.example.demo.posts;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.exceptions.GeneralException;

/**
 * Webservice client to interact with Posts REST service.
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
    	try {
    		return restTemplate.getForObject(serviceUrl + "/posts/{postId}", Post.class, postId);
    	} catch (Exception e) {
    		logger.severe(e.getClass() + ": " + e.getLocalizedMessage());
    		return null;
    	}
    	
    }
    
    public void createPost(Post post) {
    	try {
    		restTemplate.postForLocation(serviceUrl + "/posts", post);
    	} catch (Exception e) {
    		logger.severe(e.getClass() + ": " + e.getLocalizedMessage());   
    		throw new GeneralException("Unable to save Post");
    	}    	
    }
    
	public List<Post> listAllThreads() {
		try {
			ResponseEntity<Post[]> response = restTemplate.getForEntity(serviceUrl + "/threads", Post[].class);
			Post[] posts = response.getBody();
			return Arrays.asList(posts);
		} catch (Exception e) {
			logger.severe(e.getClass() + ": " + e.getLocalizedMessage());
			throw new GeneralException("Unable to retrieve threads");
		}
	}
    
    public List<Post> listAllChildPosts(String postid) {
    	try {
    		ResponseEntity<Post[]> response = restTemplate.getForEntity(serviceUrl + "/childposts/{postId}", Post[].class, postid);
			Post[] posts = response.getBody();
			return Arrays.asList(posts);
    	} catch (Exception e) {
    		logger.severe(e.getClass() + ": " + e.getLocalizedMessage());   
    		throw new GeneralException("Unable to retrieve replies on post with id: "+postid);
    	}    	
    }
    
    public List<Post> listAllPosts() {
    	try {
    		ResponseEntity<Post[]> response = restTemplate.getForEntity(serviceUrl + "/posts", Post[].class);
			Post[] posts = response.getBody();
			return Arrays.asList(posts);
    	} catch (Exception e) {
    		logger.severe(e.getClass() + ": " + e.getLocalizedMessage());   
    		throw new GeneralException("Unable to retrieve posts");
    	}    	
    }
}

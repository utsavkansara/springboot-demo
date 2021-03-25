package io.pivotal.microservices.posts;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RESTFul controller for accessing posts information.
 * 
 * @author Aayushi Raval
 */
@RestController
public class PostsController {

	protected Logger logger = Logger.getLogger(PostsController.class
			.getName());
	protected PostRepository postRepository;

	@Autowired
	public PostsController(PostRepository postRepository) {
		this.postRepository = postRepository;

		logger.info("PostRepository says system has "
				+ postRepository.countPosts() + " posts");
	}

	@RequestMapping(value = "/posts", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createPost(@RequestBody Post post) {
		logger.info("post-service createPost() invoked with body: " + post);
		postRepository.save(post);
	}
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Post> getPosts() {
		logger.info("post-service getPosts() invoked");
		return postRepository.findAll();
	}
	
	@RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Post getPostsById(@PathVariable("postId") long postId) {
		logger.info("post-service getPostsById() invoked");
		return postRepository.findById(postId).get();
	}
	
	@RequestMapping(value = "/threads", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Post> getThreads() {
		logger.info("post-service getThreads() invoked");
		return postRepository.findByParentPostIdIsNull();
	}
	
	@RequestMapping(value = "/childposts/{parentPostId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Post> getChildPostsByParentPostId(@PathVariable("parentPostId") long parentPostId) {
		logger.info("post-service getChildPostsByParentPostId() invoked");
		return postRepository.findByParentPostId(parentPostId);
	}
}

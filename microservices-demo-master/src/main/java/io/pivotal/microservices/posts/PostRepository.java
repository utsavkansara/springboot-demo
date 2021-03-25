package io.pivotal.microservices.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for Post data implemented using Spring Data JPA.
 * 
 * @author Paul Chapman
 */
public interface PostRepository extends JpaRepository<Post, Long> {

	/**
	 * Fetch the number of accounts known to the system.
	 * 
	 * @return The number of accounts.
	 */
	@Query("SELECT count(*) from Post")
	public int countPosts();
	
	public List<Post> findByParentPostIdIsNull();
	
	public List<Post> findByParentPostId(long parentPostId);
}

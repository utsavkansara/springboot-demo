package io.pivotal.microservices.posts;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Persistent post entity with JPA markup. Posts are stored in an H2
 * relational database.
 * 
 * @author Aayushi Raval
 */
@Entity
@Table(name = "T_POST")
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long postId;

	private String subject;

	private String body;

	private Long parentPostId;

	public Long getPostId() {
		return postId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getParentPostId() {
		return parentPostId;
	}

	public void setParentPostId(Long parentPostId) {
		this.parentPostId = parentPostId;
	}

}

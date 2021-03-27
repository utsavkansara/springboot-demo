package com.example.demo.posts;

import java.io.Serializable;

/**
 * POJO to hold Post data
 * 
 * @author Aayushi Raval
 */
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long postId;
	private String subject;
	private String body;
	private Long parentPostId;
	
	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
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
	
	@Override
	public String toString() {
		return "[postId=" + postId + ", subject=" + subject + ", body=" + body + ", parentPostId=" + parentPostId
				+ "]";
	}
	
}

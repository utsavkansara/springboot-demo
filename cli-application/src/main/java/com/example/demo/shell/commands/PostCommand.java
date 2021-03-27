package com.example.demo.shell.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.demo.exceptions.GeneralException;
import com.example.demo.posts.Post;
import com.example.demo.posts.WebPostsService;
import com.example.demo.shell.config.InputReader;
import com.example.demo.shell.config.ShellHelper;
import com.example.demo.shell.security.SecuredCommand;

@ShellComponent
public class PostCommand extends SecuredCommand {
	
	@Lazy
	@Autowired
	ShellHelper shellHelper;

	@Lazy
	@Autowired
	InputReader inputReader;
	
	@Autowired
	WebPostsService webPostsService;	
	
	@ShellMethod("Create a new thread")
	@ShellMethodAvailability("isUserSignedIn")
	public void createNewThread() {
		Post post = new Post();
		
		// 1. read subject --------------------------------------------
		do {
			String subject = inputReader.prompt("Subject");
			if (StringUtils.hasText(subject)) {
				post.setSubject(subject);
			} else {
				shellHelper.printWarning("Subject of thread CAN NOT be empty string. Please enter valid value!");
			}
		} while (!StringUtils.hasText(post.getSubject()));
		
		// 2. read body -----------------------------------------------
		do {
			String body = inputReader.prompt("Body");
			if (StringUtils.hasText(body)) {
				post.setBody(body);
			} else {
				shellHelper.printWarning("Body of thread CAN NOT be empty string. Please enter valid value!");
			}
		} while (!StringUtils.hasText(post.getBody()));

		// Print user's input -------------------------------------------------
		shellHelper.printInfo("\nCreating Thread:");
		shellHelper.print("\nSubject: " + post.getSubject());
		shellHelper.print("Body: " + post.getBody()+ "\n");

		webPostsService.createPost(post);
		shellHelper.printSuccess("Created thread successfully");
	}

	@ShellMethod("Reply to a post or a thread. Pass thread post id or parent post id based on requirement")
	@ShellMethodAvailability("isUserSignedIn")
	public void replyToAThreadOrPost() {
		Post post = new Post();
		
		// 1. read subject --------------------------------------------
		String subject = inputReader.prompt("Subject");
		post.setSubject(subject);	
		
		// 2. read body -----------------------------------------------
		do {
			String body = inputReader.prompt("Body");
			if (StringUtils.hasText(body)) {
				post.setBody(body);
			} else {
				shellHelper.printWarning("Body CAN NOT be empty string. Please enter valid value!");
			}
		} while (!StringUtils.hasText(post.getBody()));
		
		// 3. read thread post id or parent post id -----------------------------------------------
		do {
			String parentPostId = inputReader.prompt("threadpostid/parentpostid");
			if (StringUtils.hasText(parentPostId)) {				
				if(webPostsService.findPostById(parentPostId) == null) {
					shellHelper.printWarning("threadpostid/parentpostid doesn't exist in the system. Please enter valid value!");
				} else {
					post.setParentPostId(Long.valueOf(parentPostId));
				}
			} else {
				shellHelper.printWarning("threadpostid/parentpostid CAN NOT be empty string while creating reply. Please enter valid value!");
			}
		} while (!StringUtils.hasText(post.getBody()));

		// Print user's input -------------------------------------------------
		shellHelper.printInfo("\nCreating Reply post:");
		shellHelper.print("\nSubject: " + post.getSubject());
		shellHelper.print("\nBody: " + post.getBody());
		shellHelper.print("threadid/parentpostid: " + post.getParentPostId()+ "\n");

		webPostsService.createPost(post);
		shellHelper.printSuccess("Submitted reply successfully");
	}
	
	@ShellMethod("List all threads")
	@ShellMethodAvailability("isUserSignedIn")
	public void listAllThreads() {
		List<Post> threads = webPostsService.listAllThreads();
		if(!CollectionUtils.isEmpty(threads)) {
			for(Post thread: threads) {
				shellHelper.printSuccess(thread.toString());
			}
		}else {
			shellHelper.printSuccess("No threads found !");
		}
	}
	
	@ShellMethod("List of replies on the post")
	@ShellMethodAvailability("isUserSignedIn")
	public void listRepliesOnThePost(@ShellOption({"-P", "--postid"}) String postid) {
		Post post = webPostsService.findPostById(postid);
		if(post == null) {
			throw new GeneralException("Post not found");
		}
		
		List<Post> threads = webPostsService.listAllChildPosts(postid);
		if(!CollectionUtils.isEmpty(threads)) {
			for(Post thread: threads) {
				shellHelper.printSuccess(thread.toString());
			}
		}else {
			shellHelper.printSuccess("No replies found !");
		}		
	}
	
	@ShellMethod("View a post by id")
	@ShellMethodAvailability("isUserSignedIn")
	public void getPost(@ShellOption({"-P", "--postid"}) String postid) {
		Post post = webPostsService.findPostById(postid);
		if(post == null) {
			throw new GeneralException("Post not found");
		}
		shellHelper.printSuccess(post.toString());
	}
	
	@ShellMethod("List all the posts from the system")
	@ShellMethodAvailability("isUserSignedIn")
	public void getAllPosts() {
		List<Post> posts = webPostsService.listAllPosts();
		if(!CollectionUtils.isEmpty(posts)) {
			for(Post post: posts) {
				shellHelper.printSuccess(post.toString());
			}
		}else {
			shellHelper.printSuccess("No posts found in the system!");
		}
	}
}

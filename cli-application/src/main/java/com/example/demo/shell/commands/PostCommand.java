package com.example.demo.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import com.example.demo.posts.Post;
import com.example.demo.posts.WebPostsService;
import com.example.demo.shell.config.InputReader;
import com.example.demo.shell.config.ShellHelper;

@ShellComponent
public class PostCommand {
	
	@Lazy
	@Autowired
	ShellHelper shellHelper;

	@Lazy
	@Autowired
	InputReader inputReader;
	
	@Autowired
	WebPostsService webPostsService;
	
	@ShellMethod("Create a new post")
	@ShellMethodAvailability("isUserSignedIn")
	public void createPost() {
//		if (userService.exists(username)) {
//			shellHelper.printError(String.format("User with username='%s' already exists --> ABORTING", username));
//			return;
//		}
//		CliUser user = new CliUser();
//		user.setUsername(username);
//
//		// 1. read user's fullName --------------------------------------------
//		do {
//			String fullName = inputReader.prompt("Full name");
//			if (StringUtils.hasText(fullName)) {
//				user.setFullName(fullName);
//			} else {
//				shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
//			}
//		} while (user.getFullName() == null);
//
//		// 2. read user's password --------------------------------------------
//		do {
//			String password = inputReader.prompt("Password", "secret", false);
//			if (StringUtils.hasText(password)) {
//				user.setPassword(password);
//			} else {
//				shellHelper.printWarning("Password'CAN NOT be empty string? Please enter valid value!");
//			}
//		} while (user.getPassword() == null);
//
//		// 3. read user's Gender ----------------------------------------------
//		Map<String, String> options = new HashMap<>();
//		options.put("M", Gender.MALE.name());
//		options.put("F", Gender.FEMALE.name());
//		options.put("D", Gender.DIVERSE.name());
//		String genderValue = inputReader.selectFromList("Gender", "Please enter one of the [] values", options, true,
//				null);
//		Gender gender = Gender.valueOf(options.get(genderValue.toUpperCase()));
//		user.setGender(gender);
//		
//		// 4. Prompt for superuser attribute ------------------------------
//		String superuserValue = inputReader.promptWithOptions("New user is superuser", "N", Arrays.asList("Y", "N"));
//		if ("Y".equals(superuserValue)) {
//		    user.setSuperuser(true);
//		} else {
//		    user.setSuperuser(false);
//		}
//
//		// Print user's input -------------------------------------------------
//		shellHelper.printInfo("\nCreating new user:");
//		shellHelper.print("\nUsername: " + user.getUsername());
//		shellHelper.print("Password: " + user.getPassword());
//		shellHelper.print("Fullname: " + user.getFullName());
//		shellHelper.print("Gender: " + user.getGender());
//		shellHelper.print("Superuser: " + user.isSuperuser() + "\n");
//
//		CliUser createdUser = userService.create(user);
//		shellHelper.printSuccess("Created user with id=" + createdUser.getId());
	}
	
	@ShellMethod("Reply to a post")
	@ShellMethodAvailability("isUserSignedIn")
	public void replyToPost() {
		
	}
	
	@ShellMethod("List all threads")
	@ShellMethodAvailability("isUserSignedIn")
	public void listAllThreads() {
		
	}
	
	@ShellMethod("List replies on the post")
	@ShellMethodAvailability("isUserSignedIn")
	public void listRepliesOnThePost() {
		
	}
	
	@ShellMethod("View a post")
//	@ShellMethodAvailability("isUserSignedIn")
	public void getPost() {
		Post post = webPostsService.findPostById("1");
		shellHelper.printSuccess(post.toString());
	}
}

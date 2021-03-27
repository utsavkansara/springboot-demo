package com.example.demo.shell.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import com.example.demo.shell.config.InputReader;
import com.example.demo.shell.config.ShellHelper;
import com.example.demo.shell.security.SecuredCommand;
import com.example.demo.user.model.CliUser;
import com.example.demo.user.model.Gender;
import com.example.demo.user.service.UserService;

@ShellComponent
public class UserCommand extends SecuredCommand {

	@Autowired
	ShellHelper shellHelper;

	@Autowired
	InputReader inputReader;

	@Autowired
	UserService userService;

	@ShellMethod("Create new user with supplied username")
	@ShellMethodAvailability("isUserSignedIn")
	public void createUser(@ShellOption({ "-U", "--username" }) String username) {
		if (userService.exists(username)) {
			shellHelper.printError(String.format("User with username='%s' already exists --> ABORTING", username));
			return;
		}
		CliUser user = new CliUser();
		user.setUsername(username);

		// 1. read user's fullName --------------------------------------------
		do {
			String fullName = inputReader.prompt("Full name");
			if (StringUtils.hasText(fullName)) {
				user.setFullName(fullName);
			} else {
				shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
			}
		} while (user.getFullName() == null);

		// 2. read user's password --------------------------------------------
		do {
			String password = inputReader.prompt("Password", "secret", false);
			if (StringUtils.hasText(password)) {
				user.setPassword(password);
			} else {
				shellHelper.printWarning("Password'CAN NOT be empty string? Please enter valid value!");
			}
		} while (user.getPassword() == null);

		// 3. read user's Gender ----------------------------------------------
		Map<String, String> options = new HashMap<>();
		options.put("M", Gender.MALE.name());
		options.put("F", Gender.FEMALE.name());
		options.put("D", Gender.DIVERSE.name());
		String genderValue = inputReader.selectFromList("Gender", "Please enter one of the [] values", options, true,
				null);
		Gender gender = Gender.valueOf(options.get(genderValue.toUpperCase()));
		user.setGender(gender);
		
		// 4. Prompt for superuser attribute ------------------------------
		String superuserValue = inputReader.promptWithOptions("New user is superuser", "N", Arrays.asList("Y", "N"));
		if ("Y".equals(superuserValue)) {
		    user.setSuperuser(true);
		} else {
		    user.setSuperuser(false);
		}

		// Print user's input -------------------------------------------------
		shellHelper.printInfo("\nCreating new user:");
		shellHelper.print("\nUsername: " + user.getUsername());
		shellHelper.print("Password: " + user.getPassword());
		shellHelper.print("Fullname: " + user.getFullName());
		shellHelper.print("Gender: " + user.getGender());
		shellHelper.print("Superuser: " + user.isSuperuser() + "\n");

		CliUser createdUser = userService.create(user);
		shellHelper.printSuccess("Created user with id=" + createdUser.getId());
	}
}
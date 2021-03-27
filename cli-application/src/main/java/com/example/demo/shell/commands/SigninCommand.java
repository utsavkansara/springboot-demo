package com.example.demo.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

import com.example.demo.shell.config.InputReader;
import com.example.demo.shell.config.ShellHelper;
import com.example.demo.shell.security.SecuredCommand;

@ShellComponent
public class SigninCommand extends SecuredCommand {

    @Lazy
    @Autowired
    ShellHelper shellHelper;

    @Lazy
    @Autowired
    InputReader inputReader;

    @Autowired
    AuthenticationManager authenticationManager;

    @ShellMethod("Sign in with account number")
    public void signin() {
        String username;
        boolean usernameInvalid = true;
        do {
            username = inputReader.prompt("Please enter your account number");
            if (StringUtils.hasText(username)) {
                usernameInvalid = false;
            } else {
                shellHelper.printWarning("account number can not be empty string!");
            }
        } while (usernameInvalid);
        Authentication request = new UsernamePasswordAuthenticationToken(username, "1234", AuthorityUtils.createAuthorityList("ROLE_USER"));

        try {
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            shellHelper.printSuccess("Credentials successfully authenticated! " + username + " -> welcome to Forum. Type HELP to see list of available commands.");
        } catch (AuthenticationException e) {
            shellHelper.printError("Authentication failed: " + e.getMessage());
        }
    }
}
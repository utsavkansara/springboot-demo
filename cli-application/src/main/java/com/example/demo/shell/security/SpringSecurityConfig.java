package com.example.demo.shell.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.accounts.WebAccountsService;

/**
 * Spring security configuration
 * 
 * @author Aayushi Raval
 */
@Configuration
public class SpringSecurityConfig {

    @Bean
    public AccountDetailsService accountDetailsService(WebAccountsService webAccountService) {
        return new AccountDetailsService(webAccountService);
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Bean
    public AuthenticationManager authenticationManager(AccountDetailsService accountDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(accountDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager authenticationManager = new ProviderManager(Arrays.asList(authenticationProvider));
        return authenticationManager;
    }
}
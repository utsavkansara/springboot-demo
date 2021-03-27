package com.example.demo.shell.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.accounts.Account;
import com.example.demo.accounts.WebAccountsService;

public class AccountDetailsService implements UserDetailsService {

    private WebAccountsService webAccountService;

    public AccountDetailsService(WebAccountsService webAccountService) {
        this.webAccountService = webAccountService;
    }

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        Account account = webAccountService.findByNumber(accountNumber);

        if (account == null) {
            throw new UsernameNotFoundException("Account not found.");
        }

        User.UserBuilder builder = User.withUsername(accountNumber);
        builder.password(new BCryptPasswordEncoder().encode("1234"));
        builder.roles("USER");
        return builder.build();
    }
}

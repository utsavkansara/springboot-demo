package com.example.demo.shell.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.user.service.MockUserService;
import com.example.demo.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class UserServiceConfig {

    @Bean
    public UserService userService(ObjectMapper objectMapper) throws IOException {
        MockUserService userService = new MockUserService();
//        userService.setObserver(observer);
        userService.setObjectMapper(objectMapper);
        userService.init("cli-users.json");
        return userService;
    }
}
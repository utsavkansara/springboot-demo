package com.example.demo.user.service;

import java.util.List;

import com.example.demo.user.model.CliUser;

public interface UserService {
	CliUser findById(Long id);

	CliUser findByUsername(String username);

	List<CliUser> findAll();

	boolean exists(String username);

	CliUser create(CliUser user);

	CliUser update(CliUser user);

	long updateAll();
}
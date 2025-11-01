package com.eclaims.app.user.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.eclaims.app.user.entity.User;

public interface UserService {
	User getUser(String userName) throws UsernameNotFoundException;

	User registerUser(User user);

	User updateUser(User user);

	void deleteUser(Long userId);
}

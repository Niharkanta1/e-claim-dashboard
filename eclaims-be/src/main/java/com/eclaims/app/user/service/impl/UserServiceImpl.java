package com.eclaims.app.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eclaims.app.user.entity.User;
import com.eclaims.app.user.repository.UserRepository;
import com.eclaims.app.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUser(String userName) throws UsernameNotFoundException {
		log.info("Fetching User with username: {}", userName);
		User user = userRepository.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return user;
	}

	@Override
	public User registerUser(User user) {
		log.info("Saving User : {}", user);
		if (user == null || user.getUsername() == null) {
			throw new RuntimeException("Invalid user details");
		}
		user.setId(null);
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		log.info("Updating User : {}", user);
		userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User does not exist"));
		if (user == null || user.getUsername() == null) {
			throw new RuntimeException("Invalid user details");
		}
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User does not exist"));
		userRepository.deleteById(userId);
	}

}

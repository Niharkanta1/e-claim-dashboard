package com.eclaims.app.auth.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eclaims.app.user.entity.User;
import com.eclaims.app.user.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getUser(username);
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
						.collect(Collectors.toList()));
	}

	public User registerUser(User user) {
		if (user.getPassword() != null) {
			user.setPassword(encoder.encode(user.getPassword()));
		} else {
			user.setPassword(encoder.encode(user.getPassword()));
		}
		return userService.registerUser(user);
	}

	public void removeUser(Long userId) {
		userService.deleteUser(userId);
	}

}

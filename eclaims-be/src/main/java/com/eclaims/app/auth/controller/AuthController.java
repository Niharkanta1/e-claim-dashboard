package com.eclaims.app.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eclaims.app.auth.request.AuthRequest;
import com.eclaims.app.auth.security.CustomUserDetailsService;
import com.eclaims.app.auth.security.JwtUtil;
import com.eclaims.app.user.entity.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/hello")
	public String helloAuth() {
		return "hello from auth";
	}

	@PostMapping("/login")
	public ResponseEntity<?> postMethodName(@RequestBody AuthRequest loginRequest) {
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
		}
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
		Map<String, String> map = new HashMap<>();
		map.put("token", jwtUtil.generateToken(userDetails));
		return ResponseEntity.ok(map);
	}

	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return userDetailsService.registerUser(user);
	}

	@DeleteMapping("/remove/user/{userId}")
	public void registerUser(@PathVariable("userId") Long userId) {
		userDetailsService.removeUser(userId);
	}

}

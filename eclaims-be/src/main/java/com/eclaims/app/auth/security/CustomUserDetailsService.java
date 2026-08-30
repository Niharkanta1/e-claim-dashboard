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
        if (user == null || user.getUsername() == null || user.getPassword() == null
                || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Username and password are required");
        }
        user.setRoles(java.util.Set.of("CUSTOMER"));
        user.setPassword(encoder.encode(user.getPassword()));
        return userService.registerUser(user);
    }

    public void changePassword(String username, String currentPassword, String newPassword) {
        if (currentPassword == null || newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Current and new passwords are required");
        }
        User user = userService.getUser(username);
        if (!encoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        user.setPassword(encoder.encode(newPassword));
        userService.updateUser(user);
    }

    public void resetPassword(String username, String email, String newPassword) {
        if (username == null || email == null || newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Username, email, and new password are required");
        }
        User user = userService.getUser(username);
        if (!email.equalsIgnoreCase(user.getEmail())) {
            throw new IllegalArgumentException("Username and email do not match");
        }
        user.setPassword(encoder.encode(newPassword));
        userService.updateUser(user);
    }

    public void removeUser(Long userId) {
        userService.deleteUser(userId);
    }

}

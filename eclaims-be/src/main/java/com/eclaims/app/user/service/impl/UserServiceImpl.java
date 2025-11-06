package com.eclaims.app.user.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.eclaims.app.user.entity.User;
import com.eclaims.app.user.enums.UserRole;
import com.eclaims.app.user.repository.UserRepository;
import com.eclaims.app.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        log.info("Fetching User with user id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    @Override
    public User getUser(String userName) throws UsernameNotFoundException {
        log.info("Fetching User with username: {}", userName);
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User registerUser(User user) {
        log.info("Saving User : {}", user);
        if (user == null || user.getUsername() == null) {
            throw new RuntimeException("Invalid user details");
        }
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with username already present");
        }
        user.setId(null);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        log.info("Updating User : {}", user);
        userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        if (user.getUsername() == null) {
            throw new RuntimeException("Invalid user details");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User does not exist"));
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserForAreaCodeWithRole(String areaCode, UserRole role) {
        List<User> users = userRepository.findByAreaCode(areaCode);
        return users.stream()
                .filter(user -> user.getRoles().contains(role.name()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<String> getAllUsersWithRole(UserRole userRole) {
        log.info("Fetching all user with role: {}", userRole);
        if (userRole == null) {
            return userRepository.findAll().stream()
                    .map(User::getUsername)
                    .collect(Collectors.toList());
        }

        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(userRole == UserRole.SURVEYOR? "PARTNER" : userRole.name()))
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

}

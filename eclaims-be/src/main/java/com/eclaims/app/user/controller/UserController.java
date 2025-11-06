package com.eclaims.app.user.controller;

import com.eclaims.app.user.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.eclaims.app.user.entity.User;
import com.eclaims.app.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("")
    public List<String> getUsersWithRole(@RequestParam("userRole") UserRole userRole) {
        return userService.getAllUsersWithRole(userRole);
    }

}

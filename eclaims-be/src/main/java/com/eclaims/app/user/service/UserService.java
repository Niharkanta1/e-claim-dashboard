package com.eclaims.app.user.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.eclaims.app.user.entity.User;
import com.eclaims.app.user.enums.UserRole;

public interface UserService {
    User getUser(Long id);

    User getUser(String userName) throws UsernameNotFoundException;

    User registerUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    User getUserForAreaCodeWithRole(String areaCode, UserRole role);
}

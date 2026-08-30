package com.eclaims.app.user.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.eclaims.app.user.entity.User;
import com.eclaims.app.user.enums.UserRole;
import com.eclaims.app.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void getUserByIdReturnsUser() {
        User user = user("alice", "CUSTOMER");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThat(service.getUser(1L)).isSameAs(user);
    }

    @Test
    void getUserByIdThrowsWhenMissing() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getUser(1L)).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void getUserByNameReturnsUser() {
        User user = user("alice", "CUSTOMER");
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));

        assertThat(service.getUser("alice")).isSameAs(user);
    }

    @Test
    void getUserByNameThrowsWhenMissing() {
        when(userRepository.findByUsername("alice")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getUser("alice")).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void registerClearsIdAndSavesNewUser() {
        User user = user("alice", "CUSTOMER");
        user.setId(99L);
        when(userRepository.findByUsername("alice")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        assertThat(service.registerUser(user)).isSameAs(user);
        assertThat(user.getId()).isNull();
        verify(userRepository).save(user);
    }

    @Test
    void registerRejectsNullOrUsernameMissing() {
        assertThatThrownBy(() -> service.registerUser(null)).isInstanceOf(RuntimeException.class);
        User user = new User();
        assertThatThrownBy(() -> service.registerUser(user)).isInstanceOf(RuntimeException.class);
        verify(userRepository, never()).save(user);
    }

    @Test
    void registerRejectsDuplicateUsername() {
        User existing = user("alice", "CUSTOMER");
        User candidate = user("alice", "CUSTOMER");
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> service.registerUser(candidate)).isInstanceOf(RuntimeException.class)
                .hasMessage("User with username already present");
        verify(userRepository, never()).save(candidate);
    }

    @Test
    void updateUserSavesExistingValidUser() {
        User user = user("alice", "CUSTOMER");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        assertThat(service.updateUser(user)).isSameAs(user);
    }

    @Test
    void updateUserRejectsMissingUserOrUsername() {
        User user = user("alice", "CUSTOMER");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.updateUser(user)).isInstanceOf(RuntimeException.class);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        user.setUsername(null);
        assertThatThrownBy(() -> service.updateUser(user)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteUserDeletesExistingUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user("alice", "CUSTOMER")));

        service.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUserRejectsMissingUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteUser(1L)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void findsFirstUserWithRequestedRole() {
        User customer = user("alice", "CUSTOMER");
        User manager = user("bob", "MANAGER");
        when(userRepository.findByAreaCode("A1")).thenReturn(Arrays.asList(customer, manager));

        assertThat(service.getUserForAreaCodeWithRole("A1", UserRole.MANAGER)).isSameAs(manager);
        assertThat(service.getUserForAreaCodeWithRole("A1", UserRole.ADJUSTER)).isNull();
    }

    @Test
    void getsAllUsersWithRoleAndAllUsersWhenRoleIsNull() {
        User customer = user("alice", "CUSTOMER");
        User partner = user("bob", "PARTNER");
        when(userRepository.findAll()).thenReturn(Arrays.asList(customer, partner));

        assertThat(service.getAllUsersWithRole(UserRole.CUSTOMER)).containsExactly("alice");
        assertThat(service.getAllUsersWithRole(UserRole.SURVEYOR)).containsExactly("bob");
        assertThat(service.getAllUsersWithRole(null)).containsExactly("alice", "bob");
    }

    private User user(String username, String role) {
        User user = new User();
        user.setUsername(username);
        user.setRoles(Collections.singleton(role));
        return user;
    }
}

package com.eclaims.app.auth.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import jakarta.servlet.FilterChain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.eclaims.app.auth.util.AuthUtil;
import com.eclaims.app.user.entity.User;
import com.eclaims.app.user.repository.UserRepository;
import com.eclaims.app.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class SecurityUnitTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private AuthUtil authUtil;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService filterUserDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void loadUserByUsernameMapsRolesToAuthorities() {
        User user = domainUser("alice", "encoded", "CUSTOMER");
        when(userService.getUser("alice")).thenReturn(user);

        UserDetails details = userDetailsService.loadUserByUsername("alice");

        assertThat(details.getUsername()).isEqualTo("alice");
        assertThat(details.getPassword()).isEqualTo("encoded");
        assertThat(details.getAuthorities()).extracting("authority").containsExactly("ROLE_CUSTOMER");
    }

    @Test
    void registerUserEncodesPasswordAndDelegates() {
        User user = domainUser("alice", "plain", "CUSTOMER");
        when(passwordEncoder.encode("plain")).thenReturn("encoded");
        when(userService.registerUser(user)).thenReturn(user);

        assertThat(userDetailsService.registerUser(user)).isSameAs(user);
        assertThat(user.getPassword()).isEqualTo("encoded");
        verify(userService).registerUser(user);
    }

    @Test
    void registerUserAlsoHandlesNullPassword() {
        User user = domainUser("alice", null, "CUSTOMER");
        when(passwordEncoder.encode(null)).thenReturn("encoded-null");
        when(userService.registerUser(user)).thenReturn(user);

        userDetailsService.registerUser(user);

        assertThat(user.getPassword()).isEqualTo("encoded-null");
    }

    @Test
    void removeUserDelegatesToUserService() {
        userDetailsService.removeUser(3L);
        verify(userService).deleteUser(3L);
    }

    @Test
    void jwtTokenRoundTripsUsername() {
        JwtUtil jwtUtil = new JwtUtil();
        UserDetails details = org.springframework.security.core.userdetails.User.withUsername("alice")
                .password("ignored").roles("CUSTOMER").build();

        String token = jwtUtil.generateToken(details);

        assertThat(jwtUtil.extractUsername(token)).isEqualTo("alice");
    }

    @Test
    void invalidJwtTokenIsRejected() {
        JwtUtil jwtUtil = new JwtUtil();

        assertThatThrownBy(() -> jwtUtil.extractUsername("not-a-jwt"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void authUtilReturnsCurrentDomainUserForUserDetailsPrincipal() {
        User user = domainUser("alice", "encoded", "CUSTOMER");
        authenticateAs(org.springframework.security.core.userdetails.User.withUsername("alice")
                .password("ignored").build());
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));

        assertThat(authUtil.getCurrentUser()).isSameAs(user);
        assertThat(authUtil.getCurrentUsername()).isEqualTo("alice");
    }

    @Test
    void authUtilSupportsStringPrincipalAndUnauthenticatedContext() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("bob", null, Collections.emptyList()));

        assertThat(authUtil.getCurrentUsername()).isEqualTo("bob");
        when(userRepository.findByUsername("bob")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authUtil.getCurrentUser()).isInstanceOf(RuntimeException.class);

        SecurityContextHolder.clearContext();
        assertThat(authUtil.getCurrentUsername()).isNull();
        assertThatThrownBy(() -> authUtil.getCurrentUser()).isInstanceOf(RuntimeException.class);
    }

    @Test
    void jwtFilterAuthenticatesValidBearerToken() throws Exception {
        UserDetails details = org.springframework.security.core.userdetails.User.withUsername("alice")
                .password("ignored").build();
        when(jwtUtil.extractUsername("token")).thenReturn("alice");
        when(filterUserDetailsService.loadUserByUsername("alice")).thenReturn(details);
        org.springframework.test.util.ReflectionTestUtils.setField(jwtFilter, "userDetailsService",
            filterUserDetailsService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");

        jwtFilter.doFilterInternal(request, new MockHttpServletResponse(), filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("alice");
        verify(filterChain).doFilter(eq(request), org.mockito.ArgumentMatchers.any());
    }

    @Test
    void jwtFilterContinuesWithoutBearerToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        jwtFilter.doFilterInternal(request, new MockHttpServletResponse(), filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(eq(request), org.mockito.ArgumentMatchers.any());
    }

    private void authenticateAs(Object principal) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList()));
    }

    private User domainUser(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(Collections.singleton(role));
        return user;
    }
}

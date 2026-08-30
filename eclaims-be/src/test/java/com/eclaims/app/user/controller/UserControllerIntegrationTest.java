package com.eclaims.app.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.eclaims.app.user.entity.User;
import com.eclaims.app.user.enums.UserRole;
import com.eclaims.app.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void protectedEndpointRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/users/7"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getUserReturnsUser() throws Exception {
        User userResponse = new User();
        userResponse.setId(7L);
        userResponse.setUsername("alice");
        when(userService.getUser(7L)).thenReturn(userResponse);

        mockMvc.perform(get("/api/users/7").with(user("alice")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.username").value("alice"));
    }

    @Test
    void getUsersWithRolePassesEnumQueryParameter() throws Exception {
        when(userService.getAllUsersWithRole(UserRole.CUSTOMER)).thenReturn(Arrays.asList("alice", "bob"));

        mockMvc.perform(get("/api/users").with(user("manager"))
                .param("userRole", "CUSTOMER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("alice"))
                .andExpect(jsonPath("$[1]").value("bob"));

        verify(userService).getAllUsersWithRole(UserRole.CUSTOMER);
    }

    @Test
    void registerUserReturnsCreatedUser() throws Exception {
        User request = new User();
        request.setUsername("alice");
        when(userService.registerUser(any(User.class))).thenReturn(request);

        mockMvc.perform(post("/api/users").with(user("manager"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alice"));
    }
}
package com.eclaims.app.claim.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.eclaims.app.claim.entity.Claim;
import com.eclaims.app.claim.enums.ClaimStatus;
import com.eclaims.app.claim.service.ClaimService;
import com.eclaims.app.user.enums.UserRole;

@SpringBootTest
@AutoConfigureMockMvc
class ClaimControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClaimService claimService;

    @Test
    void getAllClaimsReturnsClaims() throws Exception {
        Claim claim = claim(1L);
        when(claimService.getAllClaims()).thenReturn(List.of(claim));

        mockMvc.perform(get("/api/claims/all").with(user("manager")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].claimId").value(1));
    }

    @Test
    void getClaimsForCurrentUserDelegatesService() throws Exception {
        when(claimService.getClaimsForCurrentUser()).thenReturn(List.of());

        mockMvc.perform(get("/api/claims").with(user("alice")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(0)));

        verify(claimService).getClaimsForCurrentUser();
    }

    @Test
    void getClaimPassesPathVariable() throws Exception {
        when(claimService.getClaim(9L)).thenReturn(claim(9L));

        mockMvc.perform(get("/api/claims/9").with(user("alice")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.claimId").value(9));
    }

    @Test
    void createClaimBindsMultipartFormAndFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "receipt.txt", MediaType.TEXT_PLAIN_VALUE,
                "receipt".getBytes());
        Claim response = claim(3L);
        when(claimService.submitClaim(any(), any(Claim.class))).thenReturn(response);

        mockMvc.perform(multipart("/api/claims").file(file).with(user("alice"))
                .param("firstName", "Alice")
                .param("policyNumber", "POL-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.claimId").value(3));

        verify(claimService).submitClaim(any(), any(Claim.class));
    }

    @Test
    void assignClaimPassesPathAndRequestParameters() throws Exception {
        when(claimService.assignToUser(3L, UserRole.ADJUSTER, "bob")).thenReturn(claim(3L));

        mockMvc.perform(put("/api/claims/assign/3").with(user("manager"))
                .param("role", "ADJUSTER")
                .param("userId", "bob"))
                .andExpect(status().isOk());

        verify(claimService).assignToUser(3L, UserRole.ADJUSTER, "bob");
    }

    @Test
    void removeAssignmentPassesRole() throws Exception {
        when(claimService.removeAssignment(3L, UserRole.ADJUSTER)).thenReturn(claim(3L));

        mockMvc.perform(put("/api/claims/remove-assign/3").with(user("manager"))
                .param("role", "ADJUSTER"))
                .andExpect(status().isOk());

        verify(claimService).removeAssignment(3L, UserRole.ADJUSTER);
    }

    @Test
    void updateClaimPassesStatus() throws Exception {
        when(claimService.updateClaim(3L, ClaimStatus.APPROVED)).thenReturn(claim(3L));

        mockMvc.perform(put("/api/claims/3").with(user("manager"))
                .param("status", "APPROVED"))
                .andExpect(status().isOk());

        verify(claimService).updateClaim(3L, ClaimStatus.APPROVED);
    }

    private Claim claim(Long id) {
        Claim claim = new Claim();
        claim.setClaimId(id);
        return claim;
    }
}
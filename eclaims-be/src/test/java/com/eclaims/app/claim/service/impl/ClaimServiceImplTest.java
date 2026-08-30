package com.eclaims.app.claim.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import com.eclaims.app.auth.util.AuthUtil;
import com.eclaims.app.claim.entity.Claim;
import com.eclaims.app.claim.entity.ClaimAssignment;
import com.eclaims.app.claim.entity.ClaimEvent;
import com.eclaims.app.claim.enums.ClaimStatus;
import com.eclaims.app.claim.enums.EventType;
import com.eclaims.app.claim.repository.ClaimRepository;
import com.eclaims.app.user.entity.User;
import com.eclaims.app.user.enums.UserRole;
import com.eclaims.app.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class ClaimServiceImplTest {

    @TempDir
    Path uploadDirectory;

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuthUtil authUtil;

    @InjectMocks
    private ClaimServiceImpl service;

    @BeforeEach
    void setUploadDirectory() {
        ReflectionTestUtils.setField(service, "uploadDir", uploadDirectory.toString());
    }

    @Test
    void submitClaimSavesFileAndAutoAssignsUsers() throws Exception {
        User customer = user("customer", "A1", 1L, "CUSTOMER");
        User manager = user("manager", "A1", 2L, "MANAGER");
        User adjuster = user("adjuster", "A1", 3L, "ADJUSTER");
        User surveyor = user("surveyor", "A1", 4L, "PARTNER");
        Claim claim = new Claim();
        claim.setEvents(new ArrayList<>());
        MockMultipartFile file = new MockMultipartFile("files", "receipt.txt", "text/plain", "receipt".getBytes());
        when(authUtil.getCurrentUser()).thenReturn(customer);
        when(userService.getUserForAreaCodeWithRole("A1", UserRole.MANAGER)).thenReturn(manager);
        when(userService.getUserForAreaCodeWithRole("A1", UserRole.ADJUSTER)).thenReturn(adjuster);
        when(userService.getUserForAreaCodeWithRole("A1", UserRole.SURVEYOR)).thenReturn(surveyor);
        when(claimRepository.save(claim)).thenReturn(claim);

        Claim result = service.submitClaim(Arrays.asList(file), claim);

        assertThat(result).isSameAs(claim);
        assertThat(claim.getStatus()).isEqualTo(ClaimStatus.SUBMITTED);
        assertThat(claim.getCustomer()).isSameAs(customer);
        assertThat(claim.getDocumentPaths()).contains("receipt.txt");
        assertThat(Files.list(uploadDirectory)).hasSize(1);
        assertThat(claim.getAssignment().getManager()).isSameAs(manager);
        assertThat(claim.getAssignment().getAdjuster()).isSameAs(adjuster);
        assertThat(claim.getAssignment().getSurveyor()).isSameAs(surveyor);
        assertThat(claim.getEvents()).extracting(ClaimEvent::getEvent)
            .containsExactly(EventType.SUBMITTED);
    }

    @Test
    void submitClaimHandlesNullAndEmptyFiles() throws Exception {
        User customer = user("customer", "A1", 1L, "CUSTOMER");
        Claim claim = new Claim();
        claim.setEvents(new ArrayList<>());
        when(authUtil.getCurrentUser()).thenReturn(customer);
        when(userService.getUserForAreaCodeWithRole(any(), any())).thenReturn(user("staff", "A1", 2L, "MANAGER"));
        when(claimRepository.save(claim)).thenReturn(claim);

        service.submitClaim(null, claim);

        assertThat(claim.getDocumentPaths()).isEmpty();
        verify(claimRepository).save(claim);
    }

    @Test
    void assignsAdjusterAndSurveyor() {
        Claim claim = claim(1L);
        User user = user("staff", "A1", 2L, "ADJUSTER");
        when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));
        when(userService.getUser("staff")).thenReturn(user);
        when(claimRepository.save(claim)).thenReturn(claim);

        service.assignToUser(1L, UserRole.ADJUSTER, "staff");
        service.assignToUser(1L, UserRole.SURVEYOR, "staff");

        assertThat(claim.getAssignment().getAdjuster()).isSameAs(user);
        assertThat(claim.getAssignment().getSurveyor()).isSameAs(user);
        verify(claimRepository, org.mockito.Mockito.times(2)).save(claim);
    }

    @Test
    void rejectsInvalidOrMissingAssignmentClaim() {
        when(claimRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.assignToUser(1L, UserRole.ADJUSTER, "staff"))
                .hasMessage("Claim is not found");

        Claim claim = claim(1L);
        when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));
        assertThatThrownBy(() -> service.assignToUser(1L, UserRole.MANAGER, "staff"))
                .hasMessage("Invalid user assignment");
    }

    @Test
    void removesAdjusterAndSurveyor() {
        Claim claim = claim(1L);
        User adjuster = user("adjuster", "A1", 2L, "ADJUSTER");
        User surveyor = user("surveyor", "A1", 3L, "PARTNER");
        claim.getAssignment().setAdjuster(adjuster);
        claim.getAssignment().setSurveyor(surveyor);
        when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));
        when(claimRepository.save(claim)).thenReturn(claim);

        service.removeAssignment(1L, UserRole.ADJUSTER);
        service.removeAssignment(1L, UserRole.SURVEYOR);

        assertThat(claim.getAssignment().getAdjuster()).isNull();
        assertThat(claim.getAssignment().getSurveyor()).isNull();
        assertThat(claim.getEvents()).extracting(ClaimEvent::getEvent)
                .containsExactly(EventType.ADJUSTER_REMOVED, EventType.SURVEYOR_REMOVED);
    }

    @Test
    void rejectsInvalidOrMissingRemovalClaim() {
        when(claimRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.removeAssignment(1L, UserRole.ADJUSTER))
                .hasMessage("Claim is not found");

        Claim claim = claim(1L);
        when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));
        assertThatThrownBy(() -> service.removeAssignment(1L, UserRole.MANAGER))
                .hasMessage("Invalid user assignment");
    }

    @Test
    void retrievesClaimsByEverySupportedRole() {
        User current = user("alice", "A1", 1L, "CUSTOMER");
        Claim newest = claim(1L);
        newest.setCreatedAt(LocalDateTime.now());
        Claim oldest = claim(2L);
        oldest.setCreatedAt(LocalDateTime.now().minusDays(1));
        List<Claim> claims = new ArrayList<>(Arrays.asList(oldest, newest));
        when(authUtil.getCurrentUser()).thenReturn(current);
        when(claimRepository.findAllByCustomer(current)).thenReturn(claims);
        when(claimRepository.findAllByAssignmentSurveyor(current)).thenReturn(claims);
        when(claimRepository.findAllByAssignmentAdjuster(current)).thenReturn(claims);
        when(claimRepository.findAll()).thenReturn(claims);

        for (String role : Arrays.asList("CUSTOMER", "PARTNER", "MANAGER", "ADJUSTER")) {
            current.setRoles(Collections.singleton(role));
            assertThat(service.getClaimsForCurrentUser()).containsExactly(newest, oldest);
        }
    }

    @Test
    void rejectsUnsupportedClaimRole() {
        User current = user("alice", "A1", 1L, "ADMIN");
        when(authUtil.getCurrentUser()).thenReturn(current);

        assertThatThrownBy(() -> service.getClaimsForCurrentUser()).hasMessage("Invalid user access");
    }

    @Test
    void getsLatestClaimForSpecialIdAndRegularClaimById() {
        User current = user("alice", "A1", 1L, "CUSTOMER");
        Claim latest = claim(1L);
        when(authUtil.getCurrentUser()).thenReturn(current);
        when(claimRepository.findTopByCustomerOrderByCreatedAtDesc(current)).thenReturn(latest);
        assertThat(service.getClaim(-1L)).isSameAs(latest);

        Claim regular = claim(2L);
        when(claimRepository.findById(2L)).thenReturn(Optional.of(regular));
        assertThat(service.getClaim(2L)).isSameAs(regular);
        when(claimRepository.findById(3L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getClaim(3L)).hasMessage("Claim is not found");
    }

    @Test
    void updatesEverySupportedStatusAndRejectsUnsupportedStatus() {
        User current = user("manager", "A1", 2L, "MANAGER");
        when(authUtil.getCurrentUser()).thenReturn(current);
        Claim claim = claim(1L);
        when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));
        when(claimRepository.save(claim)).thenReturn(claim);

        for (ClaimStatus status : Arrays.asList(ClaimStatus.SURVEY_COMPLETED, ClaimStatus.APPROVED,
                ClaimStatus.SETTLED, ClaimStatus.REJECTED)) {
            service.updateClaim(1L, status);
        }

        assertThat(claim.getEvents()).extracting(ClaimEvent::getEvent)
                .containsExactly(EventType.SURVEY_COMPLETED, EventType.APPROVED, EventType.SETTLED, EventType.REJECTED);
        assertThatThrownBy(() -> service.updateClaim(1L, ClaimStatus.IN_PROGRESS))
                .hasMessage("Invalid claim status");
    }

    @Test
    void updateClaimRejectsMissingClaim() {
        when(claimRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateClaim(1L, ClaimStatus.APPROVED))
                .hasMessage("Claim not found");
        verify(claimRepository, never()).save(any());
    }

    @Test
    void returnsAllClaims() {
        List<Claim> claims = Collections.singletonList(claim(1L));
        when(claimRepository.findAll()).thenReturn(claims);

        assertThat(service.getAllClaims()).isSameAs(claims);
    }

    private Claim claim(Long id) {
        Claim claim = new Claim();
        claim.setClaimId(id);
        claim.setEvents(new ArrayList<>());
        ClaimAssignment assignment = new ClaimAssignment();
        assignment.setClaim(claim);
        claim.setAssignment(assignment);
        return claim;
    }

    private User user(String username, String areaCode, Long id, String role) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setAreaCode(areaCode);
        user.setFirstName(username);
        user.setRoles(Collections.singleton(role));
        return user;
    }
}

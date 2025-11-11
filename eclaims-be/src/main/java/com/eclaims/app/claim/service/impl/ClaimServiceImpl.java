package com.eclaims.app.claim.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.eclaims.app.auth.util.AuthUtil;
import com.eclaims.app.claim.entity.Claim;
import com.eclaims.app.claim.entity.ClaimAssignment;
import com.eclaims.app.claim.entity.ClaimEvent;
import com.eclaims.app.claim.enums.ClaimStatus;
import com.eclaims.app.claim.enums.EventType;
import com.eclaims.app.claim.repository.ClaimRepository;
import com.eclaims.app.claim.service.ClaimService;
import com.eclaims.app.user.entity.User;
import com.eclaims.app.user.enums.UserRole;
import com.eclaims.app.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClaimServiceImpl implements ClaimService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthUtil authUtil;

    @Override
    public Claim submitClaim(List<MultipartFile> files, Claim claim) throws IOException {
        log.info("Saving claim: {} with files count: {}", claim, files != null ? files.size() : 0);
        List<String> savedPaths = new ArrayList<>();
        User currentUser = authUtil.getCurrentUser();

        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path path = Paths.get(uploadDir, fileName);
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    savedPaths.add(path.toString());
                }
            }
        }

        claim.setDocumentPaths(String.join(",", savedPaths));
        claim.setStatus(ClaimStatus.SUBMITTED);
        claim.setCreatedAt(LocalDateTime.now());
        claim.setCustomer(currentUser);
        findAndAssignAreaManager(claim);
        addEvent(claim, currentUser);
        return claimRepository.save(claim);
    }

    private void addEvent(Claim claim, User user) {
        ClaimEvent event = getClaimEvent(claim, user, EventType.SUBMITTED);
        claim.setEvents(Collections.singletonList(event));
    }

    private ClaimEvent getClaimEvent(Claim claim, User user, EventType eventType) {
        ClaimEvent event = new ClaimEvent();
        event.setEventTime(LocalDateTime.now());
        event.setUserId(user != null ? user.getId() : null);
        event.setClaim(claim);
        event.setEvent(eventType);
        return event;
    }

    @Override
    public Claim assignToUser(Long claimId, UserRole role, String userName) {
        Optional<Claim> claimOptional = claimRepository.findById(claimId);
        if (claimOptional.isPresent()) {
            User user = userService.getUser(userName);
            Claim claim = claimOptional.get();
            switch (role) {
                case ADJUSTER:
                    claim.getAssignment().setAdjuster(user);
                    claim.getEvents().add(getClaimEvent(claim, user, EventType.ADJUSTER_ASSIGNED));
                    break;
                case SURVEYOR:
                    claim.getAssignment().setSurveyor(user);
                    claim.getEvents().add(getClaimEvent(claim, user, EventType.SURVEYOR_ASSIGNED));
                    break;
                default:
                    throw new RuntimeException("Invalid user assignment");
            }
            return claimRepository.save(claim);
        } else {
            throw new RuntimeException("Claim is not found");
        }
    }

    @Override
    public Claim removeAssignment(Long claimId, UserRole userRole) {
        Optional<Claim> claimOptional = claimRepository.findById(claimId);
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            switch (userRole) {
                case ADJUSTER:
                    claim.getAssignment().setAdjuster(null);
                    claim.getEvents().add(getClaimEvent(claim, null, EventType.ADJUSTER_REMOVED));
                    break;
                case SURVEYOR:
                    claim.getAssignment().setSurveyor(null);
                    claim.getEvents().add(getClaimEvent(claim, null, EventType.SURVEYOR_REMOVED));
                    break;
                default:
                    throw new RuntimeException("Invalid user assignment");
            }
            return claimRepository.save(claim);
        } else {
            throw new RuntimeException("Claim is not found");
        }
    }

    private void findAndAssignAreaManager(Claim claim) {
        ClaimAssignment assignment = new ClaimAssignment();
        User manager = userService.getUserForAreaCodeWithRole(claim.getCustomer().getAreaCode(), UserRole.MANAGER);
        log.info("Auto Assigning MANAGER: {} to the claim: {}", manager.getFirstName(), claim.getClaimId());

        assignment.setManager(manager);
        claim.getEvents().add(getClaimEvent(claim, manager, EventType.MANAGER_ASSIGNED));

        User adjuster = userService.getUserForAreaCodeWithRole(claim.getCustomer().getAreaCode(), UserRole.ADJUSTER);
        log.info("Auto Assigning ADJUSTER: {} to the claim: {}", manager.getFirstName(), claim.getClaimId());

        assignment.setAdjuster(adjuster);
        claim.getEvents().add(getClaimEvent(claim, adjuster, EventType.ADJUSTER_ASSIGNED));

        User surveyor = userService.getUserForAreaCodeWithRole(claim.getCustomer().getAreaCode(), UserRole.SURVEYOR);
        log.info("Auto Assigning SURVEYOR: {} to the claim: {}", manager.getFirstName(), claim.getClaimId());

        assignment.setSurveyor(surveyor);
        claim.getEvents().add(getClaimEvent(claim, surveyor, EventType.SURVEYOR_ASSIGNED));

        assignment.setClaim(claim);
        assignment.setAssignedAt(LocalDateTime.now());
        claim.setAssignment(assignment);
    }

    @Override
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public List<Claim> getClaimsForCurrentUser() {
        List<Claim> claimList = null;
        User user = authUtil.getCurrentUser();
        String userRole = user.getRoles().stream().findFirst().get();
        switch (userRole) {
            case "CUSTOMER":
                claimList = claimRepository.findAllByCustomer(user);
                break;
            case "PARTNER":
                claimList = claimRepository.findAllByAssignmentSurveyor(user);
                break;
            case "MANAGER":
                claimList = getAllClaims();
                break;
            case "ADJUSTER":
                claimList = claimRepository.findAllByAssignmentAdjuster(user);
                break;
            default:
                throw new RuntimeException("Invalid user access");
        }
        claimList.sort(Comparator.comparing(Claim::getCreatedAt).reversed());
        return claimList;
    }

    @Override
    public Claim getClaim(Long id) {
        Claim claim = null;
        if (id == -1) {
            User user = authUtil.getCurrentUser();
            claim = claimRepository.findTopByCustomerOrderByCreatedAtDesc(user);
        } else {
            claim = claimRepository.findById(id).orElseThrow(() -> new RuntimeException("Claim is not found"));
        }
        return claim;
    }

    @Override
    public Claim updateClaim(Long claimId, ClaimStatus claimStatus) {
        log.info("Updating claim with claimId: {} and Status: {}", claimId, claimStatus);
        Claim claim = claimRepository.findById(claimId).orElseThrow(() -> new RuntimeException("Claim not found"));
        claim.setStatus(claimStatus);
        User user = authUtil.getCurrentUser();
        switch (claimStatus) {
            case SURVEY_COMPLETED:
                claim.getEvents().add(getClaimEvent(claim, user, EventType.SURVEY_COMPLETED));
                break;
            case APPROVED:
                claim.getEvents().add(getClaimEvent(claim, user, EventType.APPROVED));
                break;
            case SETTLED:
                claim.getEvents().add(getClaimEvent(claim, user, EventType.SETTLED));
                break;
            case REJECTED:
                claim.getEvents().add(getClaimEvent(claim, user, EventType.REJECTED));
                break;
            default:
                throw new RuntimeException("Invalid claim status");
        }
        return claimRepository.save(claim);
    }
}

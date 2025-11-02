package com.eclaims.app.claim.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        addEvent(claim, currentUser, EventType.SUBMITTED);
        return claimRepository.save(claim);
    }

    private void addEvent(Claim claim, User user, EventType eventType) {
        ClaimEvent event = new ClaimEvent();
        event.setEventTime(LocalDateTime.now());
        event.setUser(user);
        event.setClaim(claim);
        event.setEvent(eventType);
        claim.setEvents(Arrays.asList(event));
    }

    public Claim assigntToUser(Long claimId, UserRole role) {
        // TODO - Assing the current claim to the user;
        return null;
    }

    private void findAndAssignAreaManager(Claim claim) {
        ClaimAssignment assignment = new ClaimAssignment();
        User user = userService.getUserForAreaCodeWithRole(claim.getCustomer().getAreaCode(), UserRole.MANAGER);
        assignment.setManager(user);
        assignment.setAssignedAt(LocalDateTime.now());
        assignment.setClaim(claim);
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
            case "AJUSTER":
                claimList = claimRepository.findAllByAssignmentAdjuster(user);
                break;
            default:
                throw new RuntimeException("Invalid user access");
        }

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
}

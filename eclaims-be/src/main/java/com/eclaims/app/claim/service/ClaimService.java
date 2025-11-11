package com.eclaims.app.claim.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.eclaims.app.claim.entity.Claim;
import com.eclaims.app.claim.enums.ClaimStatus;
import com.eclaims.app.user.enums.UserRole;

public interface ClaimService {

    Claim submitClaim(List<MultipartFile> files, Claim claim) throws IOException;

    List<Claim> getAllClaims();

    Claim getClaim(Long id);

    List<Claim> getClaimsForCurrentUser();

    Claim assignToUser(Long claimId, UserRole role, String userName);

    Claim removeAssignment(Long claimId, UserRole userRole);

    Claim updateClaim(Long claimId, ClaimStatus claimStatus);
}

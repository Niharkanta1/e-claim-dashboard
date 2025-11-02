package com.eclaims.app.claim.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.eclaims.app.claim.entity.Claim;

public interface ClaimService {

    Claim submitClaim(List<MultipartFile> files, Claim claim) throws IOException;

    List<Claim> getAllClaims();

    Claim getClaim(Long id);

    List<Claim> getClaimsForCurrentUser();
}

package com.eclaims.app.claim.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.eclaims.app.claim.entity.Claim;
import com.eclaims.app.claim.enums.ClaimStatus;
import com.eclaims.app.claim.request.ClaimRequest;
import com.eclaims.app.claim.service.ClaimService;
import com.eclaims.app.user.enums.UserRole;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping(consumes = {"multipart/form-data"})
    public Claim createClaim(@ModelAttribute ClaimRequest request) throws IOException {
        Claim claim = new Claim();
        BeanUtils.copyProperties(request, claim, "files");
        return claimService.submitClaim(request.getFiles(), claim);
    }

    @GetMapping("/all")
    public List<Claim> getAllClaims() {
        return claimService.getAllClaims();
    }

    @GetMapping
    public List<Claim> getClaimsForUser() {
        return claimService.getClaimsForCurrentUser();
    }

    @GetMapping("/{id}")
    public Claim getClaim(@PathVariable Long id) {
        return claimService.getClaim(id);
    }

    @PutMapping("/assign/{claimId}")
    public Claim assignClaim(@PathVariable Long claimId, @RequestParam("role") UserRole userRole,
            @RequestParam("userId") String userName) {
        return claimService.assignToUser(claimId, userRole, userName);
    }

    @PutMapping("/remove-assign/{claimId}")
    public Claim assignClaim(@PathVariable Long claimId, @RequestParam("role") UserRole userRole) {
        return claimService.removeAssignment(claimId, userRole);
    }

    @PutMapping("/{claimId}")
    public Claim updateClaim(@PathVariable Long claimId, @RequestParam("status") ClaimStatus claimStatus) {
        return claimService.updateClaim(claimId, claimStatus);
    }
}

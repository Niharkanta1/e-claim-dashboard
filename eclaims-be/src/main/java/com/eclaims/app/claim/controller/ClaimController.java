package com.eclaims.app.claim.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eclaims.app.claim.entity.Claim;
import com.eclaims.app.claim.request.ClaimRequest;
import com.eclaims.app.claim.service.ClaimService;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Claim> createClaim(@ModelAttribute ClaimRequest request) throws IOException {
        Claim claim = new Claim();
        BeanUtils.copyProperties(request, claim, "files");
        Claim saved = claimService.submitClaim(request.getFiles(), claim);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Claim>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @GetMapping
    public ResponseEntity<List<Claim>> getClaimsForUser() {
        return ResponseEntity.ok(claimService.getClaimsForCurrentUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> getClaim(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaim(id));
    }
}

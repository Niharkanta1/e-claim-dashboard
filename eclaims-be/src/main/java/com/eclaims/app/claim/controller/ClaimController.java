package com.eclaims.app.claim.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/claim")
public class ClaimController {

	@Autowired
	private ClaimService claimService;

	@GetMapping("/user/{userId}")
	public List<String> getClaimsForUser(@PathVariable Long userId) {

		return Arrays.asList("claim-1-for-user-" + userId, "claim-2-for-user-" + userId);
	}

	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/dashboard/customer")
	public String getCustomerDashboardData() {
		return "Customer dashboard Data!";
	}

	@PreAuthorize("hasRole('PARTNER')")
	@GetMapping("/dashboard/partner")
	public String getPartnerDashboardData() {
		return "Partner dashboard Data!";
	}

	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/dashboard/manager")
	public String getManagerDashboardData() {
		return "Manager dashboard Data!";
	}

	@PreAuthorize("hasRole('SURVEYOR')")
	@GetMapping("/dashboard/surveyor")
	public String getSurveyorDashboardData() {
		return "Surveyor dashboard Data!";
	}

	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<Claim> createClaim(@ModelAttribute ClaimRequest request) throws IOException {
		Claim claim = new Claim();
		BeanUtils.copyProperties(request, claim, "files");
		Claim saved = claimService.saveClaim(request.getFiles(), claim);
		return ResponseEntity.ok(saved);
	}

	@GetMapping
	public ResponseEntity<List<Claim>> getAllClaims() {
		return ResponseEntity.ok(claimService.getAllClaims());
	}
}

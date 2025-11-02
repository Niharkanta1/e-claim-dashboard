package com.eclaims.app.claim.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eclaims.app.claim.entity.Claim;
import com.eclaims.app.claim.repository.ClaimRepository;
import com.eclaims.app.claim.service.ClaimService;
import com.eclaims.app.user.entity.User;
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

	@Override
	public Claim saveClaim(List<MultipartFile> files, Claim claim) throws IOException {
		log.info("Saving claim: {} with files count: {}", claim, files != null ? files.size() : 0);
		List<String> savedPaths = new ArrayList<>();
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
		Claim saved = claimRepository.save(claim);
		return saved;
	}

	@Override
	public List<Claim> getAllClaims() {
		return claimRepository.findAll();
	}

	@Override
	public List<Claim> getClaimsForUser(String username) {
		List<Claim> claimList = null;
		User user = userService.getUser(username);
		String userRole = user.getRoles().stream().findFirst().get();
		switch (userRole) {
		case "CUSTOMER":
			claimRepository.get
			break;
		case "PARTNER":
			break;
		case "MANAGER":
			claimList = getAllClaims();
			break;
		case "SURVEYOR":
			break;
		default:
			throw new RuntimeException("Invalid user access");
		}

		return claimList;
	}
}

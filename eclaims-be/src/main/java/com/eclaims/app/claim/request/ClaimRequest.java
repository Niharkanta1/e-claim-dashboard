package com.eclaims.app.claim.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimRequest {
	private String firstName;
	private String lastName;
	private String policyNumber;
	private String policyType;
	private String dateOfIncident;
	private String claimType;
	private String description;
	private String contactNumber;
	private List<MultipartFile> files;
}

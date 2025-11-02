package com.eclaims.app.claim.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Entity
@Data
public class Claim {
	@Id
	@GeneratedValue
	private Long claimId;
	private String firstName;
	private String lastName;
	private String policyNumber;
	private String policyType;
	private String dateOfIncident;
	private String claimType;
	private String description;
	private String contactNumber;
	private String policyUser;

	@Lob
	private String documentPaths;
}

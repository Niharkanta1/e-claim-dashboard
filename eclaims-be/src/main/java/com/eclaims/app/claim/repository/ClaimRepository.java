package com.eclaims.app.claim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eclaims.app.claim.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

}

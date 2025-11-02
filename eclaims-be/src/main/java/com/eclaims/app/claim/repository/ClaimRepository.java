package com.eclaims.app.claim.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eclaims.app.claim.entity.Claim;
import com.eclaims.app.user.entity.User;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    List<Claim> findAllByCustomer(User user);

    List<Claim> findAllByCustomerOrderByCreatedAtDesc(User customer);

    List<Claim> findAllByAssignmentAdjuster(User adjuster);

    List<Claim> findAllByAssignmentSurveyor(User surveyor);

    Claim findTopByCustomerOrderByCreatedAtDesc(User customer);
}

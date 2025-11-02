package com.eclaims.app.claim.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import com.eclaims.app.claim.enums.ClaimStatus;
import com.eclaims.app.user.entity.User;
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

    private LocalDateTime createdAt;

    @Lob
    private String documentPaths;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    private ClaimStatus status;

    @OneToOne(mappedBy = "claim", cascade = CascadeType.ALL)
    private ClaimAssignment assignment;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ClaimEvent> events;

}

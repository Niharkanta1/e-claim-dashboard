package com.eclaims.app.claim.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import com.eclaims.app.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
public class ClaimAssignment {

    @Id
    @GeneratedValue
    private Long assignmentId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "claim_id")
    private Claim claim;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "adjuster_id")
    private User adjuster;

    @ManyToOne
    @JoinColumn(name = "surveyor_id")
    private User surveyor;

    private LocalDateTime assignedAt;
}

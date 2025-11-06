package com.eclaims.app.claim.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.eclaims.app.claim.enums.EventType;
import com.eclaims.app.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
public class ClaimEvent {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id")
    private Claim claim;

    private Long userId;

    private LocalDateTime eventTime;

    @Enumerated(EnumType.STRING)
    private EventType event;

}

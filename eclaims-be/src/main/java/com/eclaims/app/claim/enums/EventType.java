package com.eclaims.app.claim.enums;

public enum EventType {
    SUBMITTED("Claim Submitted"), MANAGER_ASSIGNED("Assigned To Incident Manager"), ADJUSTER_ASSIGNED(
            "Assigned To Adjuster"), SURVEYOR_ASSIGNED("Assigned To Surveyor"), SURVEY_COMPLETED(
                    "Claim Inspected"), APPROVED("Claim Approved"), SETTLED("Settled");

    private final String description;

    EventType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

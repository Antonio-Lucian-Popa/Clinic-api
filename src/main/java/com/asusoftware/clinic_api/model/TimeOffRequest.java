package com.asusoftware.clinic_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "time_off_requests")
@Data
@NoArgsConstructor
public class TimeOffRequest extends BaseEntity {
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    private UUID approvedBy;
    private LocalDateTime approvedAt;


    @Enumerated(EnumType.STRING)
    private TimeOffRequestType type;

    @Enumerated(EnumType.STRING)
    private TimeOffRequestStatus status;
}
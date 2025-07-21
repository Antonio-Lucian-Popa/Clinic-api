package com.asusoftware.clinic_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String status;
}
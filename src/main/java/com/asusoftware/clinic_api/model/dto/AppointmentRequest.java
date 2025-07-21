package com.asusoftware.clinic_api.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentRequest {
    @NotNull
    private UUID doctorId;
    @NotNull
    private UUID patientId;
    private UUID assistantId;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    private String notes;
}


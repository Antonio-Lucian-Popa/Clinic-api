package com.asusoftware.clinic_api.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class DoctorRequest {
    @NotNull
    private UUID userId; // preluat din Auth Server
    private String specialization;
    private String roomLabel;
}


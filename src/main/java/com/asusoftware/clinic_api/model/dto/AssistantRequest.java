package com.asusoftware.clinic_api.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AssistantRequest {
    @NotNull
    private UUID userId; // preluat din Auth Server
}


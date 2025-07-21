package com.asusoftware.clinic_api.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MaterialUsageRequest {
    @NotNull
    private UUID doctorId;
    @NotNull
    private UUID materialId;
    private UUID appointmentId;
    @NotNull
    private BigDecimal quantity;
    private String notes;
}


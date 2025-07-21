package com.asusoftware.clinic_api.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaterialRequest {
    @NotBlank
    private String name;
    private String unit;
}


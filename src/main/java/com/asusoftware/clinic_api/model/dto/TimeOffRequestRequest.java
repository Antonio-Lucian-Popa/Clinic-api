package com.asusoftware.clinic_api.model.dto;

import com.asusoftware.clinic_api.model.TimeOffRequestType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TimeOffRequestRequest {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private TimeOffRequestType type;
    private String reason;
}


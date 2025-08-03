package com.asusoftware.clinic_api.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
public record AppointmentDto(
        UUID id,
        UUID patientId,
        String patientName,
        UUID doctorId,
        String doctorName,
        String date,
        String time,
        long duration,
        String type,
        String status,
        String notes,
        String createdAt
) {}


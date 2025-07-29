package com.asusoftware.clinic_api.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentDto {
    private String id;
    private String patientName;
    private String type;
    private String date;
    private String time;
}

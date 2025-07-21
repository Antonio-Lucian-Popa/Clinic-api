package com.asusoftware.clinic_api.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
    // OWNER
    private Integer totalCabinets;
    private Integer totalDoctors;
    private Integer totalAssistants;

    // OWNER + DOCTOR + ASISTENT
    private Integer todayAppointments;
    private Integer todayMaterialUsages;

    // OWNER => cereri pending, alții => cereri proprii
    private Integer timeOffRequests;
}



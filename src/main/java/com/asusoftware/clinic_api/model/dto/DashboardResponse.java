package com.asusoftware.clinic_api.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
    private int totalCabinets;
    private int totalDoctors;
    private int totalAssistants;
    private int todayAppointments;
    private int todayMaterialUsages;
    private int pendingTimeOffRequests;
}


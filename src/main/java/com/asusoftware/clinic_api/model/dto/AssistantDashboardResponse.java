package com.asusoftware.clinic_api.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssistantDashboardResponse {
    private int todayAppointments;
    private int materialUsages;
    private int timeOffRequests;
}


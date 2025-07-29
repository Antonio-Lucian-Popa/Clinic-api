package com.asusoftware.clinic_api.model.dto;

import com.asusoftware.clinic_api.model.Appointment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DashboardStats {
    // OWNER
    private Integer totalCabinets;
    private Integer totalDoctors;
    private Integer totalAssistants;

    // OWNER + DOCTOR + ASISTENT
    private Integer todayAppointments;
    private Integer todayMaterialUsages;

    // OWNER => cereri pending, alții => cereri proprii
    private Integer timeOffRequests;

    private List<Appointment> recentAppointments;
}

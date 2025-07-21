package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.TimeOffRequestStatus;
import com.asusoftware.clinic_api.model.dto.DashboardResponse;
import com.asusoftware.clinic_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CabinetRepository cabinetRepo;
    private final DoctorRepository doctorRepo;
    private final AssistantRepository assistantRepo;
    private final AppointmentRepository appointmentRepo;
    private final MaterialUsageRepository materialUsageRepo;
    private final TimeOffRequestRepository timeOffRepo;

    public DashboardResponse getDashboard(Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList("roles");

        if (roles.contains("OWNER")) {
            UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenant_id"));
            int cabinets = cabinetRepo.findAllByOwnerId(tenantId).size();
            int doctors = doctorRepo.findByCabinet_OwnerId(tenantId).size();
            int assistants = assistantRepo.findByCabinet_OwnerId(tenantId).size();

            int appointments = appointmentRepo.countTodayAppointmentsByTenant(tenantId);
            int usages = materialUsageRepo.countTodayByTenant(tenantId);
            int pending = timeOffRepo.countByStatus(TimeOffRequestStatus.PENDING);

            return DashboardResponse.builder()
                    .totalCabinets(cabinets)
                    .totalDoctors(doctors)
                    .totalAssistants(assistants)
                    .todayAppointments(appointments)
                    .todayMaterialUsages(usages)
                    .pendingTimeOffRequests(pending)
                    .build();

        } else if (roles.contains("DOCTOR")) {
            UUID userId = UUID.fromString(jwt.getSubject());

            int appointments = appointmentRepo.countTodayAppointmentsByDoctor(userId);
            int usages = materialUsageRepo.countTodayByDoctor(userId);
            int ownTimeOffs = timeOffRepo.findByUserId(userId).size();

            return DashboardResponse.builder()
                    .todayAppointments(appointments)
                    .todayMaterialUsages(usages)
                    .pendingTimeOffRequests(ownTimeOffs)
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}

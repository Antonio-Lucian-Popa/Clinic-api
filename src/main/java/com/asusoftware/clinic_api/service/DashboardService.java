package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.TimeOffRequestStatus;
import com.asusoftware.clinic_api.model.dto.AppointmentDto;
import com.asusoftware.clinic_api.model.dto.AssistantDashboardResponse;
import com.asusoftware.clinic_api.model.dto.DashboardResponse;
import com.asusoftware.clinic_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final PatientRepository patientRepo;

    public DashboardResponse getDashboard(Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList("roles");

        if (roles.contains("OWNER")) {
            UUID tenantId = UUID.fromString(jwt.getSubject());

            int cabinets = cabinetRepo.findAllByOwnerId(tenantId).size();
            int doctors = doctorRepo.findByCabinet_OwnerId(tenantId).size();
            int assistants = assistantRepo.findAllByOwnerId(tenantId).size();
            int patients = patientRepo.countByTenantId(tenantId);

            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1);

            int appointments = appointmentRepo.countTodayAppointmentsByTenant(tenantId, startOfDay, endOfDay);
            int usages = materialUsageRepo.countTodayByTenant(tenantId);
            int pending = timeOffRepo.countByStatus(TimeOffRequestStatus.PENDING);
            int pendingAppointments = appointmentRepo.countPendingAppointmentsByTenant(tenantId); // 👈 nou

            return DashboardResponse.builder()
                    .totalCabinets(cabinets)
                    .totalDoctors(doctors)
                    .totalAssistants(assistants)
                    .totalPatients(patients)
                    .todayAppointments(appointments)
                    .pendingAppointments(pendingAppointments) // 👈 adaugă
                    .todayMaterialUsages(usages)
                    .timeOffRequests(pending)
                    .build();

        } else if (roles.contains("DOCTOR")) {
            UUID userId = UUID.fromString(jwt.getSubject());

            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1);
            int appointments = appointmentRepo.countTodayAppointmentsByDoctor(userId, startOfDay, endOfDay);
            int usages = materialUsageRepo.countTodayByDoctor(userId);
            int ownTimeOffs = timeOffRepo.findByUserId(userId).size();

            return DashboardResponse.builder()
                    .todayAppointments(appointments)
                    .todayMaterialUsages(usages)
                    .timeOffRequests(ownTimeOffs)
                    .build();

        } else if (roles.contains("ASSISTANT")) {
            UUID userId = UUID.fromString(jwt.getSubject());

            LocalDateTime start = LocalDate.now().atStartOfDay();
            LocalDateTime end = start.plusDays(1);
            int appointments = appointmentRepo.countTodayAppointmentsByAssistant(userId, start, end);
            int usages = materialUsageRepo.countTodayByAssistant(userId, start, end);
            int ownTimeOffs = timeOffRepo.findByUserId(userId).size();

            return DashboardResponse.builder()
                    .todayAppointments(appointments)
                    .todayMaterialUsages(usages)
                    .timeOffRequests(ownTimeOffs)
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    public List<AppointmentDto> getRecentAppointments(Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getSubject());

        return appointmentRepo
                .findRecentAppointmentsByTenant(tenantId, PageRequest.of(0, 5))
                .stream()
                .map(a -> AppointmentDto.builder()
                        .id(a.getId())
                        .patientName(a.getPatient().getFirstName() + " " + a.getPatient().getLastName())
                        .type(a.getNotes()) // sau poți adăuga un câmp nou 'type' dacă vrei mai multă claritate
                        .date(a.getStartTime().toLocalDate().toString())
                        .time(a.getStartTime().toLocalTime().toString())
                        .build()
                )
                .toList();
    }


}

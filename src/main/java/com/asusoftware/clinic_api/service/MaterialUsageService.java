package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.Appointment;
import com.asusoftware.clinic_api.model.Doctor;
import com.asusoftware.clinic_api.model.Material;
import com.asusoftware.clinic_api.model.MaterialUsage;
import com.asusoftware.clinic_api.model.dto.MaterialUsageRequest;
import com.asusoftware.clinic_api.repository.AppointmentRepository;
import com.asusoftware.clinic_api.repository.DoctorRepository;
import com.asusoftware.clinic_api.repository.MaterialRepository;
import com.asusoftware.clinic_api.repository.MaterialUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialUsageService {

    private final MaterialUsageRepository usageRepo;
    private final MaterialRepository materialRepo;
    private final DoctorRepository doctorRepo;
    private final AppointmentRepository appointmentRepo;

    public MaterialUsage create(MaterialUsageRequest dto, Jwt jwt) {
        UUID doctorId = dto.getDoctorId();
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Material material = materialRepo.findById(dto.getMaterialId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Appointment appointment = null;
        if (dto.getAppointmentId() != null) {
            appointment = appointmentRepo.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        MaterialUsage usage = new MaterialUsage();
        usage.setDoctor(doctor);
        usage.setMaterial(material);
        usage.setAppointment(appointment);
        usage.setQuantity(dto.getQuantity());
        usage.setNotes(dto.getNotes());
        return usageRepo.save(usage);
    }

    public List<MaterialUsage> getAllForDoctor(Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return usageRepo.findByDoctor_UserId(userId);
    }

    public List<MaterialUsage> getAllByTenant(Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenant_id"));
        return usageRepo.findByMaterial_TenantId(tenantId);
    }

    public List<MaterialUsage> getByAppointment(UUID appointmentId) {
        return usageRepo.findByAppointment_Id(appointmentId);
    }
}

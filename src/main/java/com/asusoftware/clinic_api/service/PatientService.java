package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.Patient;
import com.asusoftware.clinic_api.model.dto.PatientRequest;
import com.asusoftware.clinic_api.model.dto.PatientResponse;
import com.asusoftware.clinic_api.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getPatientsByTenant(Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenant_id"));
        return patientRepository.findAllByTenantId(tenantId);
    }

    public PatientResponse createPatient(PatientRequest dto, Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenant_id"));
        UUID createdBy = UUID.fromString(jwt.getSubject());

        Patient p = new Patient();
        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setEmail(dto.getEmail());
        p.setPhone(dto.getPhone());
        p.setGender(dto.getGender());
        p.setCnp(dto.getCnp());
        p.setDateOfBirth(dto.getDateOfBirth());
        p.setTenantId(tenantId);
        p.setCreatedBy(createdBy);

        // ✅ Noile câmpuri
        p.setAddress(dto.getAddress());
        p.setEmergencyContact(dto.getEmergencyContact());
        p.setMedicalHistory(dto.getMedicalHistory());
        p.setAllergies(dto.getAllergies());

        return toDto(patientRepository.save(p));
    }

    public PatientResponse getPatient(UUID id, Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenant_id"));
        return toDto(patientRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public PatientResponse updatePatient(UUID id, PatientRequest dto, Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenant_id"));
        Patient p = patientRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setEmail(dto.getEmail());
        p.setPhone(dto.getPhone());
        p.setGender(dto.getGender());
        p.setCnp(dto.getCnp());
        p.setDateOfBirth(dto.getDateOfBirth());

        // ✅ Noile câmpuri
        p.setAddress(dto.getAddress());
        p.setEmergencyContact(dto.getEmergencyContact());
        p.setMedicalHistory(dto.getMedicalHistory());
        p.setAllergies(dto.getAllergies());

        return toDto(patientRepository.save(p));
    }

    public void deletePatient(UUID id, Jwt jwt) {
        UUID tenantId = UUID.fromString(jwt.getClaimAsString("tenant_id"));
        Patient p = patientRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        patientRepository.delete(p);
    }

    public PatientResponse toDto(Patient p) {
        return new PatientResponse(
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getEmail(),
                p.getPhone(),
                p.getDateOfBirth(),
                p.getGender(),
                p.getAddress(),
                p.getEmergencyContact(),
                p.getMedicalHistory(),
                p.getAllergies(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }

}


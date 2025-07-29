package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.Patient;
import com.asusoftware.clinic_api.model.dto.PatientRequest;
import com.asusoftware.clinic_api.model.dto.PatientResponse;
import com.asusoftware.clinic_api.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT', 'OWNER')")
    @GetMapping
    public List<Patient> getAll(@AuthenticationPrincipal Jwt jwt) {
        return patientService.getPatientsByTenant(jwt);
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @PostMapping
    public ResponseEntity<PatientResponse> create(@RequestBody PatientRequest request,
                                                  @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.createPatient(request, jwt));
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT', 'OWNER')")
    @GetMapping("/{id}")
    public PatientResponse get(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        return patientService.getPatient(id, jwt);
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @PutMapping("/{id}")
    public PatientResponse update(@PathVariable UUID id,
                          @RequestBody PatientRequest request,
                          @AuthenticationPrincipal Jwt jwt) {
        return patientService.updatePatient(id, request, jwt);
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        patientService.deletePatient(id, jwt);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT', 'OWNER')")
    @GetMapping("/stats/new-this-month")
    public int getNewPatientsThisMonth(@AuthenticationPrincipal Jwt jwt) {
        return patientService.getNewPatientsThisMonth(jwt);
    }

}


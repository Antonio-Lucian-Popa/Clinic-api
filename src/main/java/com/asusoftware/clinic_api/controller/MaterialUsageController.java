package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.MaterialUsage;
import com.asusoftware.clinic_api.model.dto.MaterialUsageRequest;
import com.asusoftware.clinic_api.service.MaterialUsageService;
import jakarta.validation.Valid;
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
@RequestMapping("/material-usages")
@RequiredArgsConstructor
public class MaterialUsageController {

    private final MaterialUsageService service;

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    public ResponseEntity<MaterialUsage> create(@RequestBody @Valid MaterialUsageRequest request, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request, jwt));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping
    public List<MaterialUsage> getMyUsages(@AuthenticationPrincipal Jwt jwt) {
        return service.getAllForDoctor(jwt);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/tenant")
    public List<MaterialUsage> getAllByTenant(@AuthenticationPrincipal Jwt jwt) {
        return service.getAllByTenant(jwt);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'DOCTOR')")
    @GetMapping("/by-appointment/{appointmentId}")
    public List<MaterialUsage> getByAppointment(@PathVariable UUID appointmentId) {
        return service.getByAppointment(appointmentId);
    }
}

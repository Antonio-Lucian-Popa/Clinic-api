package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.Appointment;
import com.asusoftware.clinic_api.model.dto.AppointmentRequest;
import com.asusoftware.clinic_api.service.AppointmentService;
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
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @GetMapping
    public List<Appointment> getAll(@AuthenticationPrincipal Jwt jwt) {
        return appointmentService.getAll(jwt);
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody @Valid AppointmentRequest request,
                                              @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.create(request, jwt));
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @GetMapping("/{id}")
    public Appointment getOne(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        return appointmentService.getById(id, jwt);
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @PutMapping("/{id}")
    public Appointment update(@PathVariable UUID id,
                              @RequestBody AppointmentRequest request,
                              @AuthenticationPrincipal Jwt jwt) {
        return appointmentService.update(id, request, jwt);
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        appointmentService.delete(id, jwt);
        return ResponseEntity.noContent().build();
    }
}


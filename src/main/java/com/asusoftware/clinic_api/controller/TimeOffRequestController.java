package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.TimeOffRequest;
import com.asusoftware.clinic_api.model.dto.TimeOffRequestRequest;
import com.asusoftware.clinic_api.service.TimeOffRequestService;
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
@RequestMapping("/time-off-requests")
@RequiredArgsConstructor
public class TimeOffRequestController {

    private final TimeOffRequestService service;

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @PostMapping
    public ResponseEntity<TimeOffRequest> create(@RequestBody @Valid TimeOffRequestRequest dto,
                                                 @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto, jwt));
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @GetMapping("/my")
    public List<TimeOffRequest> getOwn(@AuthenticationPrincipal Jwt jwt) {
        return service.getOwn(jwt);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping
    public List<TimeOffRequest> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        service.approve(id, jwt);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        service.reject(id, jwt);
        return ResponseEntity.noContent().build();
    }
}


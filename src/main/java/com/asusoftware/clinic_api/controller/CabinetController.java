package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.dto.CabinetRequest;
import com.asusoftware.clinic_api.model.dto.CabinetResponse;
import com.asusoftware.clinic_api.service.CabinetService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cabinets")
@RequiredArgsConstructor
public class CabinetController {

    private final CabinetService cabinetService;

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping
    public List<CabinetResponse> getCabinets(@AuthenticationPrincipal Jwt jwt) {
        UUID ownerId = UUID.fromString(jwt.getSubject());
        return cabinetService.getCabinetsForOwner(ownerId);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity<CabinetResponse> createCabinet(@Valid @RequestBody CabinetRequest request,
                                                         @AuthenticationPrincipal Jwt jwt) {
        UUID ownerId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.status(HttpStatus.CREATED).body(cabinetService.createCabinet(request, ownerId));
    }

    @GetMapping("/{id}")
    public CabinetResponse getCabinet(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID ownerId = UUID.fromString(jwt.getSubject());
        return cabinetService.getCabinetById(id, ownerId);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{id}")
    public CabinetResponse updateCabinet(@PathVariable UUID id,
                                         @Valid @RequestBody CabinetRequest request,
                                         @AuthenticationPrincipal Jwt jwt) {
        UUID ownerId = UUID.fromString(jwt.getSubject());
        return cabinetService.updateCabinet(id, request, ownerId);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCabinet(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID ownerId = UUID.fromString(jwt.getSubject());
        cabinetService.deleteCabinet(id, ownerId);
        return ResponseEntity.noContent().build();
    }
}

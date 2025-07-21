package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.Material;
import com.asusoftware.clinic_api.model.dto.MaterialRequest;
import com.asusoftware.clinic_api.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService service;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity<Material> create(@RequestBody @Valid MaterialRequest request, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request, jwt));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'DOCTOR')")
    @GetMapping
    public List<Material> list(@AuthenticationPrincipal Jwt jwt) {
        return service.getAll(jwt);
    }
}


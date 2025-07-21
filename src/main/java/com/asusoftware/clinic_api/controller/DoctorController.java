package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.Doctor;
import com.asusoftware.clinic_api.model.dto.DoctorRequest;
import com.asusoftware.clinic_api.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cabinets/{cabinetId}/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity<?> addDoctor(@PathVariable UUID cabinetId,
                                       @RequestBody @Valid DoctorRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.addDoctorToCabinet(cabinetId, dto));
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping
    public List<Doctor> getDoctors(@PathVariable UUID cabinetId) {
        return doctorService.getDoctorsByCabinet(cabinetId);
    }
}


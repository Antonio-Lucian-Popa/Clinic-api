package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorManagementController {
    private final DoctorService doctorService;

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> removeDoctor(@PathVariable UUID doctorId) {
        doctorService.removeDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }
}

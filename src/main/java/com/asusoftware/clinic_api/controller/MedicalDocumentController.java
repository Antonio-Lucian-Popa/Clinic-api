package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.MedicalDocument;
import com.asusoftware.clinic_api.service.MedicalDocumentService;

import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class MedicalDocumentController {

    private final MedicalDocumentService service;

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @PostMapping("/patients/{patientId}/documents")
    public ResponseEntity<MedicalDocument> upload(@PathVariable UUID patientId,
                                                  @RequestParam("file") MultipartFile file,
                                                  @RequestParam(required = false) String type,
                                                  @RequestParam(required = false) String notes,
                                                  @AuthenticationPrincipal Jwt jwt) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.upload(patientId, file, type, notes, jwt));
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT', 'OWNER')")
    @GetMapping("/patients/{patientId}/documents")
    public List<MedicalDocument> list(@PathVariable UUID patientId) {
        return service.getAllForPatient(patientId);
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT', 'OWNER')")
    @GetMapping("/documents/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable UUID id) {
        Resource file = service.download(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        service.delete(id, jwt);
        return ResponseEntity.noContent().build();
    }
}


package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.Assistant;
import com.asusoftware.clinic_api.model.dto.AssistantRequest;
import com.asusoftware.clinic_api.service.AssistantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors/{doctorId}/assistants")
@RequiredArgsConstructor
public class AssistantController {

    private final AssistantService assistantService;

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    public ResponseEntity<Void> linkAssistant(@PathVariable UUID doctorId,
                                              @RequestBody @Valid AssistantRequest dto) {
        assistantService.linkAssistantToDoctor(doctorId, dto.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping
    public List<Assistant> getAssistants(@PathVariable UUID doctorId) {
        return assistantService.getAssistantsForDoctor(doctorId);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @DeleteMapping("/{assistantId}")
    public ResponseEntity<Void> unlink(@PathVariable UUID doctorId, @PathVariable UUID assistantId) {
        assistantService.unlinkAssistant(doctorId, assistantId);
        return ResponseEntity.noContent().build();
    }
}

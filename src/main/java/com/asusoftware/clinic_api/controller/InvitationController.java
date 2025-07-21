package com.asusoftware.clinic_api.controller;

import com.asusoftware.clinic_api.model.Invitation;
import com.asusoftware.clinic_api.model.dto.InvitationRequest;
import com.asusoftware.clinic_api.service.InvitationService;
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
@RequestMapping("/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PreAuthorize("hasAnyRole('OWNER', 'DOCTOR')")
    @PostMapping
    public ResponseEntity<Invitation> create(@RequestBody @Valid InvitationRequest dto,
                                             @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invitationService.create(dto, jwt));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mine")
    public List<Invitation> getOwn(@AuthenticationPrincipal Jwt jwt) {
        return invitationService.getMyInvitations(jwt);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/accept")
    public ResponseEntity<Void> accept(@PathVariable UUID id,
                                       @AuthenticationPrincipal Jwt jwt) {
        invitationService.accept(id, jwt);
        return ResponseEntity.ok().build();
    }
}


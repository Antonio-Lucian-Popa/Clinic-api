package com.asusoftware.clinic_api.model.dto;

import com.asusoftware.clinic_api.model.InvitationRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class InvitationRequest {
    @Email
    @NotBlank
    private String email;

    @NotNull
    private InvitationRole role;

    private UUID cabinetId; // pentru DOCTOR
    private UUID doctorId;  // pentru ASISTENT
}

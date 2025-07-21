package com.asusoftware.clinic_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invitations")
@Data
public class Invitation {

    @Id
    @GeneratedValue
    private UUID id;

    private String email;

    @Enumerated(EnumType.STRING)
    private InvitationRole role; // DOCTOR or ASISTENT

    private UUID cabinetId; // pentru DOCTOR
    private UUID doctorId;  // pentru ASISTENT

    private UUID invitedBy; // userId OWNER sau DOCTOR

    @Enumerated(EnumType.STRING)
    private InvitationStatus status; // PENDING, ACCEPTED, EXPIRED

    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;
}

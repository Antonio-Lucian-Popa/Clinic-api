package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.*;
import com.asusoftware.clinic_api.model.dto.InvitationRequest;
import com.asusoftware.clinic_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepo;
    private final DoctorRepository doctorRepo;
    private final AssistantRepository assistantRepo;
    private final DoctorAssistantRepository doctorAssistantRepo;
    private final CabinetRepository cabinetRepo;

    public Invitation create(InvitationRequest dto, Jwt jwt) {
        UUID invitedBy = UUID.fromString(jwt.getSubject());

        Invitation invitation = new Invitation();
        invitation.setEmail(dto.getEmail());
        invitation.setRole(dto.getRole());
        invitation.setCabinetId(dto.getCabinetId());
        invitation.setDoctorId(dto.getDoctorId());
        invitation.setInvitedBy(invitedBy);
        invitation.setStatus(InvitationStatus.PENDING);
        invitation.setCreatedAt(LocalDateTime.now());

        return invitationRepo.save(invitation);
    }

    public List<Invitation> getMyInvitations(Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        return invitationRepo.findByEmailAndStatus(email, InvitationStatus.PENDING);
    }

    public void accept(UUID id, Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        UUID userId = UUID.fromString(jwt.getSubject());

        Invitation invitation = invitationRepo.findByIdAndEmail(id, email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invitation already handled");
        }

        if (invitation.getRole() == InvitationRole.DOCTOR) {
            Doctor doctor = new Doctor();
            doctor.setUserId(userId);

            Cabinet cabinet = cabinetRepo.findById(invitation.getCabinetId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            doctor.setCabinet(cabinet);

            doctor.setCabinet(cabinet);

            doctorRepo.save(doctor);
        }
        else if (invitation.getRole() == InvitationRole.ASSISTANT) {
            Assistant assistant = new Assistant();
            assistant.setUserId(userId);
            assistantRepo.save(assistant);

            Doctor doctor = doctorRepo.findById(invitation.getDoctorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            DoctorAssistant link = new DoctorAssistant();
            link.setDoctor(doctor);
            link.setAssistant(assistant);
            doctorAssistantRepo.save(link);
        }

        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitation.setAcceptedAt(LocalDateTime.now());
        invitationRepo.save(invitation);
    }
}


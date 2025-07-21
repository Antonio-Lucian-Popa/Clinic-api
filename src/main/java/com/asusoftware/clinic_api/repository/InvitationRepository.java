package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Invitation;
import com.asusoftware.clinic_api.model.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, UUID> {
    List<Invitation> findByEmailAndStatus(String email, InvitationStatus status);
    Optional<Invitation> findByIdAndEmail(UUID id, String email);
}

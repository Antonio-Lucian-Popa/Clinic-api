package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant, UUID> {
    Optional<Assistant> findByUserId(UUID userId);
}

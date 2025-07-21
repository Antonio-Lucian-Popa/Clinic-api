package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CabinetRepository extends JpaRepository<Cabinet, UUID> {
    List<Cabinet> findAllByOwnerId(UUID ownerId);
    Optional<Cabinet> findByIdAndOwnerId(UUID id, UUID ownerId);
}


package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaterialRepository extends JpaRepository<Material, UUID> {
    List<Material> findByTenantId(UUID tenantId);
    Optional<Material> findByIdAndTenantId(UUID id, UUID tenantId);
}


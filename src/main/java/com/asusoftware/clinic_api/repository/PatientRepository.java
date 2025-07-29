package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    List<Patient> findAllByTenantId(UUID tenantId);
    Optional<Patient> findByIdAndTenantId(UUID id, UUID tenantId);

    int countByTenantId(UUID tenantId);
}


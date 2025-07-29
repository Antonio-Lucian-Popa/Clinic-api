package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    List<Patient> findAllByTenantId(UUID tenantId);
    Optional<Patient> findByIdAndTenantId(UUID id, UUID tenantId);

    int countByTenantId(UUID tenantId);
    @Query("""
    SELECT COUNT(p) FROM Patient p
    WHERE p.tenantId = :tenantId
    AND p.createdAt >= :startOfMonth
""")
    int countPatientsCreatedThisMonth(@Param("tenantId") UUID tenantId, @Param("startOfMonth") LocalDateTime startOfMonth);
}


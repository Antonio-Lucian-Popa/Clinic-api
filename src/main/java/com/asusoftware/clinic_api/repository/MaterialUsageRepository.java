package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.MaterialUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MaterialUsageRepository extends JpaRepository<MaterialUsage, UUID> {
    List<MaterialUsage> findByDoctor_UserId(UUID doctorUserId);
    List<MaterialUsage> findByMaterial_TenantId(UUID tenantId);
    List<MaterialUsage> findByAppointment_Id(UUID appointmentId);

    @Query("""
    SELECT COUNT(mu)
    FROM MaterialUsage mu
    WHERE mu.material.tenantId = :tenantId
    AND CAST(mu.usageDate AS date) = CURRENT_DATE
""")
    int countTodayByTenant(@Param("tenantId") UUID tenantId);


    @Query("""
    SELECT COUNT(mu)
    FROM MaterialUsage mu
    WHERE mu.doctor.id = :doctorId
    AND FUNCTION('DATE', mu.usageDate) = CURRENT_DATE
""")
    int countTodayByDoctor(@Param("doctorId") UUID doctorId);


    @Query("""
    SELECT COUNT(mu)
    FROM MaterialUsage mu
    WHERE mu.assistant.id = :assistantId
    AND mu.usageDate >= :start
    AND mu.usageDate < :end
""")
    int countTodayByAssistant(UUID assistantId, LocalDateTime start, LocalDateTime end);




}

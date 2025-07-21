package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.MaterialUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MaterialUsageRepository extends JpaRepository<MaterialUsage, UUID> {
    List<MaterialUsage> findByDoctor_UserId(UUID doctorUserId);
    List<MaterialUsage> findByMaterial_TenantId(UUID tenantId);
    List<MaterialUsage> findByAppointment_Id(UUID appointmentId);

    @Query("SELECT COUNT(m) FROM MaterialUsage m WHERE DATE(m.usageDate) = CURRENT_DATE AND m.material.tenantId = :tenantId")
    int countTodayByTenant(@Param("tenantId") UUID tenantId);

    @Query("SELECT COUNT(m) FROM MaterialUsage m WHERE DATE(m.usageDate) = CURRENT_DATE AND m.doctor.userId = :userId")
    int countTodayByDoctor(@Param("userId") UUID userId);

}

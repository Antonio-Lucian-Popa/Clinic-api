package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findAllByDoctor_UserIdOrAssistant_UserId(UUID doctorUserId, UUID assistantUserId);
    Optional<Appointment> findByIdAndDoctor_UserIdOrAssistant_UserId(UUID id, UUID doctorUserId, UUID assistantUserId);

    @Query("""
    SELECT COUNT(a) FROM Appointment a
    WHERE a.doctor.cabinet.ownerId = :tenantId
    AND a.startTime BETWEEN :startOfDay AND :endOfDay
""")
    int countTodayAppointmentsByTenant(
            @Param("tenantId") UUID tenantId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );


    @Query("""
    SELECT COUNT(a) FROM Appointment a
    WHERE a.doctor.id = :doctorId
    AND a.startTime BETWEEN :startOfDay AND :endOfDay
""")
    int countTodayAppointmentsByDoctor(
            @Param("doctorId") UUID doctorId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );


    @Query("""
    SELECT COUNT(a) FROM Appointment a
    WHERE a.assistant.id = :assistantId
    AND a.startTime BETWEEN :startOfDay AND :endOfDay
""")
    int countTodayAppointmentsByAssistant(
            @Param("assistantId") UUID assistantId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );


    @Query("""
    SELECT a FROM Appointment a
    WHERE a.doctor.cabinet.ownerId = :tenantId
    ORDER BY a.startTime DESC
""")
    List<Appointment> findRecentAppointmentsByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("""
    SELECT COUNT(a) FROM Appointment a
    WHERE a.doctor.cabinet.ownerId = :tenantId
    AND a.status = 'PENDING'
""")
    int countPendingAppointmentsByTenant(@Param("tenantId") UUID tenantId);


}

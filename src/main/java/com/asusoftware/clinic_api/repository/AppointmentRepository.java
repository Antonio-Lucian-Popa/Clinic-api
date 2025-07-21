package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findAllByDoctor_UserIdOrAssistant_UserId(UUID doctorUserId, UUID assistantUserId);
    Optional<Appointment> findByIdAndDoctor_UserIdOrAssistant_UserId(UUID id, UUID doctorUserId, UUID assistantUserId);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE DATE(a.startTime) = CURRENT_DATE AND a.doctor.cabinet.owner.id = :tenantId")
    int countTodayAppointmentsByTenant(@Param("tenantId") UUID tenantId);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE DATE(a.startTime) = CURRENT_DATE AND a.doctor.userId = :userId")
    int countTodayAppointmentsByDoctor(@Param("userId") UUID userId);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE DATE(a.startTime) = CURRENT_DATE AND a.assistant.userId = :userId")
    int countTodayAppointmentsByAssistant(@Param("userId") UUID userId);


}

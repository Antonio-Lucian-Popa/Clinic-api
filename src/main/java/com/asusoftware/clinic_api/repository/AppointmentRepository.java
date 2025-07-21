package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findAllByDoctor_UserIdOrAssistant_UserId(UUID doctorUserId, UUID assistantUserId);
    Optional<Appointment> findByIdAndDoctor_UserIdOrAssistant_UserId(UUID id, UUID doctorUserId, UUID assistantUserId);
}

package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.DoctorAssistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorAssistantRepository extends JpaRepository<DoctorAssistant, UUID> {
    List<DoctorAssistant> findByDoctor_Id(UUID doctorId);
    Optional<DoctorAssistant> findByAssistant_UserId(UUID userId);

    void deleteByDoctor_IdAndAssistant_Id(UUID doctorId, UUID assistantId);
}

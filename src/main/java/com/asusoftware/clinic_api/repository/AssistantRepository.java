package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant, UUID> {
    Optional<Assistant> findByUserId(UUID userId);

    @Query("""
    SELECT COUNT(da.assistant.id)
    FROM DoctorAssistant da
    WHERE da.doctor.cabinet.ownerId = :ownerId
""")
    int countAssistantsByOwner(@Param("ownerId") UUID ownerId);


    @Query("""
    SELECT a FROM Assistant a
    JOIN DoctorAssistant da ON da.assistant = a
    JOIN Doctor d ON da.doctor = d
    JOIN Cabinet c ON d.cabinet = c
    WHERE c.ownerId = :ownerId
""")
    List<Assistant> findAllByOwnerId(UUID ownerId);


}

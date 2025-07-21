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

    @Query("SELECT COUNT(da.assistant.id) FROM DoctorAssistant da WHERE da.doctor.cabinet.owner.id = :ownerId")
    int countAssistantsByOwner(@Param("ownerId") UUID ownerId);

    @Query("SELECT a FROM Assistant a WHERE a.doctor.cabinet.ownerId = :ownerId")
    List<Assistant> findByCabinet_OwnerId(@Param("ownerId") UUID ownerId);


}

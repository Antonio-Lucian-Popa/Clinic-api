package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.MedicalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicalDocumentRepository extends JpaRepository<MedicalDocument, UUID> {
    List<MedicalDocument> findByPatient_Id(UUID patientId);
    Optional<MedicalDocument> findByIdAndUploadedBy(UUID id, UUID userId);
}


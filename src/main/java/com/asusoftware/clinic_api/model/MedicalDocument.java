package com.asusoftware.clinic_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "medical_documents")
@Data
@NoArgsConstructor
public class MedicalDocument extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "uploaded_by", nullable = false)
    private UUID uploadedBy;

    private String documentType;
    private String fileUrl;
    private String notes;
    private LocalDateTime uploadedAt = LocalDateTime.now();
}


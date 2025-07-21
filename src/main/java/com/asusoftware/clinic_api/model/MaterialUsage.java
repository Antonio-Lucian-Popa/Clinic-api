package com.asusoftware.clinic_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "material_usages")
@Data
@NoArgsConstructor
public class MaterialUsage extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(precision = 10, scale = 2)
    private BigDecimal quantity;

    private LocalDateTime usageDate;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "assistant_id")
    private Assistant assistant;

}


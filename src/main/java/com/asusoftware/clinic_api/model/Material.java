package com.asusoftware.clinic_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "materials")
@Data
@NoArgsConstructor
public class Material extends BaseEntity {
    private String name;
    private String unit;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;
}

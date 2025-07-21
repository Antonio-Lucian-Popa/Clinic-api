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
@Table(name = "assistants")
@Data
@NoArgsConstructor
public class Assistant extends BaseEntity {
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    private Boolean active = true;
}

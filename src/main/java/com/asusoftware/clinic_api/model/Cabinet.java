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
@Table(name = "cabinets")
@Data
@NoArgsConstructor
public class Cabinet extends BaseEntity {
    private String name;
    private String address;
    private String phone;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;
}

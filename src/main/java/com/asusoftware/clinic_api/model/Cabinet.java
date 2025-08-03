package com.asusoftware.clinic_api.model;


import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

}

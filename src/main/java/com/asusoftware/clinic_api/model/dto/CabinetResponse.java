package com.asusoftware.clinic_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CabinetResponse {
    private UUID id;
    private String name;
    private String address;
    private String phone;
}

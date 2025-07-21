package com.asusoftware.clinic_api.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String cnp;
}


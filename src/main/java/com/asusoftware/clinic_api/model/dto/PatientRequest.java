package com.asusoftware.clinic_api.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String cnp;

    private String address;
    private String emergencyContact;
    private List<String> medicalHistory;
    private List<String> allergies;
}


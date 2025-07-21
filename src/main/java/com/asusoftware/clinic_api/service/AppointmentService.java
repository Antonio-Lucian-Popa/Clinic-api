package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.Appointment;
import com.asusoftware.clinic_api.model.Assistant;
import com.asusoftware.clinic_api.model.Doctor;
import com.asusoftware.clinic_api.model.Patient;
import com.asusoftware.clinic_api.model.dto.AppointmentRequest;
import com.asusoftware.clinic_api.repository.AppointmentRepository;
import com.asusoftware.clinic_api.repository.AssistantRepository;
import com.asusoftware.clinic_api.repository.DoctorRepository;
import com.asusoftware.clinic_api.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final AssistantRepository assistantRepository;
    private final PatientRepository patientRepository;

    public List<Appointment> getAll(Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return appointmentRepository.findAllByDoctor_UserIdOrAssistant_UserId(userId, userId);
    }

    public Appointment create(AppointmentRequest dto, Jwt jwt) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Assistant assistant = null;
        if (dto.getAssistantId() != null) {
            assistant = assistantRepository.findById(dto.getAssistantId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        Appointment a = new Appointment();
        a.setDoctor(doctor);
        a.setPatient(patient);
        a.setAssistant(assistant);
        a.setStartTime(dto.getStartTime());
        a.setEndTime(dto.getEndTime());
        a.setStatus("SCHEDULED");
        a.setNotes(dto.getNotes());

        return appointmentRepository.save(a);
    }

    public Appointment getById(UUID id, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return appointmentRepository.findByIdAndDoctor_UserIdOrAssistant_UserId(id, userId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Appointment update(UUID id, AppointmentRequest dto, Jwt jwt) {
        Appointment a = getById(id, jwt);

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Assistant assistant = null;
        if (dto.getAssistantId() != null) {
            assistant = assistantRepository.findById(dto.getAssistantId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        a.setDoctor(doctor);
        a.setPatient(patient);
        a.setAssistant(assistant);
        a.setStartTime(dto.getStartTime());
        a.setEndTime(dto.getEndTime());
        a.setNotes(dto.getNotes());

        return appointmentRepository.save(a);
    }

    public void delete(UUID id, Jwt jwt) {
        Appointment a = getById(id, jwt);
        appointmentRepository.delete(a);
    }
}

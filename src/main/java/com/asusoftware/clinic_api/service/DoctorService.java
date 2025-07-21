package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.Cabinet;
import com.asusoftware.clinic_api.model.Doctor;
import com.asusoftware.clinic_api.model.dto.DoctorRequest;
import com.asusoftware.clinic_api.repository.CabinetRepository;
import com.asusoftware.clinic_api.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepo;
    private final CabinetRepository cabinetRepo;

    public Doctor addDoctorToCabinet(UUID cabinetId, DoctorRequest dto) {
        Cabinet cabinet = cabinetRepo.findById(cabinetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Doctor doctor = new Doctor();
        doctor.setUserId(dto.getUserId());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setRoomLabel(dto.getRoomLabel());
        doctor.setCabinet(cabinet);
        return doctorRepo.save(doctor);
    }

    public List<Doctor> getDoctorsByCabinet(UUID cabinetId) {
        return doctorRepo.findByCabinet_Id(cabinetId);
    }

    public void removeDoctor(UUID doctorId) {
        doctorRepo.deleteById(doctorId);
    }
}


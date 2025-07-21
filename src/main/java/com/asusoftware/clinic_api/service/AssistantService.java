package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.Assistant;
import com.asusoftware.clinic_api.model.Doctor;
import com.asusoftware.clinic_api.model.DoctorAssistant;
import com.asusoftware.clinic_api.repository.AssistantRepository;
import com.asusoftware.clinic_api.repository.DoctorAssistantRepository;
import com.asusoftware.clinic_api.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssistantService {

    private final AssistantRepository assistantRepo;
    private final DoctorRepository doctorRepo;
    private final DoctorAssistantRepository linkRepo;

    public void linkAssistantToDoctor(UUID doctorId, UUID userId) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Assistant assistant = assistantRepo.findByUserId(userId)
                .orElseGet(() -> {
                    Assistant newAs = new Assistant();
                    newAs.setUserId(userId);
                    return assistantRepo.save(newAs);
                });

        DoctorAssistant link = new DoctorAssistant();
        link.setDoctor(doctor);
        link.setAssistant(assistant);
        linkRepo.save(link);
    }

    public List<Assistant> getAssistantsForDoctor(UUID doctorId) {
        return linkRepo.findByDoctor_Id(doctorId)
                .stream()
                .map(DoctorAssistant::getAssistant)
                .toList();
    }

    public void unlinkAssistant(UUID doctorId, UUID assistantId) {
        linkRepo.deleteByDoctor_IdAndAssistant_Id(doctorId, assistantId);
    }
}

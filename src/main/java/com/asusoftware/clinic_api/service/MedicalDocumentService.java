package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.MedicalDocument;
import com.asusoftware.clinic_api.model.Patient;
import com.asusoftware.clinic_api.repository.MedicalDocumentRepository;
import com.asusoftware.clinic_api.repository.PatientRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalDocumentService {

    private final MedicalDocumentRepository repo;
    private final PatientRepository patientRepo;

    @Value("${clinic.documents.upload-dir}")
    private String uploadDirPath;

    private Path uploadDir;

    @PostConstruct
    public void init() throws IOException {
        uploadDir = Paths.get(uploadDirPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public List<MedicalDocument> getAllForPatient(UUID patientId) {
        return repo.findByPatient_Id(patientId);
    }

    @Transactional
    public MedicalDocument upload(UUID patientId, MultipartFile file, String type, String notes, Jwt jwt) throws IOException {
        UUID userId = UUID.fromString(jwt.getSubject());
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        MedicalDocument doc = new MedicalDocument();
        doc.setPatient(patient);
        doc.setUploadedBy(userId);
        doc.setDocumentType(type);
        doc.setNotes(notes);
        doc.setFileUrl(filename); // doar numele; reconstruim pathul la download
        return repo.save(doc);
    }

    public Resource download(UUID id) {
        MedicalDocument doc = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Path path = uploadDir.resolve(doc.getFileUrl());
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void delete(UUID id, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        MedicalDocument doc = repo.findByIdAndUploadedBy(id, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
        Path path = uploadDir.resolve(doc.getFileUrl());
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {}
        repo.delete(doc);
    }
}


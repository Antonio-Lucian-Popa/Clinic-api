package com.asusoftware.clinic_api.service;

import com.asusoftware.clinic_api.model.TimeOffRequest;
import com.asusoftware.clinic_api.model.TimeOffRequestStatus;
import com.asusoftware.clinic_api.model.dto.TimeOffRequestRequest;
import com.asusoftware.clinic_api.repository.TimeOffRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TimeOffRequestService {

    private final TimeOffRequestRepository repo;

    public TimeOffRequest create(TimeOffRequestRequest dto, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());

        TimeOffRequest request = new TimeOffRequest();
        request.setUserId(userId);
        request.setStartDate(dto.getStartDate());
        request.setEndDate(dto.getEndDate());
        request.setType(dto.getType());
        request.setReason(dto.getReason());
        request.setStatus(TimeOffRequestStatus.PENDING);
        return repo.save(request);
    }

    public List<TimeOffRequest> getOwn(Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return repo.findByUserId(userId);
    }

    public List<TimeOffRequest> getAll() {
        return repo.findAll(); // sau doar `PENDING` dacă vrei
    }

    public void approve(UUID id, Jwt jwt) {
        UUID ownerId = UUID.fromString(jwt.getSubject());
        TimeOffRequest r = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        r.setStatus(TimeOffRequestStatus.APPROVED);
        r.setApprovedBy(ownerId);
        r.setApprovedAt(LocalDateTime.now());
        repo.save(r);
    }

    public void reject(UUID id, Jwt jwt) {
        UUID ownerId = UUID.fromString(jwt.getSubject());
        TimeOffRequest r = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        r.setStatus(TimeOffRequestStatus.REJECTED);
        r.setApprovedBy(ownerId);
        r.setApprovedAt(LocalDateTime.now());
        repo.save(r);
    }
}


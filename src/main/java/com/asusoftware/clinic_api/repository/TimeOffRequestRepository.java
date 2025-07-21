package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.TimeOffRequest;
import com.asusoftware.clinic_api.model.TimeOffRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimeOffRequestRepository extends JpaRepository<TimeOffRequest, UUID> {
    List<TimeOffRequest> findByUserId(UUID userId);
    List<TimeOffRequest> findByStatus(TimeOffRequestStatus status);
    int countByStatus(TimeOffRequestStatus status);

}


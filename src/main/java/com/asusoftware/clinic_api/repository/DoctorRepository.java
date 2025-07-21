package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findByCabinet_Id(UUID cabinetId);
}

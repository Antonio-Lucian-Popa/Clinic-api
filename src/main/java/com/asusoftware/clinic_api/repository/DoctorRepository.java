package com.asusoftware.clinic_api.repository;

import com.asusoftware.clinic_api.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findByCabinet_Id(UUID cabinetId);

    @Query("SELECT d FROM Doctor d WHERE d.cabinet.ownerId = :ownerId")
    List<Doctor> findByCabinet_OwnerId(@Param("ownerId") UUID ownerId);

}

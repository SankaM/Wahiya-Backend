package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA interface to query tutorial data
 *
 * @author Priyantha Weerakoon
 */
public interface PatientEntityRepository extends JpaRepository<PatientEntity, UUID> {

    void deleteByPatientId(UUID patientId);

    List<PatientEntity> findByDoctorIdAndIsActive(String doctorId, Boolean isActive);

    PatientEntity findByDoctorIdAndPatientId(String doctorId, UUID patientId);
}
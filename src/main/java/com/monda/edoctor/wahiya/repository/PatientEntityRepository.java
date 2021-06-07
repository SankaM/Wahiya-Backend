package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA interface to query tutorial data
 *
 * @author Priyantha Weerakoon
 */
public interface PatientEntityRepository extends JpaRepository<PatientEntity, UUID>, JpaSpecificationExecutor {

    void deleteByPatientId(UUID patientId);

    List<PatientEntity> findByDoctorIdAndIsActive(UUID doctorId, Boolean isActive);

    Optional<PatientEntity> findByDoctorIdAndPatientId(UUID doctorId, UUID patientId);
}
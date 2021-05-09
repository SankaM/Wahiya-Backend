package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.BasicPatientInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA interface to query tutorial data
 *
 * @author Priyantha Weerakoon
 */
public interface BasicPatientInfoRepository extends JpaRepository<BasicPatientInfoEntity, UUID> {

    void deleteByPatientId(UUID patientId);

    List<BasicPatientInfoEntity> findByDoctorId(String doctorId);
}
package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.DoseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoseEntityRepository extends JpaRepository<DoseEntity, UUID> {

    List<DoseEntity> findByPrescriptionId(UUID prescriptionId);

}

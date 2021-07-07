package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.DosageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DosageEntityRepository extends JpaRepository<DosageEntity, UUID> {

    List<DosageEntity> findByPrescriptionId(UUID prescriptionId);

}

package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, UUID> {

    List<AttachmentEntity> findByDoctorIdAndPatientId(UUID doctorId, UUID patientId);
    Optional<AttachmentEntity> findById(UUID id);
}

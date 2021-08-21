package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.InventoryEntity;
import com.monda.edoctor.wahiya.model.PrescriptionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, UUID> {
    List<PrescriptionEntity> findByPatientIdOrderByPrescriptionDateAsc(UUID patientId);

    @Query("select p from PrescriptionEntity p where p.patient.id = :patientId and p.lastTreatmentDate >= :currentDate ")
    List<PrescriptionEntity> findNotExpiredPrescription(UUID patientId, LocalDateTime currentDate);

    // Todo: Low performance query. Find a way to tune-in the query
    @Query("SELECT p FROM PrescriptionEntity p WHERE p.doctor.id = :doctorId AND (lower(p.patient.firstName) LIKE lower(concat('%',:patientName,'%')) OR lower(p.patient.lastName) LIKE lower(concat('%',:patientName,'%')))")
    List<PrescriptionEntity> findByDoctorIdAndPatientName(UUID doctorId, String patientName, Pageable pageable);

    @Query("SELECT p FROM PrescriptionEntity p WHERE p.doctor.id = :doctorId")
    List<PrescriptionEntity> findByDoctorId(UUID doctorId, Pageable pageable);
}

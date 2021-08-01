//package com.monda.edoctor.wahiya._old.repository;
//
//import com.monda.edoctor.wahiya.model.PrescriptionEntity;
//import org.apache.catalina.LifecycleState;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.UUID;
//
//public interface PrescriptionEntityRepository extends JpaRepository<PrescriptionEntity, UUID> {
//
//    List<PrescriptionEntity> findByPatientIdOrderByPrescriptionDateDesc(UUID patientId);
//}

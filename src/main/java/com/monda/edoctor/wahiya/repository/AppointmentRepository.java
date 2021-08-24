package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {
    @Query("select a from AppointmentEntity a where a.doctor.id = :doctorId and a.appointmentDate < :beforeDate order by a.appointmentDate desc")
    List<AppointmentEntity> findBeforeDate(UUID doctorId, LocalDateTime beforeDate);

    @Query("select a from AppointmentEntity a where a.doctor.id = :doctorId and a.appointmentDate >= :afterDate")
    List<AppointmentEntity> findAfterDate(UUID doctorId, LocalDateTime afterDate);

    @Query("select a from AppointmentEntity a where a.doctor.id = :doctorId and a.id = :appointmentId")
    Optional<AppointmentEntity> findByDoctorIdAndAppointmentId(UUID doctorId, UUID appointmentId);
}

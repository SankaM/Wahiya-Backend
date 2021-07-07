package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppointmentEntityRepository extends JpaRepository<AppointmentEntity, UUID> {
    
}

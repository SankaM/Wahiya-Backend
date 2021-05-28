package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.Optional;
import java.util.UUID;

public interface DoctorEntityRepository extends JpaRepository<DoctorEntity, UUID> {

    Optional<DoctorEntity> findByUserName(String userName);


}

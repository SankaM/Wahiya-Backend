package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.DrugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DrugEntityRepository extends JpaRepository<DrugEntity, UUID> , JpaSpecificationExecutor {
}

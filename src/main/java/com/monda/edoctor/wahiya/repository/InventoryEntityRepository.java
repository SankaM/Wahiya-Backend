package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryEntityRepository extends JpaRepository<InventoryEntity, UUID> {

}

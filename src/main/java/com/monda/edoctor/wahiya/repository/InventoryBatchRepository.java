package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.InventoryBatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryBatchRepository extends JpaRepository<InventoryBatchEntity, UUID> {
}

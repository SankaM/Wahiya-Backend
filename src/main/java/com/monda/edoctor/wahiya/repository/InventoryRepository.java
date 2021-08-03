package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.InventoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends PagingAndSortingRepository<InventoryEntity, UUID>, JpaRepository<InventoryEntity, UUID> {
    // Todo: Low performance query. Find a way to tune-in the query
    @Query("SELECT i FROM InventoryEntity i WHERE i.doctor.id = :doctorId AND lower(i.drug.name) LIKE lower(concat('%',:drugName,'%'))")
    List<InventoryEntity> findByDrugName(UUID doctorId, String drugName, Pageable pageable);

    // Todo: Low performance query. Find a way to tune-in the query
    @Query("SELECT i FROM InventoryEntity i WHERE i.doctor.id = :doctorId AND lower(i.drug.type) LIKE lower(concat('%',:drugType,'%'))")
    List<InventoryEntity> findByDrugType(UUID doctorId, String drugType, Pageable pageable);

    @Query("SELECT i FROM InventoryEntity i WHERE i.doctor.id = :doctorId AND i.drug.measurement = :measurement")
    List<InventoryEntity> findByDrugMeasurement(UUID doctorId, Double measurement, Pageable pageable);

    @Query("SELECT i FROM InventoryEntity i WHERE i.doctor.id = :doctorId")
    List<InventoryEntity> find(UUID doctorId, Pageable pageable);

    @Query("SELECT i FROM InventoryEntity i WHERE i.doctor.id = :doctorId AND i.drug.id = :drugId")
    Optional<InventoryEntity> findByDoctorIdAndDrugId(UUID doctorId, UUID drugId);

    @Query("SELECT i FROM InventoryEntity i WHERE i.doctor.id = :doctorId ORDER BY i.drug.name")
    List<InventoryEntity> findAllByDoctorId(UUID doctorId);
}

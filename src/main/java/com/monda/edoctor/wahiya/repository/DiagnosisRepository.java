package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.DiagnosisEntity;
import com.monda.edoctor.wahiya.model.DrugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiagnosisRepository extends JpaRepository<DiagnosisEntity, UUID> {
    // Todo: Low performance query. Find a way to tune-in the query
    @Query("SELECT d FROM DiagnosisEntity d WHERE lower(d.name) LIKE lower(concat('%',:name,'%'))")
    List<DiagnosisEntity> findByLikeName(String name);

    @Query("SELECT d FROM DiagnosisEntity d WHERE lower(d.name) = lower(:name)")
    Optional<DiagnosisEntity> findByExactName(String name);
}

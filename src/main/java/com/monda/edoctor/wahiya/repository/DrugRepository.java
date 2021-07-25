package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.DrugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DrugRepository extends JpaRepository<DrugEntity, UUID> {
    // Todo: Low performance query. Find a way to tune-in the query
    @Query("SELECT d FROM DrugEntity d WHERE lower(d.name) LIKE lower(concat('%',:name,'%'))")
    public List<DrugEntity> findDrugByName(String name);
}

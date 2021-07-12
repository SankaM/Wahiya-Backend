package com.monda.edoctor.wahiya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkHourRepository extends JpaRepository<WorkHourRepository, UUID> {
}

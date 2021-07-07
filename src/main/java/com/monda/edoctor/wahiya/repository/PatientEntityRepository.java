package com.monda.edoctor.wahiya.repository;

import com.monda.edoctor.wahiya.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA interface to query tutorial data
 *
 * @author Priyantha Weerakoon
 */
public interface PatientEntityRepository extends JpaRepository<PatientEntity, UUID> {
    // ============================================================================================================== OK
    List<PatientEntity> findByDoctorIdAndIsActive(UUID doctorId, Boolean isActive);

    // Todo: Low performance query. Find a way to tune-in the query
    @Query("SELECT p FROM PatientEntity p WHERE lower(p.firstName) LIKE lower(concat('%',:name,'%')) OR lower(p.lastName) LIKE lower(concat('%',:name,'%'))")
    List<PatientEntity> findByName(String name);

    List<PatientEntity> findByEmailContains(String email);

    List<PatientEntity> findByUserNameContains(String username);

    List<PatientEntity> findByMobilePhoneContains(String mobilePhone);

    Optional<PatientEntity> findByDoctorIdAndId(UUID doctorId, UUID patientId);

    // ======================================================================================================== PROGRESS

    // ========================================================================================================= NOT YET

}

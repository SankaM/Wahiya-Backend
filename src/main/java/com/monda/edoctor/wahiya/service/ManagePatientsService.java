package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.PatientSummary;
import com.monda.edoctor.wahiya.dto.RegisterPatientRequest;
import com.monda.edoctor.wahiya.exception.DoctorNotFoundException;
import com.monda.edoctor.wahiya.exception.NoContentException;
import com.monda.edoctor.wahiya.exception.PatientNotFoundException;
import com.monda.edoctor.wahiya.model.PatientEntity;
import com.monda.edoctor.wahiya.repository.PatientEntityRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter
public class ManagePatientsService {

    private static final Logger logger = LoggerFactory.getLogger(ManagePatientsService.class);

    @Autowired
    private DoctorEntityService doctorEntityService;

    @Autowired
    private PatientEntityRepository patientEntityRepository;

    public boolean existsById(UUID id) throws PatientNotFoundException {
        if (!patientEntityRepository.existsById(id)) {
            logger.error("Patient ID not available : {}", id);
            throw new PatientNotFoundException();
        }
        return true;
    }

    public List<PatientSummary> getPatientsSummaryOfDoctor(UUID doctorId) throws DoctorNotFoundException {
        if (doctorEntityService.existsById(doctorId)) {
            List<PatientEntity> patients = patientEntityRepository.findByDoctorIdAndIsActive(doctorId, true);
            if (!patients.isEmpty()) {
                return patients.stream().map(p -> PatientSummary.builder()
                        .age(p.getAge()).name(p.getName()).mobile(p.getMobile())
                        .patientId(p.getPatientId())
                        .imageUrl(p.getImageUrl())
                        .build()).collect(Collectors.toList());
            }
        }
        logger.debug("Active Patient not assign for Doctor ID : {}", doctorId);
        throw new NoContentException("No Active patient available");
    }

    public void registerPatient(RegisterPatientRequest registerPatientRequest, UUID doctorId) {
        PatientEntity patientEntity = patientEntityRepository.save(PatientEntity.builder().age(registerPatientRequest.getAge())
                .birthDate(registerPatientRequest.getBirthDate()).mobile(registerPatientRequest.getMobile())
                .email(registerPatientRequest.getEmail()).name(registerPatientRequest.getName())
                .healthProfile(registerPatientRequest.getHealthProfile()).isActive(true)
                .userName(registerPatientRequest.getUserName()).doctorId(doctorId).build());
        logger.debug("Patient added successfully ID: {} Name: {}", patientEntity.getPatientId(), patientEntity.getName());
    }

    public void inactivePatient(UUID doctorId, UUID patientId) throws DoctorNotFoundException, PatientNotFoundException {
        if (doctorEntityService.existsById(doctorId) && existsById(patientId)) {
            Optional<PatientEntity> patientEntityOpt = patientEntityRepository.findByDoctorIdAndPatientId(doctorId, patientId);
            if (patientEntityOpt.isPresent()) {
                PatientEntity patientEntity = patientEntityOpt.get();
                patientEntity.setIsActive(false);
                patientEntityRepository.save(patientEntity);
                logger.debug("Patient : {} inactivate successfully.", patientId);
            } else {
                logger.error("Patient: {} not  assigned to Doctor: {}", patientId, doctorId);
                throw new NoContentException("Patient not assign to doctor");
            }
        }
    }

    public PatientEntity getPatientDetails(UUID doctorId, UUID patientId) throws DoctorNotFoundException, PatientNotFoundException {
        if (doctorEntityService.existsById(doctorId) && existsById(patientId)) {
            Optional<PatientEntity> patientEntityOpt = patientEntityRepository.findByDoctorIdAndPatientId(doctorId, patientId);
            if (patientEntityOpt.isPresent()) {
                return patientEntityOpt.get();
            }
        }
        logger.error("Patient: {} not  assigned to Doctor: {}", patientId, doctorId);
        throw new NoContentException("Patient not assign to doctor");
    }
}

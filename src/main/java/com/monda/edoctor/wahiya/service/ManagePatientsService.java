package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.*;
import com.monda.edoctor.wahiya.exception.DuplicateContentException;
import com.monda.edoctor.wahiya.exception.NoContentException;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.PatientEntity;
import com.monda.edoctor.wahiya.model.PrescriptionEntity;
import com.monda.edoctor.wahiya.repository.PatientEntityRepository;
import com.monda.edoctor.wahiya.repository.specification.PatientSpecification;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Autowired
    private PrescriptionEntityService prescriptionEntityService;

    @Autowired
    private DoseEntityService doseEntityService;

    public boolean existsById(UUID id) throws NotFoundException {
        if (!patientEntityRepository.existsById(id)) {
            logger.error("Patient ID not available : {}", id);
            throw new NotFoundException("Requested patient ID not available");
        }
        return true;
    }

    public PatientEntity save(PatientEntity patientEntity) {
        try {
            return patientEntityRepository.saveAndFlush(patientEntity);
        } catch (DataIntegrityViolationException e) {
            logger.error("Duplicate Record : {}", e.getMessage());
            throw new DuplicateContentException(e.getMessage());
        }
    }

    public PatientResponse getPatientResponse(UUID id) {
        PatientEntity patientEntity = patientEntityRepository.findById(id).get();
        return PatientResponse.builder().id(patientEntity.getId().toString()).name(patientEntity.getName()).imageURL(patientEntity.getImageUrl()).build();
    }

    public List<PatientSummary> getPatientsSummaryOfDoctor(UUID doctorId) throws NotFoundException {
        if (doctorEntityService.existsById(doctorId)) {
            List<PatientEntity> patients = patientEntityRepository.findByDoctorIdAndIsActive(doctorId, true);
            if (!patients.isEmpty()) {
                return patients.stream().map(p -> PatientSummary.builder()
                        .age(p.getAge()).name(p.getName()).mobile(p.getMobile())
                        .patientId(p.getId())
                        .imageUrl(p.getImageUrl())
                        .build()).collect(Collectors.toList());
            }
        }
        logger.debug("Active Patient not assign for Doctor ID : {}", doctorId);
        throw new NoContentException("No Active patient available");
    }

    public void registerPatient(RegisterPatientRequest registerPatientRequest, UUID doctorId) {
        PatientEntity patientEntity = save(PatientEntity.builder().age(registerPatientRequest.getAge())
                .birthDate(registerPatientRequest.getBirthDate()).mobile(registerPatientRequest.getMobile())
                .email(registerPatientRequest.getEmail()).name(registerPatientRequest.getName())
                .healthProfile(registerPatientRequest.getHealthProfile()).isActive(true)
                .userName(registerPatientRequest.getUserName()).doctorId(doctorId).build());
        logger.debug("Patient added successfully ID: {} Name: {}", patientEntity.getId(), patientEntity.getName());
    }

    public void inactivePatient(UUID doctorId, UUID patientId) throws NotFoundException {
        if (doctorEntityService.existsById(doctorId) && existsById(patientId)) {
            Optional<PatientEntity> patientEntityOpt = patientEntityRepository.findByDoctorIdAndId(doctorId, patientId);
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

    public PatientEntity getPatientDetails(UUID doctorId, UUID patientId) throws NotFoundException {
        if (doctorEntityService.existsById(doctorId) && existsById(patientId)) {
            Optional<PatientEntity> patientEntityOpt = patientEntityRepository.findByDoctorIdAndId(doctorId, patientId);
            if (patientEntityOpt.isPresent()) {
                return patientEntityOpt.get();
            }
        }
        logger.error("Patient: {} not  assigned to Doctor: {}", patientId, doctorId);
        throw new NoContentException("Patient not assign to doctor");
    }

    public List<PatientEntity> searchPatient(String query) {
        List<PatientEntity> patients = patientEntityRepository.findAll(PatientSpecification.textInAllColumns(query));
        if (patients.isEmpty()) {
            logger.debug("No patient available for: {}", query);
            throw new NoContentException("No patient available for: " + query);
        }
        return patients;
    }

    public MedicalHistoryResponse getPatientMedicalHistory(UUID patientId) throws NotFoundException {
        List<PrescriptionResponse> prescriptionResponses = null;
        if (existsById(patientId)) {
            List<PrescriptionEntity> prescriptions = prescriptionEntityService.findByPatientId(patientId);
            if (prescriptions.isEmpty()) {
                logger.debug("No history available for: {}", patientId);
                throw new NoContentException("No patient available for: " + patientId);
            }
            prescriptionResponses = prescriptions.stream().map(p -> PrescriptionResponse.builder()
                    .doctor(doctorEntityService.getDoctorResponse(p.getDoctorId()))
                    .id(p.getId())
                    .issuedDate(p.getIssuedDate())
                    .doses(doseEntityService.getDoseResponses(p.getId()))
                    .build()).collect(Collectors.toList());
        }
        return MedicalHistoryResponse.builder().patient(getPatientResponse(patientId)).prescriptions(prescriptionResponses).build();
    }
}

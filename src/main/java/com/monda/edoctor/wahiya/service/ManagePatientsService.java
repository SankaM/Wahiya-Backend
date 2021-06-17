package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.*;
import com.monda.edoctor.wahiya.exception.DoctorNotFoundException;
import com.monda.edoctor.wahiya.exception.DrugNotFoundException;
import com.monda.edoctor.wahiya.exception.NoContentException;
import com.monda.edoctor.wahiya.exception.PatientNotFoundException;
import com.monda.edoctor.wahiya.model.DoseEntity;
import com.monda.edoctor.wahiya.model.PatientEntity;
import com.monda.edoctor.wahiya.model.PrescriptionEntity;
import com.monda.edoctor.wahiya.repository.DoseEntityRepository;
import com.monda.edoctor.wahiya.repository.PatientEntityRepository;
import com.monda.edoctor.wahiya.repository.PrescriptionEntityRepository;
import com.monda.edoctor.wahiya.repository.specification.PatientSpecification;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private PrescriptionEntityRepository prescriptionEntityRepository;

    @Autowired
    private DrugEntityService drugEntityService;

    @Autowired
    private DoseEntityRepository doseEntityRepository;

    public boolean existsById(UUID id) throws PatientNotFoundException {
        if (!patientEntityRepository.existsById(id)) {
            logger.error("Patient ID not available : {}", id);
            throw new PatientNotFoundException();
        }
        return true;
    }

    public PatientResponse getPatientResponse(UUID id) {
        PatientEntity patientEntity = patientEntityRepository.findById(id).get();
        return PatientResponse.builder().id(patientEntity.getPatientId().toString()).name(patientEntity.getName()).imageURL(patientEntity.getImageUrl()).build();
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

    public List<PatientEntity> searchPatient(String query) {
        List<PatientEntity> patients = patientEntityRepository.findAll(PatientSpecification.textInAllColumns(query));
        if (patients.isEmpty()) {
            logger.debug("No patient available for: {}", query);
            throw new NoContentException("No patient available for: " + query);
        }
        return patients;
    }

    public void addPrescription(UUID doctorId, UUID patientId, PrescriptionRequest prescriptionRequest) throws DoctorNotFoundException, PatientNotFoundException, DrugNotFoundException {
        if (doctorEntityService.existsById(doctorId) && existsById(patientId)) {
            PrescriptionEntity prescriptionEntity = prescriptionEntityRepository.save(PrescriptionEntity.builder()
                    .doctorId(doctorId)
                    .patientId(patientId)
                    .issuedDate(LocalDateTime.now()).build());

            for (DoseEntityRequest doseEntityRequest : prescriptionRequest.getDoses()) {
                if (drugEntityService.existsById(doseEntityRequest.getDrugId())) {
                    doseEntityRepository.save(DoseEntity.builder()
                            .prescriptionId(prescriptionEntity.getPrescriptionId())
                            .unitsPerDose(doseEntityRequest.getUnitsPerDose())
                            .dosesPerDay(doseEntityRequest.getDosesPerDay())
                            .numberOfDays(doseEntityRequest.getNumberOfDays())
                            .beforeAfterMeal(doseEntityRequest.getBeforeAfterMeal())
                            .note(doseEntityRequest.getNote())
                            .fromDate(doseEntityRequest.getFromDate())
                            .toDate(doseEntityRequest.getToDate())
                            .drugId(doseEntityRequest.getDrugId())
                            .build());
                }
            }
        }
    }

    public MedicalHistoryResponse getPatientMedicalHistory(UUID patientId) throws PatientNotFoundException {
        List<PrescriptionResponse> prescriptionResponses = null;
        if (existsById(patientId)) {
            List<PrescriptionEntity> prescriptions = prescriptionEntityRepository.findByPatientId(patientId);
            if (prescriptions.isEmpty()) {
                logger.debug("No history available for: {}", patientId);
                throw new NoContentException("No patient available for: " + patientId);
            }
            prescriptionResponses = prescriptions.stream().map(p -> PrescriptionResponse.builder()
                    .doctor(doctorEntityService.getDoctorResponse(p.getDoctorId()))
                    .id(p.getPrescriptionId())
                    .issuedDate(p.getIssuedDate())
                    .doses(getDoseResponses(p.getPrescriptionId()))
                    .build()).collect(Collectors.toList());
        }
        return MedicalHistoryResponse.builder().patient(getPatientResponse(patientId)).prescriptions(prescriptionResponses).build();
    }

    private List<DoseResponse> getDoseResponses(UUID prescriptionId) {
        List<DoseEntity> doses = doseEntityRepository.findByPrescriptionId(prescriptionId);
        return doses.stream().map(p -> DoseResponse.builder()
                .unitsPerDose(p.getUnitsPerDose())
                .beforeAfterMeal(p.getBeforeAfterMeal())
                .dosesPerDay(p.getDosesPerDay())
                .drug(drugEntityService.getDrugResponse(p.getDrugId()))
                .note(p.getNote())
                .numberOfDays(p.getNumberOfDays())
                .fromDate(p.getFromDate())
                .toDate(p.getToDate())
                .build()).collect(Collectors.toList());
    }

    public InvoiceResponse getInvoice(UUID prescriptionId) {
        PrescriptionEntity prescription = prescriptionEntityRepository.findById(prescriptionId).get();
        PrescriptionResponse prescriptionResponse = PrescriptionResponse.builder()
                .doctor(doctorEntityService.getDoctorResponse(prescription.getDoctorId()))
                .id(prescription.getPrescriptionId())
                .issuedDate(prescription.getIssuedDate())
                .doses(getDoseResponses(prescription.getPrescriptionId()))
                .build();
        return InvoiceResponse.builder().prescription(prescriptionResponse)
                .patient(getPatientResponse(prescription.getPatientId())).build();
    }
}

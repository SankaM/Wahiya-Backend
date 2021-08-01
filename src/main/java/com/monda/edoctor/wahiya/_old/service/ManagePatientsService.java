//package com.monda.edoctor.wahiya._old.service;
//
//import com.monda.edoctor.wahiya._old.dto.req.RegisterPatientRequest;
//import com.monda.edoctor.wahiya._old.dto.res.*;
//import com.monda.edoctor.wahiya.exception.DuplicateContentException;
//import com.monda.edoctor.wahiya.exception.NoContentException;
//import com.monda.edoctor.wahiya.exception.NotFoundException;
//import com.monda.edoctor.wahiya.model.DiagnosisEntity;
//import com.monda.edoctor.wahiya.model.DosageEntity;
//import com.monda.edoctor.wahiya.model.PatientEntity;
//import com.monda.edoctor.wahiya.model.PrescriptionEntity;
//import com.monda.edoctor.wahiya._old.repository.DiagnosisEntityRepository;
//import com.monda.edoctor.wahiya._old.repository.DoctorEntityRepository;
//import com.monda.edoctor.wahiya._old.repository.PatientEntityRepository;
//import lombok.extern.slf4j.Slf4j;
//import lombok.val;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class ManagePatientsService {
//
//    @Autowired
//    private DoctorEntityService doctorEntityService;
//
//    @Autowired
//    private PatientEntityRepository patientEntityRepository;
//
//    @Autowired
//    private PrescriptionEntityService prescriptionEntityService;
//
//    @Autowired
//    private DosageEntityService dosageEntityService;
//
//    @Autowired
//    private DiagnosisEntityRepository diagnosisEntityRepository;
//
//    @Autowired
//    private DoctorEntityRepository doctorEntityRepository;
//
//    public static enum SearchPatientField {
//        NAME, USERNAME, EMAIL, MOBILE_PHONE
//    }
//
//    // ============================================================================================================== OK
//    public boolean existsById(UUID id) throws NotFoundException {
//        if (!patientEntityRepository.existsById(id)) {
//            log.error("Patient ID not available : {}", id);
//            throw new NotFoundException("Requested patient ID not available");
//        }
//
//        return true;
//    }
//
//    public List<PatientResponse> getPatientsOfDoctor(UUID doctorId) throws NotFoundException {
//        if (doctorEntityService.existsById(doctorId)) {
//            List<PatientEntity> patients = patientEntityRepository.findByDoctorIdAndIsActive(doctorId, true);
//            if (!patients.isEmpty()) {
//                return patients.stream().map(p -> PatientResponse.buildPatientSummary(p, findLastPatientDiagnosisAsString(p.getId()))).collect(Collectors.toList());
//            }
//        }
//
//        return new ArrayList<>();
//    }
//
//    public List<PatientResponse> searchPatient(String query, SearchPatientField field) {
//        List<PatientEntity> patients = new ArrayList<>();
//
//        switch(field) {
//            case NAME: {
//                patients = patientEntityRepository.findByName(query);
//                break;
//            } case EMAIL: {
//                patients = patientEntityRepository.findByEmailContains(query);
//                break;
//            } case USERNAME: {
//                patients = patientEntityRepository.findByUserNameContains(query);
//                break;
//            } case MOBILE_PHONE: {
//                patients = patientEntityRepository.findByMobilePhoneContains(query);
//                break;
//            }
//        }
//
//        if (!patients.isEmpty()) {
//            return patients.stream().map(p -> PatientResponse.buildPatientSummary(p, findLastPatientDiagnosisAsString(p.getId()))).collect(Collectors.toList());
//        }
//
//        return new ArrayList<>();
//    }
//
//    public PatientResponse getPatientDetails(UUID doctorId, UUID patientId) throws NotFoundException {
//        if (doctorEntityService.existsById(doctorId) && existsById(patientId)) {
//            Optional<PatientEntity> patientEntityOpt = patientEntityRepository.findByDoctorIdAndId(doctorId, patientId);
//            if (patientEntityOpt.isPresent()) {
//                return PatientResponse.buildPatientDetail(patientEntityOpt.get(), findLastPatientDiagnosisAsString(patientId));
//            }
//        }
//
//        log.error("Patient ID: {} not  assigned to Doctor ID: {}", patientId, doctorId);
//        throw new NoContentException("Patient not assign to doctor");
//    }
//
//    public DiagnosisEntity findLastPatientDiagnosis(UUID patientId) {
//        val prescriptions = prescriptionEntityService.findByPatientId(patientId);
//
//        for(PrescriptionEntity p: prescriptions) {
//            int treatmentDays = 0;
//            val dosages = dosageEntityService.findByPrescriptionId(p.getId());
//            for(DosageEntity d: dosages) {
//                if(d.getTreatmentDays() > treatmentDays) treatmentDays = d.getTreatmentDays();
//            }
//
//            if(p.getPrescriptionDate().plusDays(treatmentDays).isAfter(LocalDateTime.now())) {
//                val diagnosisOpt = diagnosisEntityRepository.findById(p.getDiagnosisId());
//                if(diagnosisOpt.isPresent()) {
//                    return diagnosisOpt.get();
//                }
//            }
//        }
//
//        return null;
//    }
//
//    public String findLastPatientDiagnosisAsString(UUID patientId) {
//        val d = findLastPatientDiagnosis(patientId);
//        if(d != null) return d.getName();
//
//        return "No last diagnosis data";
//    }
//
//    public PatientResponse getPatientResponse(UUID id) throws NotFoundException {
//        existsById(id);
//
//        PatientEntity p = patientEntityRepository.findById(id).get();
//        return PatientResponse.buildPatientSummary(p, findLastPatientDiagnosisAsString(p.getId()));
//    }
//
//    public MedicalHistoryResponse getPatientMedicalHistory(UUID patientId) throws NotFoundException {
//        existsById(patientId);
//
//        val builder = MedicalHistoryResponse.builder().patient(getPatientResponse(patientId));
//        val prescriptions = prescriptionEntityService.findByPatientId(patientId);
//        if (!prescriptions.isEmpty()) {
//            log.debug("Found history for: {}", patientId);
//            val prescriptionResponses = prescriptions.stream()
//                    .map(p -> {
//                        val doctorResponse = DoctorResponse.buildDoctorSummary(doctorEntityRepository.findById(p.getDoctorId()).get());
//                        val diagnosisResponse = DiagnosisResponse.build(diagnosisEntityRepository.findById(p.getDiagnosisId()).get());
//                        val dosageResponseList = dosageEntityService.findDosageResponseByPrescriptionId(p.getId());
//
//                        return PrescriptionResponse.build(p, doctorResponse, diagnosisResponse, dosageResponseList);
//                    }).collect(Collectors.toList());
//
//            builder.prescriptions(prescriptionResponses);
//        }
//
//        return builder.build();
//    }
//
//    // ======================================================================================================== PROGRESS
//
//    // ========================================================================================================= NOT YET
//    public PatientEntity save(PatientEntity patientEntity) {
//        try {
//            return patientEntityRepository.saveAndFlush(patientEntity);
//        } catch (DataIntegrityViolationException e) {
//            log.error("Duplicate Record : {}", e.getMessage());
//            throw new DuplicateContentException(e.getMessage());
//        }
//    }
//
//    public void registerPatient(RegisterPatientRequest registerPatientRequest, UUID doctorId) {
//        PatientEntity patientEntity = save(PatientEntity.builder()
////                .age(registerPatientRequest.getAge())
//                .birthDate(registerPatientRequest.getBirthDate())
//                .mobilePhone(registerPatientRequest.getMobile())
//                .email(registerPatientRequest.getEmail()).firstName(registerPatientRequest.getName())
//                .healthProfile(registerPatientRequest.getHealthProfile()).isActive(true)
//                .userName(registerPatientRequest.getUserName()).doctorId(doctorId).build());
//        log.debug("Patient added successfully ID: {} Name: {}", patientEntity.getId(), patientEntity.getFirstName());
//    }
//
//    public void inactivePatient(UUID doctorId, UUID patientId) throws NotFoundException {
//        if (doctorEntityService.existsById(doctorId) && existsById(patientId)) {
//            Optional<PatientEntity> patientEntityOpt = patientEntityRepository.findByDoctorIdAndId(doctorId, patientId);
//            if (patientEntityOpt.isPresent()) {
//                PatientEntity patientEntity = patientEntityOpt.get();
//                patientEntity.setIsActive(false);
//                patientEntityRepository.save(patientEntity);
//                log.debug("Patient : {} inactivate successfully.", patientId);
//            } else {
//                log.error("Patient: {} not  assigned to Doctor: {}", patientId, doctorId);
//                throw new NoContentException("Patient not assign to doctor");
//            }
//        }
//    }
//}

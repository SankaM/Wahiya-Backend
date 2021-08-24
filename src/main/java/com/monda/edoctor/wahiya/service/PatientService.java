package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.req.PatientRegistrationReq;
import com.monda.edoctor.wahiya.dto.req.UpdatePatientHealthProfileReq;
import com.monda.edoctor.wahiya.dto.res.PatientRes;
import com.monda.edoctor.wahiya.exception.DuplicateContentException;
import com.monda.edoctor.wahiya.exception.NoContentException;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.DiagnosisEntity;
import com.monda.edoctor.wahiya.model.PatientEntity;
import com.monda.edoctor.wahiya.model.PrescriptionEntity;
import com.monda.edoctor.wahiya.repository.DoctorRepository;
import com.monda.edoctor.wahiya.repository.PatientRepository;
import com.monda.edoctor.wahiya.repository.PrescriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    public static enum SearchPatientField {
        NAME, USERNAME, EMAIL, MOBILE_PHONE
    }

    public boolean existsById(UUID id) throws NotFoundException {
        if (!patientRepository.existsById(id)) {
            log.error("Patient ID not available : {}", id);
            throw new NotFoundException("Patient ID not available");
        }

        return true;
    }

    public List<PatientRes> getPatientsOfDoctor(UUID doctorId) throws NotFoundException {
        doctorService.existsById(doctorId);

        List<PatientEntity> patients = patientRepository.findByDoctorIdAndIsActive(doctorId, true);
        if (!patients.isEmpty()) {
            return patients.stream().map(p -> PatientRes.buildSimple(p, findLastPatientDiagnosis(p.getId()))).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public List<PatientRes> searchPatient(String query, SearchPatientField field) {
        List<PatientEntity> patients = new ArrayList<>();

        switch (field) {
            case NAME: {
                patients = patientRepository.findByName(query);
                break;
            }
            case EMAIL: {
                patients = patientRepository.findByEmailContains(query);
                break;
            }
            case USERNAME: {
                patients = patientRepository.findByUserNameContains(query);
                break;
            }
            case MOBILE_PHONE: {
                patients = patientRepository.findByMobilePhoneContains(query);
                break;
            }
        }

        if (!patients.isEmpty()) {
            return patients.stream().map(p -> PatientRes.buildSimple(p, findLastPatientDiagnosis(p.getId()))).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public PatientRes getPatientDetails(UUID doctorId, UUID patientId) throws NotFoundException {
        doctorService.existsById(doctorId);
        existsById(patientId);

        Optional<PatientEntity> patientEntityOpt = patientRepository.findByDoctorIdAndId(doctorId, patientId);
        if (patientEntityOpt.isPresent()) {
            return PatientRes.buildDetail(patientEntityOpt.get(), findLastPatientDiagnosis(patientId));
        }

        log.error("Patient ID: {} not  assigned to Doctor ID: {}", patientId, doctorId);
        throw new NoContentException("Patient not assign to doctor");
    }

    public void registerPatient(UUID doctorId, PatientRegistrationReq req) throws NotFoundException, DuplicateContentException {
        doctorService.existsById(doctorId);

        if(req.getMobilePhone() != null && patientRepository.findByMobilePhone(req.getMobilePhone()) != null) {
            throw new DuplicateContentException("Patient with mobile phone " + req.getMobilePhone() + " already exist");
        }

        if(req.getEmail() != null && patientRepository.findByEmail(req.getEmail()) != null) {
            throw new DuplicateContentException("Patient with email " + req.getEmail() + " already exist");
        }

        if(req.getUserName() != null && patientRepository.findByUserName(req.getUserName()) != null) {
            throw new DuplicateContentException("Patient with username " + req.getUserName() + " already exist");
        }

        PatientEntity patient = req.buildEntity();
        patient.setIsActive(true);
        patient.setDoctor(doctorRepository.getOne(doctorId));

        patientRepository.save(patient);
    }

    public void updateHealthProfileOfPatient(UpdatePatientHealthProfileReq req) throws NotFoundException {
        existsById(req.getPatientId());

        PatientEntity patientEntity = patientRepository.getOne(req.getPatientId());
        patientEntity.setHealthProfile(req.getHealthProfile());

        patientRepository.save(patientEntity);
    }

    protected DiagnosisEntity findLastPatientDiagnosis(UUID patientId) {
        LocalDateTime now = LocalDate.now().atStartOfDay();
        List<PrescriptionEntity> prescriptions = prescriptionRepository.findNotExpiredPrescription(patientId, now);

        if(prescriptions != null && prescriptions.size() > 0) {
            return prescriptions.get(0).getDiagnosis();
        }

        return null;
    }
}

package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.res.PatientRes;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.DiagnosisEntity;
import com.monda.edoctor.wahiya.model.DosageEntity;
import com.monda.edoctor.wahiya.model.PatientEntity;
import com.monda.edoctor.wahiya.model.PrescriptionEntity;
import com.monda.edoctor.wahiya.repository.PatientRepository;
import com.monda.edoctor.wahiya.repository.PrescriptionRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
            return patients.stream().map(p -> PatientRes.buildSummary(p, findLastPatientDiagnosis(p.getId()))).collect(Collectors.toList());
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
            return patients.stream().map(p -> PatientRes.buildSummary(p, findLastPatientDiagnosis(p.getId()))).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    protected DiagnosisEntity findLastPatientDiagnosis(UUID patientId) {
        val prescriptions = prescriptionRepository.findByPatientIdOrderByPrescriptionDateAsc(patientId);

        for (PrescriptionEntity p : prescriptions) {
            int treatmentDays = 0;

            for (DosageEntity d : p.getDosageList()) {
                if (d.getTreatmentDays() > treatmentDays) treatmentDays = d.getTreatmentDays();
            }

            if (p.getPrescriptionDate().plusDays(treatmentDays).isAfter(LocalDateTime.now())) {
                return p.getDiagnosis();
            }
        }

        return null;
    }
}

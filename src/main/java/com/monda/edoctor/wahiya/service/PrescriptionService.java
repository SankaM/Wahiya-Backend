package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.res.PrescriptionRes;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.PrescriptionEntity;
import com.monda.edoctor.wahiya.repository.PrescriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PrescriptionService {
    @Autowired
    private PatientService patientService;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<PrescriptionRes> retrievePrescriptions(UUID patientId) throws NotFoundException {
        patientService.existsById(patientId);

        List<PrescriptionEntity> prescriptionEntityList = prescriptionRepository.findByPatientIdOrderByPrescriptionDateAsc(patientId);

        return prescriptionEntityList
                .stream()
                .map(prescription -> PrescriptionRes.buildSimple(prescription))
                .collect(Collectors.toList());
    }
}

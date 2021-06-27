package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.DoseEntityRequest;
import com.monda.edoctor.wahiya.dto.InvoiceResponse;
import com.monda.edoctor.wahiya.dto.PrescriptionRequest;
import com.monda.edoctor.wahiya.dto.PrescriptionResponse;
import com.monda.edoctor.wahiya.exception.DuplicateContentException;
import com.monda.edoctor.wahiya.exception.NotFoundException;
import com.monda.edoctor.wahiya.model.DoseEntity;
import com.monda.edoctor.wahiya.model.PrescriptionEntity;
import com.monda.edoctor.wahiya.repository.PrescriptionEntityRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Setter
public class PrescriptionEntityService {

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionEntityService.class);

    @Autowired
    private PrescriptionEntityRepository prescriptionEntityRepository;

    @Autowired
    private DoctorEntityService doctorEntityService;

    @Autowired
    private ManagePatientsService managePatientsService;

    @Autowired
    private DrugEntityService drugEntityService;

    @Autowired
    private DoseEntityService doseEntityService;

    public boolean existsById(UUID id) throws NotFoundException {
        if (!prescriptionEntityRepository.existsById(id)) {
            logger.error("Prescription ID not available : {}", id);
            throw new NotFoundException("Requested patient ID not available");
        }
        return true;
    }

    public PrescriptionEntity save(PrescriptionEntity prescriptionEntity) {
        try {
            if (existsById(prescriptionEntity.getId())) {
                logger.error("Duplicate Record : {}", prescriptionEntity.getId());
                throw new DuplicateContentException("Prescription already available");
            }
        } catch (NotFoundException e) {
        }
        return prescriptionEntityRepository.saveAndFlush(prescriptionEntity);
    }

    public void addPrescription(UUID doctorId, UUID patientId, PrescriptionRequest prescriptionRequest) throws NotFoundException {
        if (doctorEntityService.existsById(doctorId) && managePatientsService.existsById(patientId)) {
            PrescriptionEntity prescriptionEntity = save(PrescriptionEntity.builder()
                    .id(prescriptionRequest.getId())
                    .doctorId(doctorId)
                    .patientId(patientId)
                    .issuedDate(LocalDateTime.now()).build());

            for (DoseEntityRequest doseEntityRequest : prescriptionRequest.getDoses()) {
                if (drugEntityService.existsById(doseEntityRequest.getDrugId())) {
                    doseEntityService.save(DoseEntity.builder()
                            .prescriptionId(prescriptionEntity.getId())
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

    public InvoiceResponse getInvoice(UUID prescriptionId) throws NotFoundException {
        PrescriptionEntity prescription = null;
        PrescriptionResponse prescriptionResponse = null;
        if (existsById(prescriptionId)) {
            prescription = prescriptionEntityRepository.findById(prescriptionId).get();
            prescriptionResponse = PrescriptionResponse.builder()
                    .doctor(doctorEntityService.getDoctorResponse(prescription.getDoctorId()))
                    .id(prescription.getId())
                    .issuedDate(prescription.getIssuedDate())
                    .doses(doseEntityService.getDoseResponses(prescription.getId()))
                    .build();

        }
        return InvoiceResponse.builder().prescription(prescriptionResponse)
                .patient(managePatientsService.getPatientResponse(prescription.getPatientId())).build();
    }

    public List<PrescriptionEntity> findByPatientId(UUID patientId){
        return prescriptionEntityRepository.findByPatientId(patientId);
    }

}

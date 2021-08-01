//package com.monda.edoctor.wahiya._old.service;
//
//import com.monda.edoctor.wahiya._old.dto.req.DoseEntityRequest;
//import com.monda.edoctor.wahiya._old.dto.req.PrescriptionRequest;
//import com.monda.edoctor.wahiya._old.dto.res.InvoiceResponse;
//import com.monda.edoctor.wahiya._old.dto.res.PrescriptionResponse;
//import com.monda.edoctor.wahiya.exception.DuplicateContentException;
//import com.monda.edoctor.wahiya.exception.NotFoundException;
//import com.monda.edoctor.wahiya.model.DosageEntity;
//import com.monda.edoctor.wahiya.model.PrescriptionEntity;
//import com.monda.edoctor.wahiya._old.repository.PrescriptionEntityRepository;
//import lombok.extern.slf4j.Slf4j;
//import lombok.val;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@Slf4j
//public class PrescriptionEntityService {
//
//    @Autowired
//    private PrescriptionEntityRepository prescriptionEntityRepository;
//
//    @Autowired
//    private DoctorEntityService doctorEntityService;
//
//    @Autowired
//    private ManagePatientsService managePatientsService;
//
//    @Autowired
//    private DrugEntityService drugEntityService;
//
//    @Autowired
//    private DosageEntityService dosageEntityService;
//
//    // ============================================================================================================== OK
//    public List<PrescriptionEntity> findByPatientId(UUID patientId){
//        return prescriptionEntityRepository.findByPatientIdOrderByPrescriptionDateDesc(patientId);
//    }
//
//    // ======================================================================================================== PROGRESS
//
//    // ========================================================================================================= NOT YET
//    public boolean existsById(UUID id) throws NotFoundException {
//        if (!prescriptionEntityRepository.existsById(id)) {
//            log.error("Prescription ID not available : {}", id);
//            throw new NotFoundException("Requested patient ID not available");
//        }
//        return true;
//    }
//
//    public PrescriptionEntity save(PrescriptionEntity prescriptionEntity) {
//        try {
//            if (existsById(prescriptionEntity.getId())) {
//                log.error("Duplicate Record : {}", prescriptionEntity.getId());
//                throw new DuplicateContentException("Prescription already available");
//            }
//        } catch (NotFoundException e) {
//        }
//        return prescriptionEntityRepository.saveAndFlush(prescriptionEntity);
//    }
//
//    public void addPrescription(UUID doctorId, UUID patientId, PrescriptionRequest prescriptionRequest) throws NotFoundException {
//        if (doctorEntityService.existsById(doctorId) && managePatientsService.existsById(patientId)) {
//            PrescriptionEntity prescriptionEntity = save(PrescriptionEntity.builder()
//                    .id(prescriptionRequest.getId())
//                    .doctorId(doctorId)
//                    .patientId(patientId)
//                    .prescriptionDate(LocalDateTime.now())
//                    .build());
//
//            for (DoseEntityRequest doseEntityRequest : prescriptionRequest.getDoses()) {
//                if (drugEntityService.existsById(doseEntityRequest.getDrugId())) {
//                    dosageEntityService.save(DosageEntity.builder()
////                            .prescriptionId(prescriptionEntity.getId())
////                            .unitsPerDose(doseEntityRequest.getUnitsPerDose())
////                            .dosesPerDay(doseEntityRequest.getDosesPerDay())
////                            .numberOfDays(doseEntityRequest.getNumberOfDays())
////                            .beforeAfterMeal(doseEntityRequest.getBeforeAfterMeal())
////                            .notes(doseEntityRequest.getNote())
////                            .fromDate(doseEntityRequest.getFromDate())
////                            .toDate(doseEntityRequest.getToDate())
////                            .drugId(doseEntityRequest.getDrugId())
//                            .build());
//                }
//            }
//        }
//    }
//
//    public InvoiceResponse getInvoice(UUID prescriptionId) throws NotFoundException {
//        PrescriptionEntity prescription = null;
//        PrescriptionResponse prescriptionResponse = null;
//        if (existsById(prescriptionId)) {
//            prescription = prescriptionEntityRepository.findById(prescriptionId).get();
//            prescriptionResponse = PrescriptionResponse.builder()
//                    .doctor(doctorEntityService.getDoctorResponse(prescription.getDoctorId()))
//                    .id(prescription.getId())
////                    .issuedDate(prescription.getIssuedDate())
////                    .dosage(dosageEntityService.getDoseResponses(prescription.getId()))
//                    .build();
//
//        }
//        return InvoiceResponse.builder().prescription(prescriptionResponse)
//                .patient(managePatientsService.getPatientResponse(prescription.getPatientId())).build();
//    }
//}

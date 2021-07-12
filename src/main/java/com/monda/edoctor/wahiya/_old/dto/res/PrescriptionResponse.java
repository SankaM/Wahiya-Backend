//package com.monda.edoctor.wahiya._old.dto.res;
//
//import com.monda.edoctor.wahiya.model.PrescriptionEntity;
//import lombok.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@Setter
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class PrescriptionResponse {
//    private UUID id;
//
//    private DoctorResponse doctor;
//
//    private DiagnosisResponse diagnosis;
//
//    private LocalDateTime prescriptionDate;
//
//    private String notes;
//
//    private List<DosageResponse> dosages;
//
//    public static PrescriptionResponse build(PrescriptionEntity prescription, DoctorResponse doctorResponse, DiagnosisResponse diagnosisResponse, List<DosageResponse> dosageResponseList) {
//        val res = new PrescriptionResponse();
//
//        if(prescription != null) {
//            res.id = prescription.getId();
//            res.doctor = doctorResponse;
//            res.diagnosis = diagnosisResponse;
//            res.prescriptionDate = prescription.getPrescriptionDate();
//            res.notes = prescription.getNotes();
//            res.dosages = dosageResponseList;
//        }
//
//        return res;
//    }
//}

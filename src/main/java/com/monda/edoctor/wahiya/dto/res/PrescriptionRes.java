package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.Config;
import com.monda.edoctor.wahiya.model.PrescriptionEntity;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionRes {
    private UUID id;

    private DoctorRes doctor;

    private PatientRes patient;

    private DiagnosisRes diagnosis;

    private PrescriptionEntity.IllnessSeverity illnessSeverity;

    private String prescriptionDate;

    private String notes;

    private String attachmentUrl;

    private Double doctorCost;

    private Double drugCost;

    private Double totalCost;

    private List<DosageRes> dosageList;

    public static PrescriptionRes buildDetail(PrescriptionEntity prescription) {
        PrescriptionRes res = null;

        if(prescription != null) {
            val dosageList = prescription.getDosageList() != null ?
                    prescription.getDosageList().stream().map(dosage -> DosageRes.buildDetail(dosage)).collect(Collectors.toList())
                    : null;

            res = new PrescriptionRes();
            res.id = prescription.getId();
            res.doctor = DoctorRes.buildSimple(prescription.getDoctor());
            res.diagnosis = DiagnosisRes.buildDetail(prescription.getDiagnosis());
            res.illnessSeverity = prescription.getIllnessSeverity();
            res.prescriptionDate = prescription.getPrescriptionDate() != null ? prescription.getPrescriptionDate().toString() : null;
            res.notes = prescription.getNotes();
            res.attachmentUrl = prescription.getAttachmentUrl();
            res.doctorCost = prescription.getDoctorCost();
            res.drugCost = prescription.getDrugCost();
            res.totalCost = prescription.getTotalCost();
            res.dosageList = dosageList;
            res.attachmentUrl = prescription.getAttachmentUrl();
        }

        return res;
    }
}

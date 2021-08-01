//package com.monda.edoctor.wahiya._old.dto.res;
//
//import com.monda.edoctor.wahiya.model.DosageEntity;
//import lombok.*;
//
//import java.util.UUID;
//
//@Setter
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class DosageResponse {
//
//    private UUID id;
//
//    private DrugResponse drug;
//
//    private Integer treatmentDays;
//
//    private Integer timesPerDay;
//
//    private String dosageRule;
//
//    private Double dosageCount;
//
//    public static DosageResponse build(DosageEntity d, DrugResponse drugResponse) {
//        val res = new DosageResponse();
//
//        if(d != null) {
//            res.id = d.getId();
//            res.drug = drugResponse;
//            res.treatmentDays = d.getTreatmentDays();
//            res.timesPerDay = d.getTimesPerDay();
//            res.dosageRule = d.getDosageRule() != null ? d.getDosageRule().toString() : null;
//            res.dosageCount = d.getDosageCount();
//        }
//
//        return res;
//    }
//}

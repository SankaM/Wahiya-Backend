//package com.monda.edoctor.wahiya._old.dto.res;
//
//import com.monda.edoctor.wahiya.model.DrugEntity;
//import lombok.*;
//
//import java.util.UUID;
//
//@Setter
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class DrugResponse {
//    private UUID id;
//
//    private String name;
//
//    private String description;
//
//    private String type;
//
//    private Double measurement;
//
//    private String measurementUnit;
//
//    private String imageUrl;
//
//    public static DrugResponse buildDrug(DrugEntity d) {
//        DrugResponse res = new DrugResponse();
//
//        if(d != null) {
//            res.id = d.getId();
//            res.name = d.getName();
//            res.type = d.getType() != null ? d.getType().toString() : null;
//            res.description = d.getDescription();
//            res.measurement = d.getMeasurement();
//            res.measurementUnit = d.getMeasurementUnit() != null ? d.getMeasurementUnit().toString() : null;
//            res.imageUrl = d.getImageUrl();
//        }
//
//        return res;
//    }
//}

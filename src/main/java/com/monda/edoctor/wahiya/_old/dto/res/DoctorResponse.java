//package com.monda.edoctor.wahiya._old.dto.res;
//
//
//import com.monda.edoctor.wahiya.model.DoctorEntity;
//import lombok.*;
//
//import java.util.UUID;
//
//@Setter
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class DoctorResponse {
//
//    private UUID id;
//
//    private String name;
//
//    private String profile;
//
//    private String imageURL;
//
//    public static DoctorResponse buildDoctorSummary(DoctorEntity d) {
//        DoctorResponse res = new DoctorResponse();
//
//        if(d != null) {
//            res.id = d.getId();
//            res.name = d.getName();
//            res.profile = d.getProfile();
//            res.imageURL = d.getImageUrl();
//        }
//
//        return res;
//    }
//}

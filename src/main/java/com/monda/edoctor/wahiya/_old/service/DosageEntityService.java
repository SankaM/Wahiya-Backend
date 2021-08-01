//package com.monda.edoctor.wahiya._old.service;
//
//import com.monda.edoctor.wahiya._old.dto.res.DosageResponse;
//import com.monda.edoctor.wahiya._old.dto.res.DrugResponse;
//import com.monda.edoctor.wahiya.model.DosageEntity;
//import com.monda.edoctor.wahiya._old.repository.DosageEntityRepository;
//import com.monda.edoctor.wahiya._old.repository.DrugEntityRepository;
//import lombok.extern.slf4j.Slf4j;
//import lombok.val;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class DosageEntityService {
//
//    @Autowired
//    private DosageEntityRepository dosageEntityRepository;
//
//    @Autowired
//    private DrugEntityService drugEntityService;
//
//    @Autowired
//    private DrugEntityRepository drugEntityRepository;
//
//    // ============================================================================================================== OK
//    List<DosageEntity> findByPrescriptionId(UUID prescriptionId) {
//        return dosageEntityRepository.findByPrescriptionId(prescriptionId);
//    }
//
//    // ======================================================================================================== PROGRESS
//    List<DosageResponse> findDosageResponseByPrescriptionId(UUID prescriptionId) {
//        val dosageList = findByPrescriptionId(prescriptionId);
//
////        return dosageList.stream().map(d -> {
////            val drugResponse = DrugResponse.buildDrug(drugEntityRepository.getOne(d.getDrugId()));
////
////            return DosageResponse.build(d, drugResponse);
////        }).collect(Collectors.toList());
//        return new ArrayList<>();
//    }
//
//    // ========================================================================================================= NOT YET
//    public DosageEntity save(DosageEntity doseEntity){
//        return dosageEntityRepository.saveAndFlush(doseEntity);
//    }
//
//    public List<DosageResponse> getDoseResponses(UUID prescriptionId) {
//        List<DosageEntity> doses = dosageEntityRepository.findByPrescriptionId(prescriptionId);
//        return doses.stream().map(p -> DosageResponse.builder()
////                .unitsPerDose(p.getUnitsPerDose())
////                .beforeAfterMeal(p.getBeforeAfterMeal())
////                .dosesPerDay(p.getDosesPerDay())
////                .drug(drugEntityService.getDrugResponse(p.getDrugId()))
////                .note(p.getNote())
////                .numberOfDays(p.getNumberOfDays())
////                .fromDate(p.getFromDate())
////                .toDate(p.getToDate())
//                .build()).collect(Collectors.toList());
//    }
//}

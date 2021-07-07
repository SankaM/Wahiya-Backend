package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.DoseResponse;
import com.monda.edoctor.wahiya.model.DosageEntity;
import com.monda.edoctor.wahiya.repository.DosageEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DosageEntityService {

    @Autowired
    private DosageEntityRepository dosageEntityRepository;

    @Autowired
    private DrugEntityService drugEntityService;

    // ============================================================================================================== OK
    List<DosageEntity> findByPrescriptionId(UUID prescriptionId) {
        return dosageEntityRepository.findByPrescriptionId(prescriptionId);
    }

    // ======================================================================================================== PROGRESS

    // ========================================================================================================= NOT YET
    public DosageEntity save(DosageEntity doseEntity){
        return dosageEntityRepository.saveAndFlush(doseEntity);
    }

    public List<DoseResponse> getDoseResponses(UUID prescriptionId) {
        List<DosageEntity> doses = dosageEntityRepository.findByPrescriptionId(prescriptionId);
        return doses.stream().map(p -> DoseResponse.builder()
//                .unitsPerDose(p.getUnitsPerDose())
//                .beforeAfterMeal(p.getBeforeAfterMeal())
//                .dosesPerDay(p.getDosesPerDay())
                .drug(drugEntityService.getDrugResponse(p.getDrugId()))
//                .note(p.getNote())
//                .numberOfDays(p.getNumberOfDays())
//                .fromDate(p.getFromDate())
//                .toDate(p.getToDate())
                .build()).collect(Collectors.toList());
    }
}

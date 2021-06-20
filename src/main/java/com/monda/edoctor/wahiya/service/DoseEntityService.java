package com.monda.edoctor.wahiya.service;

import com.monda.edoctor.wahiya.dto.DoseResponse;
import com.monda.edoctor.wahiya.model.DoseEntity;
import com.monda.edoctor.wahiya.repository.DoseEntityRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter
public class DoseEntityService {

    private static final Logger logger = LoggerFactory.getLogger(DoseEntityService.class);

    @Autowired
    private DoseEntityRepository doseEntityRepository;

    @Autowired
    private DrugEntityService drugEntityService;

    public DoseEntity save(DoseEntity doseEntity){
        return doseEntityRepository.saveAndFlush(doseEntity);
    }

    public List<DoseResponse> getDoseResponses(UUID prescriptionId) {
        List<DoseEntity> doses = doseEntityRepository.findByPrescriptionId(prescriptionId);
        return doses.stream().map(p -> DoseResponse.builder()
                .unitsPerDose(p.getUnitsPerDose())
                .beforeAfterMeal(p.getBeforeAfterMeal())
                .dosesPerDay(p.getDosesPerDay())
                .drug(drugEntityService.getDrugResponse(p.getDrugId()))
                .note(p.getNote())
                .numberOfDays(p.getNumberOfDays())
                .fromDate(p.getFromDate())
                .toDate(p.getToDate())
                .build()).collect(Collectors.toList());
    }
}

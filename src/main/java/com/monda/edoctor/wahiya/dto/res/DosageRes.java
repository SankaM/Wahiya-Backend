package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.model.DosageEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DosageRes {
    private UUID id;

    private DrugRes drug;

    private Integer treatmentDays;

    private Integer timesPerDay;

    private DosageEntity.DosageRule dosageRule;

    private Double dosageCount;

    private Double drugCost;

    public static DosageRes buildDetail(DosageEntity dosage) {
        DosageRes res = null;

        if(dosage != null) {
            res = new DosageRes();
            res.id = dosage.getId();
            res.drug = DrugRes.buildDetail(dosage.getDrug());
            res.treatmentDays = dosage.getTreatmentDays();
            res.timesPerDay = dosage.getTimesPerDay();
            res.dosageRule = dosage.getDosageRule();
            res.dosageCount = dosage.getDosageCount();
            res.drugCost = dosage.getDrugCost();
        }

        return res;
    }
}

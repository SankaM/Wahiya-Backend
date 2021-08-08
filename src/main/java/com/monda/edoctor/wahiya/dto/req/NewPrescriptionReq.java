package com.monda.edoctor.wahiya.dto.req;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewPrescriptionReq {
    @Data
    public static class TreatmentItem {
        private UUID inventoryId;

        private Integer treatmentDays;

        private Integer timesPerDay;

        private String dosageRule;

        private Integer dosageCount;
    }

    private UUID diagnosisId;

    private String illnessSeverity;

    private List<TreatmentItem> treatmentItemList;

    private String notes;
}

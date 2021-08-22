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

/*
{
    "diagnosisId": "f3560a74-bfa8-0002-8529-0242ac130003",
    "illnessSeverity": "HIGH",
    "notes": "bla bla bla",
    "treatmentItemList": [
        {
            "inventoryId": "f3560a74-bfa8-0000-8529-0242ac1300aa",
            "treatmentDays": 1,
            "timesPerDay": 2,
            "dosageCount": 3,
            "dosageRule": "BEFORE_MEAL"
        }
    ]
}

attachmentId = 1e18d6ed-4c43-457d-b6d3-d877d6e90e03

1e18d6ed-4c43-457d-b6d3-d877d6e90e03	null	null	Appointment_Entity.png	null/null/Appointment_Entity.png
 */
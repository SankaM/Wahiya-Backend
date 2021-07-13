package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.model.DiagnosisEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiagnosisRes {
    private UUID id;

    private String name;

    public static DiagnosisRes buildDetail(DiagnosisEntity diagnosis) {
        DiagnosisRes res = null;

        if(diagnosis != null) {
            res = new DiagnosisRes();
            res.id = diagnosis.getId();
            res.name = diagnosis.getName();
        }

        return res;
    }
}

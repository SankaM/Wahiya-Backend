package com.monda.edoctor.wahiya.dto.res;

import com.monda.edoctor.wahiya.model.DiagnosisEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisResponse {
    private UUID id;

    private String name;

    public static DiagnosisResponse build(DiagnosisEntity d) {
        val res = new DiagnosisResponse();

        if(d != null) {
            res.id = d.getId();
            res.name = d.getName();
        }

        return res;
    }
}

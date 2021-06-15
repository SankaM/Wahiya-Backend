package com.monda.edoctor.wahiya.dto;

import com.monda.edoctor.wahiya.model.DrugEntity;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDrugRequest {
    private Double unit;
    private DrugEntity.UpdateType updateType;
}

package com.monda.edoctor.wahiya.dto.req;

import com.monda.edoctor.wahiya.model.DrugEntity;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDrugRequest {
    private Double unit;
}

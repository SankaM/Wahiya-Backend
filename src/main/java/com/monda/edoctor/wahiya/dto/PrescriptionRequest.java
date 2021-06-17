package com.monda.edoctor.wahiya.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionRequest {
    private List<DoseEntityRequest> doses;
}

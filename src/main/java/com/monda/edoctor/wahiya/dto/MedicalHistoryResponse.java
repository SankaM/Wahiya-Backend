package com.monda.edoctor.wahiya.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryResponse {
    private PatientResponse patient;
    private List<PrescriptionResponse> prescriptions;
}

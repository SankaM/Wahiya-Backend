package com.monda.edoctor.wahiya.dto.res;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    private PatientResponse patient;
    private PrescriptionResponse prescription;
}

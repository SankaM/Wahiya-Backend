package com.monda.edoctor.wahiya.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {
    private UUID id;
    private DoctorResponse doctor;
    private LocalDateTime issuedDate;
    private List<DoseResponse> doses;

}

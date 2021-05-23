package com.monda.edoctor.wahiya.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientSummary {
    private String name;
    private int age;
    private String mobile;
    private UUID patientId;
    private String imageUrl;
}

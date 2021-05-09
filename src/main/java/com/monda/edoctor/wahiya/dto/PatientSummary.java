package com.monda.edoctor.wahiya.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientSummary {
    private String name;
    private int age;
}

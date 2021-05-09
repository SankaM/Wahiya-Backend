package com.monda.edoctor.wahiya.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPatientRequest {
    private String name;
    private int age;
}

package com.monda.edoctor.wahiya.dto.req;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPatientRequest {
    private String name;
    private int age;
    private LocalDate birthDate;
    private String userName;
    private String email;
    private String healthProfile;
    private String mobile;
}

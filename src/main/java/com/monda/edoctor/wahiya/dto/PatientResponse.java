package com.monda.edoctor.wahiya.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {
    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String mobilePhone;

    private String imageUrl;

    private String gender;

    private String healthProfile;
}

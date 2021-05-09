package com.monda.edoctor.wahiya.dto;

import com.monda.edoctor.wahiya.model.PatientEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

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

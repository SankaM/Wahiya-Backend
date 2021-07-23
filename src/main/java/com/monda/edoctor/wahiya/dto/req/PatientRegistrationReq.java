package com.monda.edoctor.wahiya.dto.req;

import com.monda.edoctor.wahiya.model.Gender;
import com.monda.edoctor.wahiya.model.PatientEntity;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegistrationReq {
    private String firstName;

    private String lastName;

    private String birthDate;

    private String gender;

    private String mobilePhone;

    private String nic;

    private String userName;

    private String email;

    public PatientEntity buildEntity() {
        PatientEntity entity = PatientEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(LocalDate.parse(birthDate))
                .gender(Gender.valueOf(gender))
                .mobilePhone(mobilePhone)
                .nic(nic)
                .userName(userName)
                .email(email)
                .build();

        return entity;
    }
}

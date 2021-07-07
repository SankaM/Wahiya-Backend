package com.monda.edoctor.wahiya.dto;

import com.monda.edoctor.wahiya.model.PatientEntity;
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

    private String imageUrl;

    private String gender;

    private String currentDiagnosis;

    public static PatientResponse build(PatientEntity p, String currentDiagnosis) {
        val res = new PatientResponse();
        res.id = p.getId();
        res.firstName = p.getFirstName();
        res.lastName = p.getLastName();
        res.birthDate = p.getBirthDate();
        res.imageUrl = p.getImageUrl();
        res.gender = p.getGender() != null ? p.getGender().toString() : null;
        res.currentDiagnosis = currentDiagnosis;

        return res;
    }
}

package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.model.PatientEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientResponse {
    private UUID id;

    private UUID doctorId;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String gender;

    private String mobilePhone;

    private String healthProfile;

    private String nic;

    private String imageUrl;

    private String userName;

    private Boolean isActive = true;

    private String email;

    private String currentDiagnosis;

    public static PatientResponse buildPatientSummary(PatientEntity p, String currentDiagnosis) {
        val res = new PatientResponse();

        if(p != null) {
            res.id = p.getId();
            res.firstName = p.getFirstName();
            res.lastName = p.getLastName();
            res.birthDate = p.getBirthDate();
            res.imageUrl = p.getImageUrl();
            res.gender = p.getGender() != null ? p.getGender().toString() : null;
            res.currentDiagnosis = currentDiagnosis;
        }

        return res;
    }

    public static PatientResponse buildPatientDetail(PatientEntity p, String currentDiagnosis) {
        val res = new PatientResponse();
        res.id = p.getId();
        res.doctorId = p.getDoctorId();
        res.firstName = p.getFirstName();
        res.lastName = p.getLastName();
        res.birthDate = p.getBirthDate();
        res.mobilePhone = p.getMobilePhone();
        res.healthProfile = p.getHealthProfile();
        res.nic = p.getNic();
        res.userName = p.getUserName();
        res.isActive = p.getIsActive();
        res.email = p.getEmail();
        res.imageUrl = p.getImageUrl();
        res.gender = p.getGender() != null ? p.getGender().toString() : null;
        res.currentDiagnosis = currentDiagnosis;

        return res;
    }
}

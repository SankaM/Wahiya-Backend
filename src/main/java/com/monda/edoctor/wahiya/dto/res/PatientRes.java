package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.model.DiagnosisEntity;
import com.monda.edoctor.wahiya.model.Gender;
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
public class PatientRes {
    private UUID id;

    private DoctorRes doctor;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Gender gender;

    private String mobilePhone;

    private String healthProfile;

    private String nic;

    private String imageUrl;

    private String userName;

    private Boolean isActive = true;

    private String email;

    private String currentDiagnosis;

    public static PatientRes buildSimple(PatientEntity p, DiagnosisEntity currentDiagnosis) {
        PatientRes res = null;

        if (p != null) {
            res = new PatientRes();
            res.id = p.getId();
            res.firstName = p.getFirstName();
            res.lastName = p.getLastName();
            res.birthDate = p.getBirthDate();
            res.imageUrl = p.getImageUrl();
            res.gender = p.getGender();
            res.currentDiagnosis = currentDiagnosis != null ? currentDiagnosis.getName() : null;
        }

        return res;
    }

    public static PatientRes buildDetail(PatientEntity p, DiagnosisEntity currentDiagnosis) {
        PatientRes res = null;

        if (p != null) {
            res = new PatientRes();
            res.id = p.getId();
            res.doctor = DoctorRes.buildSimple(p.getDoctor());
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
            res.gender = p.getGender();
            res.currentDiagnosis = currentDiagnosis != null ? currentDiagnosis.getName() : null;
        }

        return res;
    }
}

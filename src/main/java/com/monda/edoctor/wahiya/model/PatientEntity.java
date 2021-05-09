package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "patient", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="patient_id")
    private UUID patientId;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private Integer age;

    @Column(name="user_name")
    private String userName;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name="email")
    private String email;

    @Column(name="mobile")
    private String mobile;

    @Column(name="health_profile")
    private String healthProfile;

    @Column(name="doctor_id")
    private String doctorId;

    @Column(name="is_active")
    private Boolean isActive;

}

package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "basic_patient_info", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicPatientInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="patient_id")
    private UUID patientId;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private Integer age;

    @Column(name="doctorId")
    private String doctorId;

}

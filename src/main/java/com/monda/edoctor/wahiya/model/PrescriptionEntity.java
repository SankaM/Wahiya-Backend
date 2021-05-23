package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "prescription", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="prescription_id")
    private UUID prescriptionId;

    @Column(name="doctor_id")
    private String doctorId;

    @Column(name="patient_id")
    private Integer patientId;

    @Column(name="issued_date")
    private LocalDateTime issuedDate;
}

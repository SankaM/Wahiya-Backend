package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Column(name = "id")
    private UUID id;

    @Column(name = "doctor_id")
    private UUID doctorId;

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "diagnosis_id")
    private UUID diagnosisId;

    @Column(name = "prescription_date")
    private LocalDateTime prescriptionDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "doctor_cost")
    private Double doctorCost;

    @Column(name = "drug_cost")
    private Double drugCost;

    @Column(name = "total_cost")
    private Double totalCost;
}

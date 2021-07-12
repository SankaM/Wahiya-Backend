package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
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
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "diagnosis_id")
    private DiagnosisEntity diagnosis;

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

    @OneToMany
    @JoinColumn(name = "prescription_id")
    private List<DosageEntity> dosageList;
}

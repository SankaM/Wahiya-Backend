package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
    @Column(name="prescription_id")
    private UUID prescriptionId;

    @Column(name="doctor_id")
    private String doctorId;

    @Column(name="patient_id")
    private Integer patientId;

    @Column(name="issued_date")
    private LocalDateTime issuedDate;

    @Column(name="drug_id")
    private UUID drugId;

    @Column(name="units_per_dose")
    private Integer unitsPerDose;

    @Column(name="unit")
    private String unit;

    @Column(name="doses_per_day")
    private Integer dosesPerDay;

    @Column(name="number_of_days")
    private Integer numberOfDays;

    @Column(name="before_after_meal")
    private String beforeAfterMeal;

    @Column(name="note")
    private String note;

}

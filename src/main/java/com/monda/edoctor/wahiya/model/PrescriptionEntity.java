package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @Column(name = "id")
    private UUID id;

    @Column(name = "doctor_id")
    private UUID doctorId;

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "issued_date")
    private LocalDateTime issuedDate;
}

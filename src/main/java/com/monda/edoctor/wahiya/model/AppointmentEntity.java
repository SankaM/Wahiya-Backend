package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "appointment", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {
    public enum AppointmentStatus {
        REQUESTED, CONFIRMED, DECLINED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

//    @Column(name = "work_hour_id")
//    private UUID workHourId;
    @ManyToOne
    @JoinColumn(name = "work_hour_id")
    private WorkHourEntity workHour;

//    @Column(name = "patient_id")
//    private UUID patientId;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}

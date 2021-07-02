package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.UUID;

@Table(name = "work_hour", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkHourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "doctor_id")
    private UUID doctorId;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "time_")
    private String time;
}

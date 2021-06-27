package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "dose", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoseEntity {

    @Id
    @Column(name = "prescription_id")
    private UUID prescriptionId;

    @Column(name = "name")
    private UUID drugId;

    @Column(name = "units_per_dose")
    private Integer unitsPerDose;

    @Column(name = "doses_per_day")
    private Integer dosesPerDay;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "before_after_meal")
    private String beforeAfterMeal;

    @Column(name = "note")
    private String note;

    @Column(name = "fromDate")
    private LocalDateTime fromDate;

    @Column(name = "toDate")
    private LocalDateTime toDate;
}

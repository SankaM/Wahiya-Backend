package com.monda.edoctor.wahiya.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoseResponse {

    private DrugResponse drug;
    private Integer unitsPerDose;
    private Integer dosesPerDay;
    private Integer numberOfDays;
    private String beforeAfterMeal;
    private String note;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime toDate;

}

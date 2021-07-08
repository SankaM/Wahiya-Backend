package com.monda.edoctor.wahiya.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoseEntityRequest {

    private UUID drugId;
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

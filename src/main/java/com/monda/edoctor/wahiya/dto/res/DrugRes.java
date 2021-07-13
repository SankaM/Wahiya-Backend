package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.model.DrugEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugRes {
    private UUID id;

    private String name;

    private String description;

    private DrugEntity.DrugType type;

    private Double measurement;

    private DrugEntity.MeasurementUnit measurementUnit;

    private String imageUrl;

    public static DrugRes buildSimple(DrugEntity drug) {
        DrugRes res = null;

        if(drug != null) {
            res = new DrugRes();
            res.id = drug.getId();
            res.name = drug.getName();
            res.measurement = drug.getMeasurement();
            res.measurementUnit = drug.getMeasurementUnit();
        }

        return res;
    }

    public static DrugRes buildDetail(DrugEntity drug) {
        DrugRes res = null;

        if(drug != null) {
            res = new DrugRes();
            res.id = drug.getId();
            res.name = drug.getName();
            res.description = drug.getDescription();
            res.type = drug.getType();
            res.measurement = drug.getMeasurement();
            res.measurementUnit = drug.getMeasurementUnit();
            res.imageUrl = drug.getImageUrl();
        }

        return res;
    }
}

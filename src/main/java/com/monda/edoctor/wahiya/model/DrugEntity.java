package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "drug", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugEntity {
    public enum DrugType {
        // Reff: https://www.gosh.nhs.uk/conditions-and-treatments/medicines-information/types-medicines/
        LIQUID, TABLET, CAPSULE, CREAMS, SUPPOSITORIES, DROPS, INHALER, INJECTION, PATCHES, BUCCAL,
    }

    public enum MeasurementUnit {
        MG, ML,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DrugType type;

    @Column(name = "measurement")
    private Double measurement;

    @Column(name = "measurement_unit")
    @Enumerated(EnumType.STRING)
    private MeasurementUnit measurementUnit;

    @Column(name = "image_url")
    private String imageUrl;
}

package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "drug", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "available_units")
    private Double availableUnits;

    @Column(name = "unit")
    private String unit;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public enum UpdateType {
        ADD,
        SET,
        DEDUCT
    }

}

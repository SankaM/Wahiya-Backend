package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
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
    @Column(name="drug_id")
    private UUID drugId;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="expiry_date")
    private LocalDate expiryDate;

    @Column(name="available_units")
    private Double availableUnits;

    @Column(name="unit")
    private String unit;

    @Column(name="unit_price")
    private Double unitPrice;

    @Column(name="image_link")
    private String imageLink;

    @Column(name="is_available")
    private Boolean isAvailable;

}

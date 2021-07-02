package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "inventory", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "doctor_id")
    private UUID doctorId;

    @Column(name = "drug_id")
    private UUID drugId;

    @Column(name = "available_units")
    private Double availableUnits;

    @Column(name = "unit_sell_price")
    private Double unitSellPrice;

    @Column(name = "unit_price_currency")
    private String unitPriceCurrency;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}

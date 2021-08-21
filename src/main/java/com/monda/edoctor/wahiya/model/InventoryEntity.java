package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
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

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "drug_id")
    private DrugEntity drug;

    @Column(name = "available_units")
    private Double availableUnits;

    @Column(name = "unit_sell_price")
    private Double unitSellPrice;

    @Column(name = "unit_price_currency")
    private String unitPriceCurrency;

    @Column(name = "unit_threshold_warning")
    private Double unitThresholdWarning;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany
    @JoinColumn(name = "inventory_id")
    private List<InventoryBatchEntity> inventoryBatchList;
}

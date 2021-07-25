package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "inventory_batch", schema = "wahiya")
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inventory_id")
    private InventoryEntity inventory;

    @Column(name = "unit_buy_price")
    private Double unitBuyPrice;

    @Column(name = "unit_price_currency")
    private String unitPriceCurrency;

    @Column(name = "unit_count")
    private Double unitCounts;

    @Column(name = "batch_date")
    private LocalDate batchDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;
}

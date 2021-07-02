package com.monda.edoctor.wahiya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "inventory_id")
    private UUID inventoryId;

    @Column(name = "unit_buy_price")
    private Double unitBuyPrice;

    @Column(name = "unit_price_currency")
    private String unitPriceCurrency;

    @Column(name = "unit_count")
    private Double unitCounts;

    @Column(name = "batch_date")
    private LocalDateTime batchDate;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}

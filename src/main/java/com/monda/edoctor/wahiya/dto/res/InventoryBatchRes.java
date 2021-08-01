package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.model.InventoryBatchEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryBatchRes {
    private UUID id;

    private Double unitBuyPrice;

    private String unitPriceCurrency;

    private Double unitCounts;

    private LocalDate batchDate;

    private LocalDate expiryDate;

    public static InventoryBatchRes build(InventoryBatchEntity ib) {
        InventoryBatchRes res = null;

        if (ib != null) {
            res = new InventoryBatchRes();
            res.id = ib.getId();
            res.batchDate = ib.getBatchDate();
            res.expiryDate = ib.getExpiryDate();
            res.unitBuyPrice = ib.getUnitBuyPrice();
            res.unitPriceCurrency = ib.getUnitPriceCurrency();
            res.unitCounts = ib.getUnitCounts();
        }

        return res;
    }
}

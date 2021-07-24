package com.monda.edoctor.wahiya.dto.res;

import com.monda.edoctor.wahiya.model.InventoryEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRes {
    private UUID id;

    private DrugRes drug;

    private Double availableUnits;

    private Double unitSellPrice;

    private String unitPriceCurrency;

    private Boolean isAvailable = true;

    public static InventoryRes buildSimple(InventoryEntity i) {
        InventoryRes res = null;

        if(i != null) {
            res = new InventoryRes();
            res.id = i.getId();
            res.drug = DrugRes.buildDetail(i.getDrug());
            res.availableUnits = i.getAvailableUnits();
            res.unitSellPrice = i.getUnitSellPrice();
            res.unitPriceCurrency = i.getUnitPriceCurrency();
            res.isAvailable = i.getIsAvailable();
        }

        return res;
    }
}

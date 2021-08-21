package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.model.InventoryEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryRes {
    private UUID id;

    private DrugRes drug;

    private Double availableUnits;

    private Double unitThresholdWarning;

    private Double unitSellPrice;

    private String unitPriceCurrency;

    private Boolean isAvailable = true;

    private List<InventoryBatchRes> inventoryBatchList;

    public static InventoryRes buildSimple(InventoryEntity i) {
        InventoryRes res = null;

        if(i != null) {
            res = new InventoryRes();
            res.id = i.getId();
            res.drug = DrugRes.buildDetail(i.getDrug());
            res.availableUnits = i.getAvailableUnits();
            res.unitThresholdWarning = i.getUnitThresholdWarning();
            res.unitSellPrice = i.getUnitSellPrice();
            res.unitPriceCurrency = i.getUnitPriceCurrency();
            res.isAvailable = i.getIsAvailable();
        }

        return res;
    }

    public static InventoryRes buildDetail(InventoryEntity i) {
        InventoryRes res = null;

        if(i != null) {
            res = new InventoryRes();
            res.id = i.getId();
            res.drug = DrugRes.buildDetail(i.getDrug());
            res.availableUnits = i.getAvailableUnits();
            res.unitThresholdWarning = i.getUnitThresholdWarning();
            res.unitSellPrice = i.getUnitSellPrice();
            res.unitPriceCurrency = i.getUnitPriceCurrency();
            res.isAvailable = i.getIsAvailable();
            res.inventoryBatchList = new ArrayList<>();

            if(i.getInventoryBatchList() != null && i.getInventoryBatchList().size() > 0) {
                res.inventoryBatchList.addAll(i.getInventoryBatchList().stream().map(ib -> InventoryBatchRes.build(ib)).collect(Collectors.toList()));
            }
        }

        return res;
    }
}

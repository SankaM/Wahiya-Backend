package com.monda.edoctor.wahiya.dto.req;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewBatchInventoryReq {
    private String drugId;

    private Double unitSellPrice;

    private String unitSellCurrency;

    private Double unitBuyPrice;

    private String unitBuyCurrency;

    private Double unitCount;

    private Double unitThresholdWarning;

    private String batchDate;

    private String expiryDate;
}

package com.monda.edoctor.wahiya.dto.req;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBatchInventoryReq {
    private Double unitSellPrice;

    private String unitSellCurrency;

    private Double unitBuyPrice;

    private String unitBuyCurrency;

    private Double unitCount;

    private String batchDate;

    private String expiryDate;
}

package com.dj.boot.pojo.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingLineDto {
    private String sellerPurchaseNo;
    private String factoryCode;
    private String lineNo;
    private String itemId;
    private BigDecimal expectedQty;
    private String isvLotattrs;
    private String stockLocationCode;
    private String unitNo;
}

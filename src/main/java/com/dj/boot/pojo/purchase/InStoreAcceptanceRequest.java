package com.dj.boot.pojo.purchase;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InStoreAcceptanceRequest {

    private String receivingNo;
    private String containerNo;
    private String realReceivedUsers;
    private String bookTips;
    private Integer isAppearance;
    private Integer qtyMatch;
    private Integer modelMatch;
    private Integer qualityTag;
    private String other;
    private Integer purchasingEligibility;
    private Integer technicalFocusEligibility;
    private Integer archivesEligibility;
    private Integer isPass;
    private List<ReceivingLineDto> receivingLines;
}

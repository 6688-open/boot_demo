package com.dj.boot.pojo.purchase.insr;



import com.dj.boot.pojo.purchase.ReceivingLineDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 协同透传wms验收明细信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcceptanceDto {

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

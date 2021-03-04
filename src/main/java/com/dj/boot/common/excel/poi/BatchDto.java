package com.dj.boot.common.excel.poi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.excel.poi
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-09-08-15-06
 */
@Data
@NoArgsConstructor
public class BatchDto {

    private String goodsLevel;
    private BigDecimal backQty;
    private String batchNo;
    private String lotAttrStr;
}

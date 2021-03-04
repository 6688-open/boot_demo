package com.dj.boot.controller.tenant.vo;


import com.dj.boot.common.excel.exc.Excel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class GoodsVo {

    @Excel(name = "物料组")
    private String goods;
    @Excel(name = "物料组描述")
    private String goodsDesc;


    public GoodsVo(String goods, String goodsDesc) {
        this.goods = goods;
        this.goodsDesc = goodsDesc;
    }


}

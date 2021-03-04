package com.dj.boot.common.excel.poi;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 物流执行单明细
 *
 * @author tianka
 * @Date 2019-10-17 21:33:1
 */
@Data
@NoArgsConstructor
public class ReceiptsPerformItemDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 等同于id，只是为前端展示用
     */
    private String sId;
    /**
     * 入库编号
     */
    private String receiptsPerformNo;
    /**
     * 物资编码
     */
    @NotNull(message = "物资编码不能为空")
    private String goodsNo;
    /**
     * 物资名称
     */
    @NotNull(message = "物资名称不能为空")
    private String goodsName;
    /**
     * 计划商品数量
     */
    @NotNull(message = "商品数量不能为空")
    @Positive(message = "商品数量不能小于等于0")
    @Digits(integer = 12, fraction = 2, message = "单据明细数量长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal goodsApplyQty;
    /**
     * 计划入库商品等级
     */
    @NotNull(message = "商品等级不能为空")
    private String goodsLevel;
    /**
     * 实际入库数量
     */
    private BigDecimal realBackQty;

    /**
     * 生产时间（2020-03-12）
     */
    private String productTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 0：没有删除，1：删除
     */
    private Integer delete;



    /**
     * 行号
     */
    private String lineNo;

    /**
     * @return
     */
    private String remark;

    /**
     * 租户编号
     *
     * @return
     */
    private String tenantId;

    /**
     * 计量单位名称
     */
    private String unitName;

    /*物资码*/
    private List<String> materialCodes;



    /**
     * 起始物资码
     */
    private String startMaterialCode;

    /**
     * 结束物资码
     */
    private String endMaterialCode;
    private String spGoodsNo;
    private List<BatchDto> BatchDtoList;

}
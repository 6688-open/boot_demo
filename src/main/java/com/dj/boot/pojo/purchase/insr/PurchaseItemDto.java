package com.dj.boot.pojo.purchase.insr;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购单明细
 *
 * @author tianka
 * @Date 2019-9-27 11:0:39
 */
@Data
@NoArgsConstructor
public class PurchaseItemDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 行号
     */
    @NotBlank(message = "行号必填")
    @Size(max = 20, message = "行号不能超过20位")
    private String lineNo;
    /**
     * oms商品编码
     */
    private String goodsNo;
    /**
     * 商家商品编码
     */
    private String spGoodsNo;
    /**
     * 货主商品编码
     */
    private String ownerGoodsNo;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品等级
     */
    private String goodsLevel;
    /**
     * 商品等级
     */
    private String goodsLevelName;

    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 采购单编号
     */
    private String purchaseNo;

    /**
     * 实际入库数量
     */
    private BigDecimal realBackQty;

    private String realBackLevel;
    /**
     * 差异数量
     */
    private BigDecimal diffQty;
    /**
     * 少货数量
     */
    private BigDecimal shortQty;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 单位转换率
     */
    private BigDecimal unitRate;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 辅单位申请数量
     */
    @Positive(message = "辅申请数量需大于0")
    @Digits(integer = 12, fraction = 2, message = "单据明细行商品数量长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal applyQty;
    /**
     * 辅单位预占数量
     */
    private BigDecimal occupyQty;
    /**
     * 主单位数量
     */
    @Positive(message = "申请数量需大于0")
    @Digits(integer = 12, fraction = 2, message = "申请数量长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal applyMainQty;
    /**
     * 主单位计量单位ID
     */
    private Long mainUnitId;
    /**
     * 主单位数量
     */
    private String mainUnitName;
    /**
     * 含税单价
     */
    @Positive(message = "含税单价需大于0")
    @Digits(integer = 12, fraction = 2, message = "含税单价整数位不能超过{integer}位,小数位不能超过{fraction}位")
    private BigDecimal taxInUnitPrice;
    /**
     * 无税单价
     */
    @Positive(message = "无税单价需大于0")
    @Digits(integer = 12, fraction = 2, message = "含税单价整数位不能超过{integer}位,小数位不能超过{fraction}位")
    private BigDecimal taxExUnitPrice;
    /**
     * 含税净价
     */
    @Positive(message = "含税净价需大于0")
    @Digits(integer = 12, fraction = 2, message = "含税净价整数位不能超过{integer}位,小数位不能超过{fraction}位")
    private BigDecimal taxInCleanPrice;
    /**
     * 无税净价
     */
    @Positive(message = "无税净价需大于0")
    @Digits(integer = 12, fraction = 2, message = "无税净价整数位不能超过{integer}位,小数位不能超过{fraction}位")
    private BigDecimal taxExCleanPrice;
    /**
     * 主单位含税单价
     */
    @Positive(message = "主单位含税单价需大于0")
    @Digits(integer = 12, fraction = 2, message = "主单位含税单价整数位不能超过{integer}位,小数位不能超过{fraction}位")
    private BigDecimal taxInMainUnitPrice;
    /**
     * 主单位无税单价
     */
    @Positive(message = "主单位无税单价需大于0")
    @Digits(integer = 12, fraction = 2, message = "主单位无税单价整数位不能超过{integer}位,小数位不能超过{fraction}位")
    private BigDecimal taxExMainUnitPrice;
    /**
     * 主单位含税净价
     */
    @Positive(message = "主单位含税净价需大于0")
    @Digits(integer = 12, fraction = 2, message = "主单位含税净价整数位不能超过{integer}位,小数位不能超过{fraction}位")
    private BigDecimal taxInMainCleanPrice;
    /**
     * 主单位无税净价
     */
    @Positive(message = "主单位无税净价需大于0")
    @Digits(integer = 12, fraction = 2, message = "主单位无税净价整数位不能超过{integer}位,小数位不能超过{fraction}位")
    private BigDecimal taxExMainCleanPrice;
    /**
     * 单品扣率
     */
    private BigDecimal itemDiscountRate;
    /**
     * 整单扣率
     */
    private BigDecimal orderDiscountRate;
    /**
     * 税额
     */
    private BigDecimal taxAmount;
    /**
     * 折扣额
     */
    private BigDecimal discountAmount;
    /**
     * 无税金额
     */
    private BigDecimal taxExAmount;
    /**
     * 价税合计
     */
    private BigDecimal taxInAmount;

//    /**
//     * 批属性
//     */
//    @Size(max = 10, message = "批属性不能超过10条")
//    private List<LotItemDto> lotItemDtos;

    private String lotAttrStr;

    @Size(max = 500, message = "附加信息小于500")
    private String extraMsg;

    private Map<String, String> lotAttrMap;

    /**
     * 是否删除 YNEnum
     */
    private Integer delete;

    /**
     * 行状态
     * @ItemStatusEnum
     */
    private Integer itemStatus;

    /**
     * 行备注
     */
    @Size(max = 500, message = "行备注超长")
    private String remark;

    /**
     * 针对残品入库的已使用时间：单位：天
     */
    @Positive(message = "已使用时间必须大于0")
    private Integer usedTime;

    /**
     * 计量单位ID
     */
    private Long unitId;

    /**
     * 商家主单位ID
     */
    private String sellerMainUnitId;

    /**
     * 跳单标识-0非跳单，1、虚拟到货、2、真实到货
     */
    private Byte crossFlowFlag;

    /**
     * 跳单量-下发量
     */
    private BigDecimal crossNum;

    /**
     * 已下发量
     */
    private BigDecimal crossIssuedNum;

    /**
     * 跳单差额
     */
    private BigDecimal crossNumDiff;
    /**
     * 不存库
     */
    private String tenantGoodsNo;
}
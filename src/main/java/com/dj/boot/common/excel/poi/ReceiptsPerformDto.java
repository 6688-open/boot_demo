package com.dj.boot.common.excel.poi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 物流执行单
 *
 * @author wj
 * @Date 2019-10-17 21:33:0
 */
@Data
@NoArgsConstructor
public class ReceiptsPerformDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 物流执行单单号
     */
    private String receiptsPerformNo;
    /**
     * 关联出库单号
     */
    private String referenceOdoNo;
    /**
     * 企业编号
     */
    private String enterpriseNo;
    /**
     * 企业名称
     */
    private String enterpriseName;
    /**
     * 货主编号
     */
    @NotBlank(message = "货主编号不能为空")
    private String ownerNo;
    /**
     * 货主名称
     */
    private String ownerName;
    /**
     * 商家入库单号
     */
    @NotBlank(message = "商家入库单号不能为空")
    private String sellerReceiptsPerformNo;
    /**
     * 源仓库编号
     */
    private String originWarehouseNo;
    /**
     * 源仓库名称
     */
    private String originWarehouseName;
    /**
     * 仓库编号
     */
    @NotBlank(message = "仓库不能为空")
    private String warehouseNo;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 收货等级
     *
     */
    private Byte receiveType;
    /**
     * 回传方式
     *
     */
    private Byte backType;
    /**
     * 备注
     */
    private String note;
    /**
     * 供应商编号
     */
    private String supplyNo;
    /**
     * 供应商名称
     */
    private String supplyName;
    /**
     * 承运商编号
     */
    private String carrierNo;
    /**
     * 承运商名称
     */
    private String carrierName;
    /**
     * 运单号
     */
    private String waybillNo;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date spCreateTime;
    /**
     * 来源
     */
    @NotNull(message = "来源必填")
    private Byte source;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 接收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除1：没有删除；0：删除
     */
    private Integer delete;
    /**
     * 行数量之和
     */
    @Positive(message = "行数量之和不能小于等于0")
    @Digits(integer = 11, fraction = 3, message = "行数量之和长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal totalRowNums;
    /**
     * 不同OMS商品编码的数量之和
     */
    @Positive(message = "总SKU数量不能小于等于0")
    @Digits(integer = 11, fraction = 3, message = "总SKU数量长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal totalSkuQty;
    /**
     * 行金额汇总之和
     */
    @Positive(message = "行金额汇总不能小于等于0")
    @Digits(integer = 11, fraction = 3, message = "行金额汇总长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal totalMoney;
    /**
     * 状态
     *
     */
    private Integer status;

    /**
     * 租户编号
     *
     * @return
     */
    private String tenantId;

    @Valid
    @NotEmpty(message = "入库单明细不能为空")
    @Size(max = 500, message = "明细数不能超过500")
    public List<ReceiptsPerformItemDto> receiptsPerformItemDtoList;

    public List<ReceiptsPerformStatusDto> receiptsPerformStatusDtoList;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date spCreateBeginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date spCreateEndTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createBeginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createEndTime;

    /**
     * 单据类型
     *
     * @return
     */
    @NotNull(message = "单据类型不能为空")
    private String orderType;


    /**
     * 源单据单号
     */
    private String referenceReceiptsNo;

    /**
     * 商家关联单号
     */
    private String sellerReferenceNo;


    /**
     * 采购单完成时间
     *
     * @return
     */
    private Date completeTime;

    /**
     */
    private Integer hasDiffer;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 差异备注
     */
    private String diffRemark;
    /**
     * 总板数
     */
    private Integer sumPallets;

    /**
     * 当前板号
     */
    private String palletNo;


    /**
     * 取消状态
     */
    private Integer cancelStatus;

    /**
     * 取消失败原因
     */
    private String cancelFailureReason;


    /**
     * 货主列表
     */
    private List<String> ownerNoList;

    /**
     * pin码
     *
     * @return
     */
    @NotBlank(message = "操作人必填")
    private String pin;

    /**
     * isv编码
     *
     * @return
     */
    private String isvCode;


    /**
     * 外部承运商编码
     *
     * @return
     */
    private String outerCarrierNo;

    /**
     */
    @Deprecated
    private Boolean autoClose;

    private String batchNo;
    /**
     * 单据业务类型名称
     */
    private String orderTypeName;
    /**
     * 单据标识串
     */
    private String orderMark;
    /**
     * 工厂编码
     */
    private String factoryCode;
    /**
     * 工厂名称
     */
    private String factoryName;
    /**
     * SAP库存地编码
     */
    private String stockLocationCode;
    /**
     * SAP库存地名称
     */
    private String stockLocationName;

    /**
     * 工厂编码
     */
    private String originFactoryCode;
    /**
     * SAP库存地编码
     */
    private String originStockLocationCode;
    /**
     * 溯源码
     */
    private Byte tracingCode;
    /**
     * 调整单
     */
    private Byte stockChange;

    private String extraMsg;

    private String wmsOrderNo;

    /**
     * 验收明细 json --主要透传wms
     */
    private String acceptanceDetails;

    /**
     * 过账日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postingDate;

    /**
     * 凭证日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date documentDate;
    /**
     * 采购申请单号
     */
    private String purchaseRequestOrderNo;

    /**
     * 跳单标识  0非跳单，1、虚拟到货、2、真实到货
     */
    private Byte crossFlowFlag;

    /**
     * 仓库执行系统编号
     */
    private String warehouseExeSystemNo;
    /**
     * 企业编号
     */
    private String sourceEnterpriseNo;
    /**
     * 货主编号
     */
    private String sourceOwnerNo;
    /**
     * 仓库编号
     */
    private String sourceWarehouseNo;

    /**
     * 入库类型
     */
    private Byte inboundType;
    /**
     * 0否1是
     * 快速入库(一键入库)
     */
    private Byte fastIncomingFlag;

    /**
     * 0否1是
     * 是否开票
     */
    private Byte invoiceFlag;

    /**
     * 0否1是
     * 是否结算
     */
    private Byte settlementFlag;


    private String statusName;
    private String sourceName;
    private String crossFlowFlagName;
    /**
     * 验收人
     */
    private String receivedUsers;

}
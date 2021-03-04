package com.dj.boot.pojo.purchase.insr;

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
 * 采购单主档
 *
 * @author tianka
 * @Date 2019-9-27 11:0:39
 */
@Data
@NoArgsConstructor
public class PurchaseMainDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 采购单编号
     */
    private String purchaseNo;
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
     * 商家采购单号
     */
    @NotBlank(message = "商家采购单号不能为空")
    private String sellerPurchaseNo;

    /**
     * 供应商编号
     */
    private String supplyNo;

    /**
     * 供应商名称
     */
    private String supplyName;
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
     * 状态
     */
    private Integer status;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 租户编号
     *
     * @return
     */
    private String tenantId;

    /**
     * 行数量之和
     */
    @Positive(message = "商品行数量之和不能小于等于0")
    @Digits(integer = 12, fraction = 2, message = "商品行数量之和长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal totalRowNums;
    /**
     * 不同OMS商品编码的数量之和
     */
    @Positive(message = "商品总SKU数量不能小于等于0")
    @Digits(integer = 12, fraction = 2, message = "商品总SKU数量长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal totalSkuQty;
    /**
     * 行金额汇总之和
     */
    @Positive(message = "商品总金额不能小于等于0")
    @Digits(integer = 12, fraction = 2, message = "单据总金额长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal totalMoney;

    /**
     * 销售平台创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date spCreateTime;
    /**
     * 单据来源
     */
    @NotNull(message = "来源必填")
    private Byte source;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date spCreateBeginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date spCreateEndTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createBeginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createEndTime;

    /**
     * 备注
     */
    private String note;
    /**
     * 入库单号（入库执行单单号来自 receiptsPerformDto.getReceiptsPerformNo()）
     */
    private String performNo;

    @Valid
    @NotEmpty(message = "明细不能为空")
    @Size(max = 500,message = "明细数不能超过500")
    private List<PurchaseItemDto> purchaseItemDtoList;



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
     * 单据类型
     * @return
     */
    @NotBlank(message = "单据业务类型必填")
    private String orderType;

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

    //    @NotBlank(message = "仓库编号不能为空")
    private String warehouseNo;

    private String warehouseName;
    /**
     * 批号
     * 用户分批生成入库单
     */
    @Size(max = 25)
    private String batchNo;
    /**
     * 自动关单
     */
    private Boolean autoClose;

    /**
     * 收货等级
     */
    private Byte receiveType;
    /**
     *单据业务类型名称
     */
    private String orderTypeName;
    /**
     *单据标识串
     */
    private String orderMark;
    /**
     *工厂编码
     */
    private String factoryCode;
    /**
     *工厂名称
     */
    private String factoryName;
    /**
     *SAP库存地编码
     */
    private String stockLocationCode;
    /**
     *SAP库存地名称
     */
    private String stockLocationName;

    private Byte backType;

    @Size(max = 500,message = "附加信息小于500")
    private String extraMsg;

    /**
     * 商家供应商编号
     */
    private String sellerSupplyNo;

    /**
     * 审核状态
     */
    private Integer approveStatus;


    private String sellerReceiptsPerformNo;

    /**
     * 业务类型为倒挂入库的采购单单号
     */
    private String upsideDownPurchaseNo;
    /**
     * 倒挂状态
     */
    private Integer upsideDownStatus;




    /**
     * 跳单标识-0非跳单，1、虚拟到货、2、真实到货
     */
    private Byte crossFlowFlag;
    /**
     * 操作人
     * 后续考虑使用operator来提代pin字段的使用
     */


    private String operator;
    /**
     * 原单据单号
     */
    private String originOrderNo;

    /**
     * 过账日期
     */
    private Date postingDate;

    /**
     * 凭证日期
     */
    private Date documentDate;



    /**
     * 1创建
     * 2修改
     * 3取消
     */
    private String operateType;

    private String supplyEnterpriseNo;

    private String supplyEnterpriseName;

    private String customerNo;


    /**
     * 透传wms验收明细信息
     */
    private List<AcceptanceDto> acceptanceDtos;





}
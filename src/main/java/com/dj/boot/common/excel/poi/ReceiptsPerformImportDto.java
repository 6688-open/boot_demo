package com.dj.boot.common.excel.poi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ReceiptsPerformImportDto {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 入库单号
     */
    private String receiptsPerformNo;
    /**
     * 采购单号，下传才会不为空
     */
    private String referenceReceiptsNo;
    /**
     * 省份编码
     */
    @NotBlank(message = "省份编码不能为空")
    private String provinceNo;
    /**
     * 省份名称
     */
    @NotBlank(message = "省份名称不能为空")
    private String provinceName;
    /**
     * 采购类型（1-年度采购、2-应急采购）
     */
    @NotNull(message = "采购类型不能为空")
    private Integer orderType;

    /**
     * 计划完成时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date planCompleteTime;

    /**
     * 机构编号
     */
    @NotBlank(message = "机构编号不能为空")
    private String orgNo;
    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 供应商编号
     */
    private String supplyNo;
    /**
     * 供应商名称
     */
    private String supplyName;

    /**
     * 来源(1-需求、2-计划)
     */
    @NotNull(message = "来源不能为空")
    private Integer source;

    /**
     * 仓库编号
     */
    @NotBlank(message = "仓库不能为空")
    private String warehouseNo;
    /**
     * 仓库名称
     */
    @NotBlank(message = "仓库名称不能为空")
    private String warehouseName;

    /**
     * 回传方式(1-按单,2-按板) 上游建单默认（按单）
     */
    //@NotNull(message = "回传方式不能为空")
    private Byte backType;

    /**
     * 状态  "新建"、"已下发库房"、"入库完成"  取消
     * @see
     */
    private Integer status;

    /**
     * 收货等级（本期不做）
     */
    private Byte receiveType;

    /**
     * 入库凭证（证明）
     */
    private String certificate;
    /**
     * 备注
     */
    private String remark;

    /**
     * 来源(1-上游（流程）建单、2-页面建单、3-导入建单)
     */
    private Byte orderSource;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除 0：没有删除；1：删除
     */
    private Integer delete;

    @Valid
    @NotEmpty(message = "入库单明细不能为空")
    public List<ReceiptsPerformItemDto> receiptsPerformItemDtoList ;

    public List<ReceiptsPerformStatusDto>receiptsPerformStatusDtoList ;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date spCreateBeginTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date spCreateEndTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createBeginTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createEndTime;


    /**
     * 完成时间
     */
    private Date completeTime;

    /**
     * 租户编号
     *
     * @return
     */
    private String tenantId;


    /**
     * 操作人
     */
    private String operator;


    /**
     * 前端（保存按钮-1 和保存并下发按钮-2）
     */
    private Byte operate;
}

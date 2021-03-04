package com.dj.boot.common.excel.poi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 入库单状态
 *
 */
@Data
@NoArgsConstructor
public class ReceiptsPerformStatusDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 等同于id，只为js前端解析
     */
    private String sId;
    /**
     * 入库单号
     */
    private String receiptsPerformNo;
    /**
     * 状态
     */
    private Integer status;
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
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 1：没有删除，0：删除
     */
    private Byte delete;
    
    /**
     * 租户编号
     *
     * @return
     */
    private String tenantId;


}
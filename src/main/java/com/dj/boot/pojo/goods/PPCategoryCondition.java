package com.dj.boot.pojo.goods;

import java.io.Serializable;

/**
 * 销售平台分页查询条件类
 */
public class PPCategoryCondition implements Serializable {


    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 顶级
     */
    private String ppTreeCode;


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPpTreeCode() {
        return ppTreeCode;
    }

    public void setPpTreeCode(String ppTreeCode) {
        this.ppTreeCode = ppTreeCode;
    }
}

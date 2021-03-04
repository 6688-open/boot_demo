package com.dj.boot.pojo.goods;

import java.io.Serializable;

/**
 * 销售平台分页查询条件类
 */
public class ParentCategoryCondition implements Serializable {


    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 父id
     */
    private Long pid;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}

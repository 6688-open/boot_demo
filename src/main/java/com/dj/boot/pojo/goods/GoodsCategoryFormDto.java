package com.dj.boot.pojo.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 租户级商品分类
 *
 * @author wj
 * @Date 2019-7-30 18:3:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsCategoryFormDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 父id
     */
    private Long pid;
    /**
     * 分类等级
     */
    private Byte level;

    /**
     * 父级的treeCode
     */
    private String pTreeCode;
    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 启用停用状态：0启用1停用
     */
    private Byte status;


    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 更新用户
     */
    private String updateUser;
    /**
     * 外部分类编码
     */
    private String outCategoryNo;
}
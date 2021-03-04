package com.dj.boot.pojo.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 租户级商品分类
 *
 * @author zhuhua
 * @Date 2019-7-30 18:3:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 分类编码
     */
    private String categoryNo;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 启用状态
     */
    private Byte status;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 分类等级
     */
    private Byte level;


    /**
     * 父级层级树
     */
    private String pTreeCode;


    /**
     * 层级树
     */
    private String treeCode;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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
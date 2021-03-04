package com.dj.boot.pojo.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 租户级商品分类父子关系类
 *
 * @author zhuhua
 * @Date 2019-7-30 18:3:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryViewDto implements Serializable {
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
     * 父id
     */
    private Long pid;
    /**
     * 分类等级
     */
    private Byte level;

    /**
     * 层级编码
     */
    private String treeCode;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 启用停用状态：0启用1停用
     */
    private Byte status;

    /**
     * 子级
     */
    private  List<CategoryViewDto> children;
    /**
     * 分类编码
     */
    private String outCategoryNo;
}
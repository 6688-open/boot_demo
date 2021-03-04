package com.dj.boot.service.goods.manager;


import com.dj.boot.pojo.goods.*;

import java.util.List;
import java.util.Map;

/**
 * 租户级商品分类管理器
 *
 * @author zhuhua
 * @Date 2019-7-30 18:3:20
 */
public interface GoodsCategoryManager {

    /**
     * 获取租户级商品分类
     *
     * @param id
     * @return the GoodsCategory
     */
    public GoodsCategoryDto getGoodsCategoryById(Long id);

    /**
     * 插入租户级商品分类
     *
     * @param goodsCategory
     */
    public Integer insertGoodsCategory(GoodsCategory goodsCategory);

    /**
     * 更新租户级商品分类
     *
     * @param goodsCategory
     */
    public Integer updateGoodsCategory(GoodsCategory goodsCategory);

    /**
     * 删除租户级商品分类
     *
     * @param id
     */
    public Integer deleteGoodsCategory(Long id);

    /**
     * 租户级商品分类分页查询方法
     *
     * @param
     */
    public List<GoodsCategoryDto> queryViewList(CategoryViewCondition param);

    /**
     * 查询一个节点的上级一直到顶级
     *
     * @param
     */
    public List<GoodsCategoryDto> queryPPList(PPCategoryCondition param);

    /**
     * 根据父id查询子级List
     *
     * @param
     */
    public List<GoodsCategoryDto> queryChildList(ParentCategoryCondition param);


    /**
     * 启用停用分类
     */
    public void updateStartOrStop(GoodsCategoryFormDto param);

    /**
     * 检查分类名称是否在租户下重复
     * true 存在重复 false 不存在重复
     */
    public boolean checkDuplicate(GoodsCategory goodsCategory);
    /**
     * 批量插入
     * @param list
     * @return
     */
    public Integer insertBatchGoodsCategory(List<GoodsCategory> list);
    /**
     * 查询总数
     */
    public Integer getCount(CategoryViewCondition param);
}

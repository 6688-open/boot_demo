package com.dj.boot.mapper.goods;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.boot.pojo.goods.*;
import com.dj.boot.pojo.useritem.UserItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 租户级商品分类Dao
 *
 * @author zhuhua
 * @Date 2019-7-30 18:3:20
 */
public interface GoodsCategoryDao extends BaseMapper<GoodsCategoryDto> {

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
     * @param param
     */
    public List<GoodsCategoryDto> queryViewList(@Param("po") CategoryViewCondition param);

    /**
     * 查询一个节点的上级一直到顶级
     *
     * @param
     */
    public List<GoodsCategoryDto> queryPPList(@Param("po") PPCategoryCondition param);


    /**
     * 根据父id查询子级List
     *
     * @param
     */
    public List<GoodsCategoryDto> queryChildList(@Param("po") ParentCategoryCondition param);

    /**
     * 启用停用分类
     */
    public Integer startOrStop(@Param("po") GoodsCategoryFormDto param);


    /**
     * 检查分类名称在租户下重复数量
     */
    public Integer getDuplicateQty(@Param("po") GoodsCategory goodsCategory);
    /**
     * 批量插入
     */
    public Integer batchInsertGoodsCategory(@Param("list") List<GoodsCategory> goodsCategorys);
    /**
     * 查询总数
     */
    public Integer getCount(@Param("po") CategoryViewCondition param);
}

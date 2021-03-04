package com.dj.boot.service.goods;


import com.dj.boot.common.base.Request;
import com.dj.boot.common.base.Response;
import com.dj.boot.pojo.goods.*;

import java.util.List;
import java.util.Map;

/**
 * 对外服务
 *
 * @author zhuhua
 * @Date 2019-7-30 18:3:20
 */
public interface GoodsCategoryService {

    /**
     * 测试服务可用性的方法
     *
     * @param message
     * @return
     */
    public String echo(String message);


    /**
     * 租户级商品分类分页查询方法
     *
     * @param
     */
    public Response queryViewList(Request<CategoryViewCondition> param);

    /**
     * 根据父id查询子级List
     *
     * @param
     */
    public Response queryChildList(Request<ParentCategoryCondition> param);



    /**
     * 添加类
     *
     * @param param
     * @return
     */
    public Response add(Request<GoodsCategoryFormDto> param);

    /**
     * 更新分类
     *
     * @param param
     * @return
     */
    public Response update(Request<GoodsCategoryFormDto> param);

    /**
     * 启用停用分类
     *
     * @param param
     * @return
     */
    public Response updateStartOrStop(Request<GoodsCategoryFormDto> param);


    /**
     * 根据id查询对象
     *
     * @param param
     * @return
     */
    public Response<GoodsCategoryDto> getById(Request<Long> param);

    /**
     * 根据id删除对象
     *
     * @param param
     * @return
     */
    public Response delete(Request<Long> param);
    /**
     * 批量插入
     * @param param
     * @return
     */
    public Response addBatch(Request<List<GoodsCategoryFormDto>> param);
    /**
     * 查询总数
     */
    public Response getCount(Request<CategoryViewCondition> param);

    /**
     * 商品级联分类添加
     */
    public Response<String> addCateCascadeAddDto(CategoryAddDto addDto);
}

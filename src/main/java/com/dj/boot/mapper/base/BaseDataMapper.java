package com.dj.boot.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.boot.pojo.base.BaseData;
import com.dj.boot.pojo.goods.GoodsCategoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * BaseDataMapper继承基类
 */
@Mapper
@Repository
public interface BaseDataMapper extends BaseMapper<BaseData> {


    int deleteByPrimaryKey(Integer id);

    int insert(BaseData baseData);

    int insertSelective(BaseData baseData);

    BaseData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseData baseData);

    int updateByPrimaryKey(BaseData baseData);
}
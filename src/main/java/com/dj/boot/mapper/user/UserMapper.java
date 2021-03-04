package com.dj.boot.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.dao.DataAccessException;

import java.util.Dictionary;
import java.util.List;

/**
 * @author ext.wangjia
 */
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into  #")
    void addUser();

    @SelectProvider(method = "findUserById",type = UserMapperSql.class)
    User findUserById(Integer id);

    /**
     * user展示
     */
    Page<User> findUserList(Page page, @Param("userDto") UserDto userDto) throws Exception;

    Long getCount();

    /**
     * getList
     * @return
     */
    List<User> getList();

    /**
     * 没有加注解 @Param("useList")     collection 必须list
     * 批量更新
     * @param list
     * @return
     */
    Integer updateBatch( List<User> list);

    /**
     * 批量新增
     * @param list
     * @return
     */
    Integer insertBatch( List<User> list);




    /**
     * user展示
     */
    List<User> findUserListByCondition( @Param("userDto") UserDto userDto) throws Exception;



    /**
     * 批量更新库存
     * @param userList
     * @param ids
     * @return
     */
    Integer updateBatchSaleableWarehouseStock(@Param("userList")List<User> userList, @Param("ids")List<Integer> ids);



}

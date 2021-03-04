package com.dj.boot.mapper.useritem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.boot.pojo.useritem.UserItem;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.mapper.useritem
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-18-11-10
 */
public interface UserItemMapper extends BaseMapper<UserItem> {
    /**
     * 添加
     * @param userItem
     * @return
     */
    Integer userItemAdd(UserItem userItem);
}

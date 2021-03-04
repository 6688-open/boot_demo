package com.dj.boot.service.useritem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.pojo.useritem.UserItem;

/**
 * @author ext.wangjia
 */
public interface UserItemService extends IService<UserItem> {
    /**
     * 添加
     * @param userItem
     * @return
     */
    Integer userItemAdd(UserItem userItem);
}

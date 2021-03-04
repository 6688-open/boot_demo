package com.dj.boot.service.useritem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.mapper.useritem.UserItemMapper;
import com.dj.boot.pojo.useritem.UserItem;
import com.dj.boot.service.useritem.UserItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.useritem.impl
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-18-11-09
 */
@Service
public class UserItemServiceImpl extends ServiceImpl<UserItemMapper, UserItem> implements UserItemService {

    @Resource
    private UserItemMapper userItemMapper;

    @Override
    public Integer userItemAdd(UserItem userItem) {
        return userItemMapper.userItemAdd(userItem);
    }
}

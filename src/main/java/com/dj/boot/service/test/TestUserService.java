package com.dj.boot.service.test;

import com.dj.boot.common.base.Response;
import com.dj.boot.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.test
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-19-15-21
 */

public interface TestUserService {


    String getUser();
    /**
     * 根据OpId查询云仓监控数据
     * @param user
     * @return
     */
    Response queryByOpId(User user);
}

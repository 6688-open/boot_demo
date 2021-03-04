package com.dj.boot.service.user.impl;

import com.dj.boot.common.base.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.user.impl
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-11-02-17-13
 */
@Slf4j
@Component
public class UserAdapter {



    public Response<String> generateOrder(){
        Response<String> response = Response.success();
        String str = "UserAdapter#generateOrder............................";
        response.setData(str);
        return response;
    }
}

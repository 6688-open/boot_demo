package com.dj.boot.service.test.impl;

import com.dj.boot.common.base.Response;
import com.dj.boot.common.enums.AppOperationTypeEnum;
import com.dj.boot.pojo.User;
import com.dj.boot.service.test.TestUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.test.impl
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-19-15-21
 */
@Service
public class TestUserServiceImpl implements TestUserService {


    /**
     * 日志对象
     */
    private static final Logger log = LogManager.getLogger(TestUserServiceImpl.class);

    @Override
    public String getUser() {
        return "222222222222222222222222222";
    }



    /**
     * 根据OpId查询云仓监控数据
     * @param user 请求参数
     * @return OpenDataResponse对象
     */
    @Override
    public Response queryByOpId(User user){
        Response response = new Response();

        if(AppOperationTypeEnum.getEnumByType(user.getSex()) == null){
            response.setMsg("查询条件opId错误");
            response.setCode(Response.ERROR_PARAM);
            return  response;
        }
        try{

            Method method = AppOperationTypeEnum.getMethodByType(user.getSex());
            method.invoke(this, user, response);

        } catch (Exception e){
            log.error("实时监控数据查询失败，OpId:{}", e.getMessage());
            response.setMsg("查询条件opId错误");
            response.setCode(Response.ERROR_PARAM);
            return response;
        }
        return response;
    }


    private void queryMainOpenData(User user, Response response){
        user.setUserName("wj");
        response.setData(user);
    }
}

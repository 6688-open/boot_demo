package com.dj.boot.service.handle;

import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Request;
import com.dj.boot.common.enums.HandleTypeEnums;
import com.dj.boot.configuration.quartz.util.SpringUtil;
import com.dj.boot.handeltest.AbstractHandlerType;
import com.dj.boot.handeltest.HandlerContext;
import com.dj.boot.handle.AbstractHandler;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryServiceImpl implements QueryService{

    @Override
    public BaseResponse<T> queryStock(Request<T> request) {
        //根据type 找到枚举维护的bean名称 通过反射机制获取对应的实现类
        String handleType = HandleTypeEnums.TYPE2ENUM_MAP.get(request.getOperateType()).getHandlerName();
        AbstractHandler abstractHandler = (AbstractHandler) SpringUtil.getBean(handleType);
        return abstractHandler.handle(request);
    }



    /**
     * 上下文Handler
     */
    @Autowired
    private HandlerContext handlerContext;

    @Override
    public List<String> handlerType(String handlerType) {
        //根据类型 获取到注解上的type类型的实现类handle
        AbstractHandlerType abstractHandlerType =  handlerContext.getInstance(handlerType);
        String condition = "userId";
        return abstractHandlerType.handle(condition);
    }
}

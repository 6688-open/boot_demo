package com.dj.boot.handeltest;


import java.util.Map;

/**
 * @description:
 * @author: wj
 * @create: 2020/03/21 23:34
 */
@SuppressWarnings("unchecked")
public class HandlerContext {

    private Map<String, Class> handlerMap;

    public HandlerContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractHandlerType getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type: " + type);
        }
        return (AbstractHandlerType) BeanTool.getBean(clazz);
    }

}
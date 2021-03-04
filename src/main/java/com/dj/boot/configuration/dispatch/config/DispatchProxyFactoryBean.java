package com.dj.boot.configuration.dispatch.config;

import com.dj.boot.configuration.dispatch.handler.DispatchInvocationHandler;
import com.dj.boot.configuration.dispatch.proxy.DispatchProxy;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;

/**
 * Spring为此提供了一个org.springframework.bean.factory.FactoryBean的工厂类接口，
 *  用户可以通过实现该接口定制实例化Bean的逻辑
 *
 * 归档代理FactoryBean
 * @param <T>
 * @User: ext.wangjia
 * @Author: wangJia
 */
public class DispatchProxyFactoryBean<T> implements FactoryBean<T> {
    /**
     * 被代理类接口
     */
    private Class<T> targetInterface;
    /**
     * 被代理类实例
     */
    private Map<String,T> targetInstances;
    /**
     * 默认Target
     */
    private String defaultTarget;
    /**
     * 调用处理
     */
    private DispatchInvocationHandler handler;

    /**
     * 实现了FactoryBean接口的bean是一类叫做factory的bean。
     * 其特点是，spring会在使用 getBean()调用获得该bean时，
     * 会自动调用该bean的getObject()方法，所以返回的不是factory这个bean，
     * 而是这个bean.getOjbect()方法的返回值：
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        //制实例化Bean的逻辑    这里返回的就是 创建一个代理对象的类
        return DispatchProxy.newDispatchProxy(targetInterface, targetInstances,defaultTarget,handler);
    }

    @Override
    public Class<?> getObjectType() {
        return targetInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setTargetInterface(Class<T> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public void setTargetInstances(Map<String, T> targetInstances) {
        this.targetInstances = targetInstances;
    }

    public void setDefaultTarget(String defaultTarget) {
        this.defaultTarget = defaultTarget;
    }

    public void setDispatchInvocationHandler(DispatchInvocationHandler handler) {
        this.handler = handler;
    }


    public Class<T> getTargetInterface() {
        return targetInterface;
    }

    public Map<String, T> getTargetInstances() {
        return targetInstances;
    }

    public String getDefaultTarget() {
        return defaultTarget;
    }

    public DispatchInvocationHandler getHandler() {
        return handler;
    }
}

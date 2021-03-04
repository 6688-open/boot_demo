package com.dj.boot.configuration.dispatch.handler;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author ext.wangjia
 */
public interface DispatchInvocationHandler {
    /**
     * invoke
     * @param candidateTarget
     * @param defaultTargetKey
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    Object invoke(Map<String,Object> candidateTarget,String defaultTargetKey, Method method,Object[] args) throws Throwable;
}

package com.dj.boot.configuration.dispatch.servlet;

import com.dj.boot.configuration.dispatch.proxy.DispatchSelector;
import com.dj.boot.configuration.dispatch.proxy.ProxyDispatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatchProxyHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        final HandlerMethod method = (HandlerMethod) handler;
        final ProxyDispatch proxy = method.getMethodAnnotation(ProxyDispatch.class);
        if (proxy == null) {
            return true;
        }
        //Controller 方法上加上注解 @ProxyDispatch("alien")
        //http://localhost:8082/user/userList?alien=2020
        System.out.println(proxy.value());
        //获取值2020
        final String targetSelectedValue = request.getParameter(proxy.value());
        DispatchSelector.select(targetSelectedValue);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //DispatchSelector.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //DispatchSelector.clear();
    }


}

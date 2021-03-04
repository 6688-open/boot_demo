package com.dj.boot.configuration.dispatch.servlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.dispatch.servlet
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-17-10-41
 */
@Configuration
public class InterceptorConfigByImplWebMvcConfigurer implements WebMvcConfigurer {
    @Bean
    public DispatchProxyHandlerInterceptor dispatchProxyHandlerInterceptor(){
        return new DispatchProxyHandlerInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dispatchProxyHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/*.html");
    }
}

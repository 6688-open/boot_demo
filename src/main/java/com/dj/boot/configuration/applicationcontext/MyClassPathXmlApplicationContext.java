package com.dj.boot.configuration.applicationcontext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.applicationcontext
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-28-18-03
 */
public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {
    public MyClassPathXmlApplicationContext(String... configLocations){
        super(configLocations);
    }
    @Override
    protected void initPropertySources(){
        //添加验证要求
        getEnvironment().setRequiredProperties("VAR");
    }
}

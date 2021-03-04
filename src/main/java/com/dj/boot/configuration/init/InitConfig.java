package com.dj.boot.configuration.init;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitConfig {
    @PostConstruct
    public void init(){
        //项目启动就会执行这个方法
        System.out.println(" .....init start...");
    }
}

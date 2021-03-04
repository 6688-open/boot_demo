package com.dj.boot.configuration.applicationcontext.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.applicationcontext.domain
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-27-14-26
 */
@Configuration
@Import({com.dj.boot.configuration.applicationcontext.domain.Actor.class})
@ComponentScan(basePackages = "com.dj.boot.configuration.applicationcontext.domain")
public class AppConfig {

    @Bean
    public UserTest userTest(){
        return new UserTest(4000,12,"Sally","123456","female");
    }
}

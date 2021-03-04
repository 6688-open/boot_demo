package com.dj.boot.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * AppConfiguration
 * 
 * @author: wj
 * @version: 2019-12-20 13:52:00
 */
@Configuration
@ComponentScan(basePackages = { "com.dj.boot" })
@MapperScan("com.dj.boot.mapper")
//开启定时
@EnableScheduling
@EnableAsync
public class AppConfiguration {

}
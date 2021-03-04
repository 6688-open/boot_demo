package com.dj.boot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 配合@Configuration 导入xml配置文件  配置类  也可以直接加到启动类上
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-21-15-09
 */
@Configuration
@ImportResource(locations = {"classpath:/spring/spring-main.xml"})
public class XmlConfiguration {


}

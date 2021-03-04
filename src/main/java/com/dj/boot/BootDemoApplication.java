package com.dj.boot;

import com.dj.boot.configuration.XmlConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ext.wangjia
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.BootDemoApplication
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-03-10-30
 */
@SpringBootApplication
@MapperScan("com.dj.boot.mapper")
//开启定时
@EnableScheduling
@EnableAsync
/**
 * 导入xml文件 也可以配合@Configuration 新增配置类  eg: XmlConfiguration
 *  1 @ImportResource 直接导入xml文件
 *  2 可以配合@Configuration 新增配置类 XmlConfiguration 需要加 @Configuration注解 无需导入配置类
 *  3 @Import 导入配置类  可以不加@Configuration注解
 */
//@ImportResource(locations = {"classpath:/spring/spring-main.xml"})
@Import(XmlConfiguration.class)
@PropertySources(value = {@PropertySource(value = {"classpath:prop/important.properties"}, encoding = "utf-8", ignoreResourceNotFound = true)})
public class BootDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(BootDemoApplication.class, args);
        System.out.println("BootDemoApplication start successfully......");
    }


    /**
     * bean 注入
     * @return
     */
    @Bean
    public Redisson redisson () {
        //单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
        return (Redisson)Redisson.create(config);
    }

}

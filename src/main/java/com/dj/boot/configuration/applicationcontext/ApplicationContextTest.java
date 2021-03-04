package com.dj.boot.configuration.applicationcontext;

import com.dj.boot.configuration.applicationcontext.domain.Actor;
import com.dj.boot.configuration.applicationcontext.domain.UserTest;
import com.dj.boot.configuration.applicationcontext.domain.UserTestAno;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.applicationcontext
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-27-13-50
 */
public class ApplicationContextTest {
    /**
     * 1.基于xml配置注册于获取bean
     *
     * 2.半注解半xml方式
     *
     * 3.基于注解注册与获取bean
     *
     * 常用于获取bean的类：
     *
     * ClassPathXmlApplicationContext、
     *
     * FileSystemXmlApplicationContext、
     *
     * AnnotationConfigApplicationContext
     */


    /**
     * 使用ClassPathXmlApplicationContext获取bean.
     */
    @Test
    public void test(){
        //使用BeanFactory方式加载XML.
        //BeanFactory bf = new XmlBeanFactory(new ClassPathResource("applicationcontext/applicationContext.xml"));
        //使用ClassPathXmlApplicationContext加载xml，默认从classpath下加载
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext/applicationContext.xml");
        UserTest userTest = context.getBean(UserTest.class);
        System.out.println("bean:"+ userTest);
    }


    /**
     * 使用FileSystemXmlApplicationContext获取bean.
     */
    @Test
    public void test2(){
        //使用FileSystemXmlApplicationContext加载xml，可指定文件位置
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:applicationcontext/applicationContext2.xml");
        UserTest user2 = context.getBean("user2",UserTest.class);
        System.out.println("bean:"+ user2);
    }


    /**
     *  2.半注解半xml方式
     *  此时还有配置文件，所以还是从文件中获取.
     */
    @Test
    public void test3(){
        //使用FileSystemXmlApplicationContext加载xml，可指定文件位置
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:applicationcontext/annotation.xml");
        UserTestAno userTestAno = context.getBean("userTestAno", UserTestAno.class);
        System.out.println("bean:"+ userTestAno);
    }


    /**
     * 基于注解
     *
     * @Configuration表明这是一个配置类，配置类具有xml文件具有的所有功能。
     *
     * @ComponentScan注解可以扫描包。
     *
     * @Bean注解代表这是一个bean,返回类型即bean类型。
     *
     * @Import注解可以把其他类注入当前配置中。
     *
     * 此时使用使用AnnotationConfigApplicationContext获取配置类
     */

    @Test
    public void test4(){
        //使用AnnotationConfigApplicationContext获取配置类
        //ApplicationContext context = new AnnotationConfigApplicationContext("com.config");

        ApplicationContext context = new AnnotationConfigApplicationContext(com.dj.boot.configuration.applicationcontext.domain.AppConfig.class);
        UserTest userTest = context.getBean("userTest",UserTest.class);
        System.out.println("bean:"+ userTest);

    }


    @Test
    public void test5(){
        //使用AnnotationConfigApplicationContext获取配置类

        ApplicationContext context = new AnnotationConfigApplicationContext(com.dj.boot.configuration.applicationcontext.domain.AppConfig.class);

        Actor actor=context.getBean("actor", Actor.class);
        System.out.println(actor);

    }


}

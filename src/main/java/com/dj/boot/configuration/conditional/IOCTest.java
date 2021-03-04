package com.dj.boot.configuration.conditional;

import com.dj.boot.pojo.User;
import org.apache.naming.factory.BeanFactory;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class IOCTest {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigConditionalTest.class);

    @Test
    public void  testConditional () {
        String[] beanNamesForType = context.getBeanNamesForType(User.class);
        for (String s : beanNamesForType) {
            System.out.println(s);
        }
        Map<String, User> users = context.getBeansOfType(User.class);
        System.out.println(users);
    }


    @Test
    public void  testImport () {
        String[] definitionNames = context.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }


        // 工厂bean获取的是调用getObject方法创建的对象
        Object colorFactoryBean = context.getBean("&colorFactoryBean");
        System.out.println("bean 类型,,,,,,,,,,,"+colorFactoryBean.getClass());
    }
}

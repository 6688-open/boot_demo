package com.dj.boot.configuration.conditional;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class MyConditionalWindow implements Condition {

    /**
     * ConditionContext 判断条件使用的上下文
     * AnnotatedTypeMetadata   当前 MyConditionalWindow的 注释信息
     * @param context
     * @param metadata
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //获取到当前ioc的beanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        // 获取类加载器
        ClassLoader classLoader = context.getClassLoader();
        //获取环境信息
        Environment environment = context.getEnvironment();
        //h获取bean定义的注册类
        BeanDefinitionRegistry registry = context.getRegistry();


        String property = environment.getProperty("os.name");
        assert property != null;
        if (property.contains("Windows")) {
            return true;
        }
        return false;
    }
}

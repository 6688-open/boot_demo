package com.dj.boot.configuration.quartz.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


/**
 *
 * SpringUtil
 *
 */
@Component
public class SpringUtil implements BeanFactoryPostProcessor {
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtil.beanFactory =  beanFactory;
    }

    public static Object getBean(String name) {
        try {
            Object bean = beanFactory.getBean(name);
            return bean;
        } catch (Exception e) {
            return null;
        }
    }
    public static <T> T getBean(Class<T> clazz){
        return beanFactory.getBean(clazz);
    }
}
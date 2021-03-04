package com.dj.boot.configuration.conditional;

import com.dj.boot.configuration.conditional.corlor.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefineRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * AnnotationMetadata 当前类的注解信息
     * BeanDefinitionRegistry    bean定义的注册类
     *    把所有需要添加到容器中的bean   BeanDefinitionRegistry.registerBeanDefinition  手动注册进来
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        boolean b = registry.containsBeanDefinition("com.dj.boot.configuration.conditional.corlor.Color");
        if (b) {
            RootBeanDefinition beanDefine = new RootBeanDefinition(RainBow.class);
            registry.registerBeanDefinition("rainBow", beanDefine);
        }
    }
}

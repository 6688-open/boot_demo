package com.dj.boot.configuration.conditional.corlor;

import org.springframework.beans.factory.FactoryBean;


//创建一个spring定义的FactoryBean
public class ColorFactoryBean implements FactoryBean<Color> {

    /**
     * 返回一个color对象这个对象会添加到容器中
     * @return
     * @throws Exception
     */
    @Override
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean ....... getObject");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

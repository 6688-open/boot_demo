package com.dj.boot.handeltest;

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * BeanFactoryPostProcessor
 * bean工厂的bean属性处理容器，
 * 说通俗一些就是可以管理我们的bean工厂内所有的beandefinition（未实例化）数据，可以随心所欲的修改属性。
 *
 * 当你实现了这个接口的时候，可以对还没有初始化的bean的属性进行修改或添加
 *
 * @description: 处理器核心逻辑
 * @author: wj
 * @create: 2020/03/21 23:05
 */
@Component
@SuppressWarnings("unchecked")
public class HandlerProcessor implements BeanFactoryPostProcessor {

    /**
     * 扫描的包路径
     */
    private static final String HANDLER_PACKAGE = "com.dj.boot.handeltest.biz";

    /**
     * @description 扫描@handlerType，初始化HandlerContext,将其注册到spring容器
     * @see  HandlerType
     * @see HandlerContext
     **/
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String,Class> handlerMap = Maps.newHashMapWithExpectedSize(4);
        ClassScaner.scan(HANDLER_PACKAGE,HandlerType.class).forEach(clazz->{
            //获取注解中的类型值
            String type = clazz.getAnnotation(HandlerType.class).value();
            //将注解中的值作为key，对应的类作为value，保存到Map中
            handlerMap.put(type,clazz);
        });
        //初始化HandlerContext，将其注册到spring容器中
        HandlerContext context = new HandlerContext(handlerMap);
        beanFactory.registerSingleton(HandlerContext.class.getName(),context);
    }
}

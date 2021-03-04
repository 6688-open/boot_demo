package com.dj.boot.configuration.dispatch.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 参考文档
 * https://blog.csdn.net/Hatsune_Miku_/article/details/77018869?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param
 *
 *
 * Spring的自定义标签解析是通过，写一个继承自NamespaceHandlerSupport的类，
 * 并实现init()方法，在init()方法中，去注册解析器。然后在解析xml时，
 * 通过约定的key去Map中拿到相应的解析器进行解析
 *
 * Spring提供了默认实现类NamespaceHandlerSupport，
 * 我们只需在init的时候注册每个元素的解析器即可。
 * @author ext.wangjia
 */
public class DispatchProxyNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        //解析xml标签  标签名称 proxy
        //遇到proxy元素的时候交给DispatchProxyBeanDefinitionParser来解析
        //解析为BeanDefinition，将里面的属性装配到BeanDefinition中，并注册到BeanDefinitionMap。
        registerBeanDefinitionParser("proxy",new DispatchProxyBeanDefinitionParser());
    }
}

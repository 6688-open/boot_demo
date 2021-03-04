package com.dj.boot.configuration.dispatch.config;

import com.dj.boot.configuration.dispatch.handler.DefaultDispatchInvocationHandler;
import com.dj.boot.configuration.dispatch.handler.DispatchInvocationHandler;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.Map;

/**DispatchProxyBeanDefinitionParser
 * 解析自定义标签
 *
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.dispatch.proxy
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-17-10-41
 */
public class DispatchProxyBeanDefinitionParser extends AbstractBeanDefinitionParser {

    private static final DispatchInvocationHandler DEFAULT_HANDLER = new DefaultDispatchInvocationHandler();
    private static final String ATTRIBUTE_TARGET_INTERFACE = "target-interface";
    private static final String ATTRIBUTE_DEFAULT_TARGET = "default-target";
    private static final String ATTRIBUTE_HANDLER_REF = "handler-ref";

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {

        //利用BeanDefinitionBuilder手动向Spring容器中注入Bean DispatchProxyFactoryBean (解析标签 赋值)
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DispatchProxyFactoryBean.class);
        try {
            String targetInterface = element.getAttribute(ATTRIBUTE_TARGET_INTERFACE);
            Class<?> targetInterfaceCls = ClassUtils.resolveClassName(targetInterface, null);

            builder.addPropertyValue("targetInterface", targetInterfaceCls);
            String defaultTarget = element.getAttribute(ATTRIBUTE_DEFAULT_TARGET);
            builder.addPropertyValue("defaultTarget",defaultTarget);
            Map targetMap = parserContext.getDelegate().parseMapElement(DomUtils.getChildElementByTagName(element,"map"), builder.getRawBeanDefinition());
            builder.addPropertyValue("targetInstances", targetMap);
            if(element.hasAttribute(ATTRIBUTE_HANDLER_REF)){
                String handler = element.getAttribute(ATTRIBUTE_HANDLER_REF);
                builder.addPropertyReference("dispatchInvocationHandler",handler);
            }else {
                builder.addPropertyValue("dispatchInvocationHandler",DEFAULT_HANDLER);
            }
            builder.getRawBeanDefinition().setPrimary(true);

        } catch (Exception ex) {
            XmlReaderContext readerContext = parserContext.getReaderContext();
            readerContext.error(ex.getMessage(), readerContext.extractSource(element), ex.getCause());
        }
        return builder.getBeanDefinition();
    }
}

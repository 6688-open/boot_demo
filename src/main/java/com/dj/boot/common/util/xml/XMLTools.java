package com.dj.boot.common.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created by IntelliJ IDEA.
 * User: wj
 * Date: 15-3-25
 * Time: 下午9:47
 */
public class XMLTools {
    public static <T> String generateXMLTaskContent(T bean) {
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        String msg = xstream.toXML(bean);
        return msg;
    }

    /*
     *   xml转对象，zhuhua
    */
    public static Object fromXMl2(String xml, Class a) {
        XStream xstream = XMLTools.getXStream();
        xstream.processAnnotations(a);
        xstream.autodetectAnnotations(true);
        return xstream.fromXML(xml);
    }


    /*
    * xml转对象，zhuhua
    */
    public static Object fromXML(String xml, Class a) {
        XStream xstream = XMLTools.getXStream();
        xstream.ignoreUnknownElements();
        xstream.processAnnotations(a);
        xstream.autodetectAnnotations(true);
        return xstream.fromXML(xml);
    }

    public static XStream getXStream(){
        XStream xStream = new XStream();
        setDenyTypes(xStream);
        return xStream;
    }

    public static XStream getXStreamByDomDriver(){
        XStream xStream = new XStream(new DomDriver());
        setDenyTypes(xStream);
        return xStream;
    }

    private static void setDenyTypes(XStream xStream){
        xStream.denyTypes(new String[]{"org.apache.commons.collections4.functors.InstantiateTransformer","org.apache.commons.collections4.functors.ConstantTransformer","org.apache.commons.collections4.functors.ChainedTransformer","org.apache.commons.collections4.functors.InvokerTransformer","org.apache.commons.collections4.comparators.TransformingComparator","org.apache.commons.configuration.ConfigurationMap","org.apache.commons.logging.impl.NoOpLog","org.apache.commons.configuration.Configuration","org.apache.commons.configuration.JNDIConfiguration","java.util.ServiceLoader$LazyIterator","com.sun.jndi.rmi.registry.BindingEnumeration","org.apache.commons.beanutils.BeanComparator","jdk.nashorn.internal.objects.NativeString","com.sun.xml.internal.bind.v2.runtime.unmarshaller.Base64Data","sun.misc.Service$LazyIterator","com.sun.jndi.rmi.registry.ReferenceWrapper","com.sun.jndi.toolkit.dir.LazySearchEnumerationImpl","org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator$PartiallyComparableAdvisorHolder","org.springframework.beans.factory.BeanFactory","org.springframework.jndi.support.SimpleJndiBeanFactory",
                "org.springframework.beans.factory.support.RootBeanDefinition","org.springframework.beans.factory.support.DefaultListableBeanFactory","org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor","org.springframework.aop.aspectj.annotation.BeanFactoryAspectInstanceFactory","org.springframework.aop.aspectj.AspectJPointcutAdvisor","org.springframework.aop.aspectj.AspectJAroundAdvice","org.springframework.aop.aspectj.AspectInstanceFactory","org.springframework.aop.aspectj.AbstractAspectJAdvice","javax.script.ScriptEngineFactory","com.sun.rowset.JdbcRowSetImpl","com.rometools.rome.feed.impl.ToStringBean","com.rometools.rome.feed.impl.EqualsBean","java.beans.EventHandler","javax.imageio.ImageIO$ContainsFilter","java.util.Collections$EmptyIterator",
                "javax.imageio.spi.FilterIterator","java.lang.ProcessBuilder","java.lang.Runtime","org.codehaus.groovy.runtime.MethodClosure","groovy.util.Expando","com.sun.xml.internal.ws.encoding.xml.XMLMessage$XmlDataSource","org.apache.commons.collections.map.LazyMap","org.apache.commons.collections.functors.ChainedTransformer","org.apache.commons.collections.functors.InvokerTransformer","org.apache.commons.collections.functors.ConstantTransformer","org.apache.commons.collections.keyvalue.TiedMapEntry"});
    }
}

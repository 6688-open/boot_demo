package com.dj.boot.handeltest.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 根据bean名称 或者 class类获取实例对象
 *
 * 当一个类实现了这个接口（ApplicationContextAware）之后，
 * 这个类就可以方便获得ApplicationContext中的所有bean。
 * 换句话说，就是这个类可以直接获取spring配置文件中，所有有引用到的bean对象。
 *
 * 在spring的配置文件中，注册方法类AppUtil。
 * 之所以方法类AppUtil能够灵活自如地获取ApplicationContext，就是因为spring能够为我们自动地执行了setApplicationContext。
 * 但是，spring不会无缘无故地为某个类执行它的方法的，
 * 所以，就很有必要通过注册方法类AppUtil的方式告知spring有这样子一个类的存在。
 * 这里我们使用  @Component来进行注册，
 * 或者我们也可以像下面这样在配置文件声明bean： <bean id="appUtil" class="com.htsoft.core.util.AppUtil"/>
 *
 * @Author: wangJia
 * @Date: 2020-07-15-15-58
 */
@Component
public class MyBeanFactoryAware implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    public static Object getBean(String name) {
        return beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        beanFactory = factory;
    }
}

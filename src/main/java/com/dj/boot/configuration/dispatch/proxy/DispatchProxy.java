package com.dj.boot.configuration.dispatch.proxy;


import com.dj.boot.configuration.dispatch.handler.DispatchInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 分发代理
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.dispatch.proxy
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-17-10-41
 */
public class DispatchProxy<T> implements InvocationHandler {
    private final Map<String, T> targetInstances;
    private final String defaultTarget;
    private final DispatchInvocationHandler handler;

    public DispatchProxy(Map<String, T> targetInstances, String defaultTarget, DispatchInvocationHandler handler) {
        this.targetInstances = targetInstances;
        this.defaultTarget = defaultTarget;
        this.handler = handler;
    }



    /**
     * InvocationHandler接口是proxy代理实例的调用处理程序实现的一个接口，
     * 每一个proxy代理实例都有一个关联的调用处理程序；
     * 在代理实例调用方法时，方法调用被编码分派到调用处理程序的invoke方法。
     *
     *
     *
     * 每一个动态代理类的调用处理程序都必须实现InvocationHandler接口，
     * 并且每个代理类的实例都关联到了实现该接口的动态代理类调用处理程序中，
     * 当我们通过动态代理对象调用一个方法时候，
     * 这个方法的调用就会被转发到实现InvocationHandler接口类的invoke方法来调用
     *
     * proxy:代理类代理的真实代理对象com.sun.proxy.$Proxy0
     * method:我们所要调用某个对象真实的方法的Method对象
     * args:指代代理对象方法传递的参数
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //If the method is a method from Object then defer to normal invocation.
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        //自定义invoke 实现
        return handler.invoke((Map<String, Object>) targetInstances,defaultTarget,method,args);
    }


    /**
     * Proxy类就是用来创建一个代理对象的类，它提供了很多方法，但是我们最常用的是newProxyInstance方法。
     *
     * (T) Proxy.newProxyInstance(classLoader, interfaces, h);
     *
     * loader：一个classloader对象，定义了由哪个classloader对象对生成的代理类进行加载
     *
     * interfaces：一个interface对象数组，表示我们将要给我们的代理对象提供一组什么样的接口，如果我们提供了这样一个接口对象数组，
     *              那么也就是声明了代理类实现了这些接口，代理类就可以调用接口中声明的所有方法。
     *
     * h：一个InvocationHandler对象，表示的是当动态代理对象调用方法的时候会关联到哪一个InvocationHandler对象上，并最终由其调用。
     * @param targetInterface
     * @param targetInstances
     * @param defaultTarget
     * @param handler
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T newDispatchProxy(Class<T> targetInterface, Map<String, T> targetInstances, String defaultTarget,DispatchInvocationHandler handler) {
        ClassLoader classLoader = targetInterface.getClassLoader();
        Class<?>[] interfaces = new Class[]{targetInterface};
        DispatchProxy<T> proxy = new DispatchProxy<T>(targetInstances,defaultTarget,handler);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }
}

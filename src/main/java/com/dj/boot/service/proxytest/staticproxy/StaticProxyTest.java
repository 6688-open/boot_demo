package com.dj.boot.service.proxytest.staticproxy;

/**
 * 静态代理很好的诠释了代理设计模式，
 * 代理模式最主要的就是有一个公共接口（Cat），一个委托类（Lion），一个代理类（FeederProxy）
 * 代理类持有委托类的实例，代为执行具体类实例方法。
 * 上面说到，代理模式就是在访问实际对象时引入一定程度的间接性，
 * 因为这种间接性，可以附加多种用途。这里的间接性就是指客户端不直接调用实际对象的方法，
 * 客户端依赖公共接口并使用代理类。 那么我们在代理过程中就可以加上一些其他用途。
 * 就这个例子来说在 eatFood方法调用中，
 * 代理类在调用具体实现类之前添加System.out.println("proxy Lion exec eatFood ");语句
 * 就是添加间接性带来的收益。代理类存在的意义是为了增加一些公共的逻辑代码。
 */
public class StaticProxyTest {

    public static void main(String[] args) {
        Lion lion = new Lion();
        lion.setName("狮子 小王");
        lion.setRunningSpeed(100);        /**
         * new 静态代理类，静态代理类在编译前已经创建好了，和动态代理的最大区别点
         */
        Cat proxy = new FeederProxy(lion);
        System.out.println(Thread.currentThread().getName()+" -- " + proxy.eatFood("水牛"));
        proxy.running();
    }
}

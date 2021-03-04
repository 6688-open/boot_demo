package com.dj.boot.test.single;

public class SingletonObject4 {
    /**
     * 上面的构造函数SingletonObject4()，我们编写的顺序是1、2、3，JVM 会对它进行指令重排序，所以执行顺序可能是3、1、2，也可能是2、3、1，不管是那种执行顺序，JVM 最后都会保证所以实例都完成实例化。
     * 如果构造函数中操作比较多时，为了提升效率，JVM 会在构造函数里面的属性未全部完成实例化时，就返回对象。
     * 双重检测锁出现空指针问题的原因就是出现在这里，当某个线程获取锁进行实例化时，其他线程就直接获取实例使用，由于JVM指令重排序的原因，其他线程获取的对象也许不是一个完整的对象，所以在使用实例的时候就会出现空指针异常问题。
     要解决双重检查锁模式带来空指针异常的问题，只需要使用volatile关键字，volatile关键字严格遵循happens-before原则，即在读操作前，写操作必须全部完成。添加volatile关键字之后的单例模式代码：
     */
    // 添加volatile关键字
    private static volatile SingletonObject4 instance;

    private SingletonObject4(){

    }

    public static SingletonObject4 getInstance(){

        // 第一次判断，如果这里为空，不进入抢锁阶段，直接返回实例
        if (instance == null)
            synchronized (SingletonObject4.class){
                // 抢到锁之后再次判断是否为空
                if (instance == null){
                    instance = new SingletonObject4();
                }
            }

        return instance;
    }




}

package com.dj.boot.juc.callable;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 死锁
 *           Java线程死锁是一个经典的多线程问题，因为不同的线程都在等待那些根本不可能被释放的锁，
 *           从而导致所有的工作都无法完成。
 * 死锁的原因
 *              1、 调用了wait()，却没有线程对其解锁notify()/notifyAll()，或者解锁操作先于wait()执行。
 *              2、 Synchronized的滥用，也会造成死锁。假设A、B两个线程，A和B必须同时获得O1和O2才能继续执行。
 *              假如线程A获得了O1，而线程B获得了O2。线程A就会进入阻塞状态来等待获得O2，
 *              而线程B则阻塞来等待O1相互等待就造成了死锁。
 * 避免死锁
 *             1、 尽量使用tryLock(long timeout, TimeUnit unit)的方(ReentrantLock、ReentrantReadWriteLock)，wait(long timeout)设置超时时间，超时可以退出防止死锁。
 *             2、 尽量使用java.util.concurrent包的并发类代替手写控制并发，比较常用的是ConcurrentHashMap、ConcurrentLinkedQueue、AtomicBoolean等等，实际应用中java.util.concurrent.atomic十分有用，简单方便且效率比使用Lock更高。
 *             3、 尽量降低锁的使用粒度，尽量不要几个功能用同一把锁。
 *             4、 尽量减少同步的代码块。
 * 非线程安全
 * 非线程安全的问题主要产生于并发情况下线程对全局变量的操作，导致数据混乱的情况。
 *
 *  Thead 没有callable的构造方法   有runable 的构造方法
 *
 *  所以 runable 的子类 FutureTask 有callanle 的构造方法
 *
 *  callable  抛异常  有返回值
 *
 *  实现多线程
 *  1  继承 Thread 类
 *  2  实现 Runanle 接口
 *  3  实现Callable 接口
 *  4  线程池
 *
 *
 * Integer ret = futureTask.get(); 放在最后
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //callable 接口
        FutureTask<Integer> futureTask = new FutureTask(new MyThread());
        //一个futureTask   两个线程 调用  只打印一次callable method start
        //第一个线程执行完 第二个线程复用结果
        new Thread( futureTask, "A").start();
        new Thread( futureTask, "B").start();
        Integer ret = futureTask.get();
        System.out.println(ret);



        //Runable 接口  有返回值
        FutureTask futureTask1 = new FutureTask(new MyThread01(), "123");
        new Thread( futureTask1, "B").start();

//        while (!futureTask1.isDone()){
//
//        }
        //一般放到最后  否则会导致阻塞
        Object o = futureTask1.get();
        System.out.println(o);

    }
}


class MyThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+ "******* callable method start");
        return 1024;
    }
}

class MyThread01 implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+ "******* Runnable method start");
    }
}

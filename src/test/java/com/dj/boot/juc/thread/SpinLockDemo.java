package com.dj.boot.juc.thread;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 * 1 尝试获取锁的线程不会立即阻塞
 * 2 而是采用循环的方式去尝试获取锁
 * 3 好处 减少线程上下文切换的消耗
 * 4 缺点 循环会消耗CPU
 */
public class SpinLockDemo {
    //源子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock () {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t  come in");
        while (!atomicReference.compareAndSet(null, thread)){

        }
    }


    public void myUnLock () {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName()+"\t  myUnLock");
    }


    public static void main(String[] args) {

        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread( () -> {
            spinLockDemo.myLock();
            //暂停一会线程
            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) { e.printStackTrace();}
            spinLockDemo.myUnLock();
        }, "AA").start();


        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) { e.printStackTrace();}



        new Thread( () -> {
            spinLockDemo.myLock();
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) { e.printStackTrace();}
            spinLockDemo.myUnLock();
        }, "BB").start();

    }
}

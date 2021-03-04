package com.dj.boot.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference  = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {

        System.out.println("=======以下是ABA问题的产生========");
        new Thread( () -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "T1").start();

        new Thread( () -> {
            //暂停一秒  保证T1线程完成了ABA操作
            try {TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(atomicReference.compareAndSet(100, 2019) +"\t" + atomicReference.get());
        }, "T2").start();



        //暂停一会  保证T1 T2 彻底完成
        try {TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("=======以下是ABA问题的解决========");


        new Thread( () -> {
            //先拿到版本号 初始版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t 第1次版本号： " + stamp);

            //暂停1秒 T3 T4 拿到的版本号一样
            try {TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

            atomicStampedReference.compareAndSet(100,101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t 第2次版本号： " + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t 第3次版本号： " + atomicStampedReference.getStamp());

        }, "T3").start();


        new Thread( () -> {
            //先拿到版本号 初始版本号   1
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t 第一次版本号： " + stamp);

            //暂停3秒  保证T3完成一次ABA操作
            try {TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp+ 1);
            System.out.println(Thread.currentThread().getName()+ "\t修改结果 ：" + result +"\t当前实际版本号： " + atomicStampedReference.getStamp() +"\t实际最新值： "+ atomicStampedReference.getReference());
        }, "T4").start();

    }
}

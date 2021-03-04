package com.dj.boot.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS ( compareAndSet)
 *  比较并交换
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        //将期望值和主物理内存值进行比较  相同修改 并刷新主存
        //不相等 本次修改失败 从新读取主内存最新的值
        System.out.println( atomicInteger.compareAndSet(5, 20));
        System.out.println( atomicInteger.compareAndSet(5, 30));


        atomicInteger.getAndIncrement();
    }
}

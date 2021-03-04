package com.dj.boot.juc.thread;


import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *  多个线程  多个资源
 *  多个共享资源互斥
 *  并发线程数的控制
 *
 *   并发量大   限流
 *
 * 信号标  Semaphore  英 /ˈseməfɔː(r)
 *
 * 在信号量上 定义两种操作
 *  acquire （获取）   当一个线程调用acquire 操作时 他要么通过成功获取信号量 （信号量减 1 ）
 *                    要么一致等待下去 直到有线程释放信号量 或超时
 *
 *  release 释放      将信号量的值加1  然后唤醒等待的线程
 *
 *  信号量的主要目的 ：
 *                1  多个共享资源的互斥使用
 *                2 用于并发线程数的控制
 *
 *
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //如果 permits 为 1 相当于 多个线程 一个资源  这时 Semaphore 就相当于synchronized
        //例如 某个线程抢到一个资源 让这个线程占用这个资源10秒钟
        Semaphore semaphore = new Semaphore(3); //3个车位   3个资源  信号量 3

        for (int i = 1; i <= 10; i++) {

            new Thread( () -> {
                try {
                    // 获取  当一个线程调用acquire 操作时 他要么通过成功获取信号量 （信号量减 1 ）
                    semaphore.acquire();
                    //semaphore.acquire(2); //从该信号量获取给定数量的许可证，阻止直到所有可用
                    System.out.println(Thread.currentThread().getName() + "\t 抢占到了2车位");
                    //暂停一会  也就是让这个线程 占用资源3秒钟
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "\t 停占3秒  离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //  释放 将信号量的值加1  然后唤醒等待的线程
                    semaphore.release();
                    //semaphore.release(2); 释放给定数量的许可证，将其返回到信号量。
                }

            }, String.valueOf(i)).start();
        }
        


        
    }
}

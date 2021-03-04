package com.dj.boot.juc.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Java中关于线程的计数器   与CountDownLatch相反
 *
 * 当线程数是7个的时候 开始执行
 *  让一组线程达到一个屏障（同步点）时被阻塞 知道最后一个达到屏障时 屏障才会打开
 *  所有被拦截的线程才会继续干活
 *  线程进入屏障通过 CyclicBarrier的 await（）方法
 *
 *  人到齐了才开会
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {

        // CyclicBarrier(int parties, Runnable barrierAction)
        // CyclicBarrier(int parties)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, ()-> System.out.println("等7个线程之后 才开始 打印这句话"));

        for (int i = 1; i <= 10; i++) {
            final int tempInt = i;
            new Thread( () -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "这是第\t" + tempInt + "\t 个线程");
                try {
                    //线程进来 计数  当执行的线程个数  cyclicBarrier线程被阻塞
                    //当数量是7个的时候 被唤醒
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}

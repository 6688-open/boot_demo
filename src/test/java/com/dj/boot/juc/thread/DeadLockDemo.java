package com.dj.boot.juc.thread;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * 两个线程尝试获取其他资源的锁 而每个线程又陷入无限等待其他锁的释放除非进程中断 否则是死锁
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA ="lockA";
        String lockB ="lockB";

        new Thread(new HoldLockDemo(lockA, lockB), "AAA").start();
        new Thread(new HoldLockDemo(lockB, lockA), "BBB").start();
    }



}



@Data
class HoldLockDemo implements Runnable {
    private String lockA;
    private String lockB;

    public HoldLockDemo(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有"+ lockA +"尝试获取" + lockB);

            try {TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有"+ lockB +"尝试获取" + lockA);
            }
        }

    }

}

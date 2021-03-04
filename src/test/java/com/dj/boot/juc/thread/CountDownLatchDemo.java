package com.dj.boot.juc.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 提炼方法
 * 快捷键
 * Ctrl + Alt + M
 * 选中待提炼代码 --> 右击 --> Refactor --> Extract --> Method.
 *
 *  CountDownLatch 主要有两个方法  当一个线程或者多个线程调用await方法时 这些线程会阻塞
 *  其他线程调用 countDown 方法时会将计数器减 1    调用 countDown 方法的线程不会阻塞
 *  当计时器的值变为0时 因为await方法阻塞的线程会被唤醒 继续执行
 *
 */
public class CountDownLatchDemo {


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread( () -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "其他同学离开");
                //countDown 方法时会将计数器减 1    调用 countDown 方法的线程不会阻塞
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        // 当计时器的值变为0时 因为await方法阻塞的线程会被唤醒 继续执行
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t" + "班长锁门");
    }

    private static void closeDoor() {
        for (int i = 1; i <= 6; i++) {
            new Thread( () -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "其他同学离开");
            }, String.valueOf(i)).start();
        }

        System.out.println(Thread.currentThread().getName() + "\t" + "班长锁门");
    }
}

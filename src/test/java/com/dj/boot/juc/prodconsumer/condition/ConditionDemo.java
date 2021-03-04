package com.dj.boot.juc.prodconsumer.condition;



import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData {

    //标志位
    private int number = 1 ; // A 1       B 2     C 3
    //锁
    private Lock lock = new ReentrantLock();

    //钥匙
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();



    public void print5 () {
        lock.lock();
        try {
            //判断
            while (number != 1) {
                c1.await();
            }
            //干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t" + i);
            }
            //修改标识位
            number = 2;
            //通知 顺序执行 A 执行完 通知B
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void print10 () {
        lock.lock();
        try {
            //判断
            while (number != 2) {
                c2.await();
            }
            //干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t" + i);
            }
            //修改标识位
            number = 3;
            //通知 顺序执行 A 执行完 通知B
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15 () {
        lock.lock();
        try {
            //判断
            while (number != 3) {
                c3.await();
            }
            //干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t" + i);
            }
            //修改标识位
            number = 1;
            //通知 顺序执行 A 执行完 通知B
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

/**
 *  多线程之间顺序调用  实现 A--> B --> C
 *  三个线程启动  ：
 *  AA 打印5次 BB打印10次  CC 打印15次
 * 接着
 * AA 打印5次 BB打印10次  CC 打印15次
 *  10轮
 *
 *
 *  顺序执行  标志位   等待  唤醒顺序指定的任务
 */
public class ConditionDemo {

    public static void main(String[] args) {

        ShareData shareData = new ShareData();
        new Thread( () -> {
            for (int i = 1; i <= 10; i++) {
                shareData.print5();
            }
        }, "A").start();

        new Thread( () -> {
            for (int i = 1; i <= 10; i++) {
                shareData.print10();
            }
        }, "B").start();


        new Thread( () -> {
            for (int i = 1; i <= 10; i++) {
                shareData.print15();
            }
        }, "C").start();
    }
}

package com.dj.boot.juc.prodconsumer.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**两个线程 实现一个线程加1   一个线程减 1 交替实现
 *
 *
 *  1 高聚低合前提下  线程操作资源类
 *  2 判断 干活 通知
 *  3 防止虚假唤醒
 *          同一时间多个线程进来等待 if 只判断一次    而while 循环再判断
 *  4   synchronized    Lock
 */
class AirCondition {

    private Integer number = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public  void increment () throws Exception{
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            //操作
            number++;
            System.out.println(Thread.currentThread().getName()+"\t" + number);
            //通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    public  void decrement () throws Exception{
        lock.lock();
        try {
            while (number == 0) {
                condition.await();
            }
            //操作
            number--;
            System.out.println(Thread.currentThread().getName()+"\t" + number);
            //通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}





public class ProdConsumerLock {

    public static void main(String[] args) {

        AirCondition airCondition = new AirCondition();


        new Thread( () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    airCondition.increment();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread( () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    airCondition.decrement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();


        new Thread( () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    airCondition.increment();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A1").start();

        new Thread( () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    airCondition.decrement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B1").start();

    }
}

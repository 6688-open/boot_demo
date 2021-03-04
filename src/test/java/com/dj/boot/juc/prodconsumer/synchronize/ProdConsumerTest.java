package com.dj.boot.juc.prodconsumer.synchronize;


/**两个线程 实现一个线程加1   一个线程减 1 交替实现
 *
 *
 *  1 高聚低合前提下  线程操作资源类
 *  2 判断 干活 通知
 *  3 防止虚假唤醒
 *
 *
 *
 *
 *  sleep 是Thread中的方法  wait() 是Object方法
 *
 *  sleep 进入休眠 让出CPU执行权  休眠结束 继续运行  不会释放锁
 *  wait 会释放当前的锁  调用 notify()  notifyAll() 唤醒线程
 */
class AirCondition {

    private Integer number = 0;

    public synchronized void increment () throws Exception{

        //判断  java api 当多线程下 防止虚假唤醒 wait方法应该使用while   循环加判断
        // 为了保证顺利进行 会把之前判断的拉回来重新进行判断

        //线程也可以唤醒，而不会被通知，中断或超时，即所谓的虚假唤醒 。
        // 然这在实践中很少会发生，但应用程序必须通过测试应该使线程被唤醒的条件来防范，并且如果条件不满足则继续等待。
        // 换句话说，等待应该总是出现在循环中，
        while (number != 0) {
            this.wait();  //wait(0); 默认是0 毫秒
        }
        //操作
        number++;
        System.out.println(Thread.currentThread().getName()+"\t" + number);
        //通知
        this.notifyAll();
    }


    public synchronized void decrement () throws Exception{
        //判断
        while (number == 0) {
            this.wait();
        }
        //操作
        number--;
        System.out.println(Thread.currentThread().getName()+"\t" + number);
        //通知
        this.notifyAll();
    }

}


public class ProdConsumerTest {

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

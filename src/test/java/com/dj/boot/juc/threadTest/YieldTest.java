package com.dj.boot.juc.threadTest;

/**
 * 简介        放弃
 *           Thread.yield()方法作用是：暂停当前正在执行的线程对象（及放弃当前拥有的cup资源），
 *           并执行其他线程。yield()做的是让当前运行线程回到可运行状态，以允许具有相同优先级的其
 *           他线程获得运行机会。因此，使用yield()的目的是让相同优先级的线程之间能适当的轮转执行。
 *            但是，实际中无法保证yield()达到让步目的，因为让步的线程还有可能被线程调度程序再次选中。
 *    结论：       
 *    yield()从未导致线程转到等待/睡眠/阻塞状态。在大多数情况下，yield()将导致线程从运行状
 *    态转到可运行状态，但有可能没有效果。





 抢占 join
 */
public class YieldTest {


    public static void main(String[] args) {
        new Thread( () -> {
            long begin = System.currentTimeMillis();
            int count = 0;
            for (int i = 0; i < 6000000; i++) {
                Thread.yield();
                count = count + (i + 1);
            }
            long end = System.currentTimeMillis();
            System.out.println("用时" + (end -begin)+"毫秒");
        
        }, "A").start();
    }
}


package com.dj.boot.jvm.JMM;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 * java 内存模型
 *       可见性 (通知机制)
 *               是指当一个线程修改了某一个共享变量的值，其他线程是否能够立即知道这个修改。
 *               显然，对于串行来说，可见性问题是不存在的。
 *       原子性
 *               是指一个操作是不可中断的。即使是多个线程一起执行的时候，一个操作一旦开始，就不会被其他线程干扰。
 * 　　　　       比如，对于一个静态全局变量int i，两个线程同时对它赋值，线程A给他赋值为1，线程B给他赋值为-1。
 *                那么不管这两个线程以何种方式。何种步调工作，i的值要么是1，要么是-1.线程A和线程B之间是没有干扰的。
 *                这就是原子性的一个特点，不可被中断。
 *       有序性
 *             在并发时，程序的执行可能会出现乱序。给人的直观感觉就是：写在前面的代码，会在后面执行。
 *             有序性问题的原因是因为程序在执行时，可能会进行指令重排，重排后的指令与原指令的顺序未必一致。
 *
 *       volatile
 *          1 保证共享变量的可见性
     *             如果一个变量被volatile所修饰的话，在每次数据变化之后，其值都会被强制刷入主存。
     *             而其他处理器的缓存由于遵守了缓存一致性协议，也会把这个变量的值从主存加载到自己的缓存中。
     *             这就保证了一个volatile在并发编程中，其值在多个缓存中是可见的。
 *
 *                  使用了volatile关键字修改的共享变量，当线程修改了共享变量之后，会立马刷新到主内存中，
 *                  并且会使其他线程缓存了该地址的数据失效，这就保证了线程之间共享变量的可见性。
 *            缓存一致性协议 ：
 *                 每个处理器通过嗅探在总线上传播的数据来检查自己缓存的值是不是过期了，
 *                 当处理器发现自己缓存行对应的内存地址被修改，就会将当前处理器的缓存行设置成无效状态，
 *                 当处理器要对这个数据进行修改操作的时候，会强制重新从系统内存里把数据读到处理器缓存里。
 *
 *          2  防止指令重排序
 *                 volatile防止指令重排序是通过内存屏障来实现的
 *
 *          3 不能保证原子性
 *
 * JMM 本身不存在 是一组规范 通过规范定义程序中的变量（实例字段 静态字段 构成数组对象的元素） 的访问方式
 *
 *   由于JVM 运行程序的实体是线程，而每个线程创建时JVM都会为他创建一个工作内存
 *   工作内存是每个线程的私有数据区域 而java内存模型规定所有的变量都存储在主内存
 *   主内存是共享区域内存 所有线程都可以访问
 *   但对变量的操作（读取赋值） 必须要在 工作内存中进行 首先要将变量从主内存拷贝到线程自己的工作空间
 *   然后对变量操作， 操作完成 在写回主存  不能直接操作主存
 *   各个线程中的工作内存存储的主内存的变量副本
 *   不同的线程无法访问对方的工作内存 线程之间的通信必须由主存完成
 */


class  MyNumber {

    volatile int number = 0;
    public void addTo1205 () {
        this.number = 1205;
    }

    public  void addPlusPlus () {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void atomicIn () {
        atomicInteger.getAndIncrement();
    }
}


/**1 验证volatile的可见性
 *
 *
 * 2 验证volatile不保证原子性
 *
 *    N++  get ++ put 3步
 *
 *    解决原子性
 *    加synchronized
 *    atomicInteger
 *
 *
 */
public class JMMTest {


    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();

        for (int i = 1; i <= 20; i++) {

            new Thread( () -> {
                for (int j = 1; j <= 1000; j++) {
                    myNumber.addPlusPlus();
                    myNumber.atomicIn();
                }

            }, String.valueOf(i)).start();
        }

        //等20个线程执行完 main打出最终的结果  20000
        //暂停一会
        //TimeUnit.SECONDS.sleep(5);
        while (Thread.activeCount() > 2) {  //main线程  gc线程
            Thread.yield(); //暂停
        }
        System.out.println(Thread.currentThread().getName() + "Value :" + myNumber.number);
        System.out.println(Thread.currentThread().getName() + "atomicIn Value :" + myNumber.atomicInteger);

    }


    /**
     * volatile 可以保证可见性 及时通知其他线程 主物理内存已经被修改
     */
    private static void seeOkByVolatile() {
        MyNumber myNumber = new MyNumber();

        new Thread( () -> {
            try {
                Thread.sleep(3000);
                myNumber.addTo1205();
                System.out.println(Thread.currentThread().getName()+"\t" + "update number" + "\t" + myNumber.number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();


        while (myNumber.number == 10) {
            // 当A线程改1205时 需要有一种机制 告诉main线程number已经修改了1205 写回了主内存 跳出while
        }

        System.out.println(Thread.currentThread().getName() +"\t" + "while is over" + myNumber.number);
    }
}

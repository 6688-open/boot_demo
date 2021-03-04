package com.dj.boot.juc.prodconsumer.blockingQueue;


import com.dj.boot.common.util.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource{
    private volatile boolean flag = true; //默认开启  进行生产+消费
    private AtomicInteger  atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws Exception{

        String data = null;
        boolean offer;
        while (flag) {
            data = atomicInteger.incrementAndGet() + "";
            offer = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (offer) {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "success");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "failed");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\tflag 为false   不再生产");

    }
    public void myConsumer() throws Exception{

        String poll = null;
        while (flag) {
            poll = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (StringUtils.isNullOrEmpty(poll)) {
                flag = false;
                System.out.println(Thread.currentThread().getName() + "\t超过2秒没有获取 消费退出");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t消费队列" + poll + "success");
        }


    }


    public void stop () throws Exception {
        this.flag = false;
    }
}


/**
 *  volatile/CAS/ AtomicInteger/ BlockingQueue/线程交互/ 原子引用
 */
public class BlockingQueueTest {
    public static void main(String[] args) throws Exception {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread( () -> {
            System.out.println(Thread.currentThread().getName() + "\t生产线程启动");

            try {
                myResource.myProd();
                System.out.println();
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, "Prod").start();


        new Thread( () -> {
            System.out.println(Thread.currentThread().getName() + "\t消费线程启动");
            System.out.println();
            System.out.println();

            try {
                myResource.myConsumer();
                System.out.println();
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, "Consumer").start();


        TimeUnit.SECONDS.sleep(5);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("5秒钟时间到  大老板main线程叫停 活动结束");

        myResource.stop();
    }
}

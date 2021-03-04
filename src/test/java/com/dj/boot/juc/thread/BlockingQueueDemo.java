package com.dj.boot.juc.thread;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 *  当队列是空的  从队列获取元素 将会被阻塞   直到其他的线程往空的队列里插入新的元素
 *  当队列是满的  向队列添加元素 将会被 阻塞   直到其他的线程消费一个或全部的元素
 *
 *
 *  阻塞 在某些情况下挂起线程 就是阻塞  一旦满足条件 被挂起的线程自动唤醒
 *
 *  好处
 *    不需要关心什么时候阻塞线程 什么时候唤醒线程 都交给BlockingQuene
 *
 *    ArrayBlockingQueue   基于数组结构 有界 有长度 的阻塞队列
 *    LinkedBlockingQueue  基于链表结构的 有界  默认值为integer。MAX_VALUE
 *    SynchronousQueue    不存储元素的队列  即单个元素的队列
 *
 *    DelayQueue             使用优先级队列实现的延迟无界的阻塞队列
 *    LinkedBlockingDeque   链表组成的双向阻塞队列
 *    LinkedTransferQueue   链表组成的无界阻塞队列
 *    priorityBlockingQueue  支持优先级排序的无界阻塞队列
 *
 *            抛出异常       特殊值      阻塞         超时
 *    插入   add()           offer(e)    put（e）     offer(e, time, unit)
 *    移除   remove()         poll（）   take()         poll（time, unit）
 *    检查   element()        peek()    ...            ...
 *
 *
 *    满时   添加 add()   返回 true/false        抛出 java.lang.IllegalStateException: Queue full
 *    空时   移除 remove()  返回数据元素   抛出  java.util.NoSuchElementException
 *           检查 element()        Exception in thread "main" java.util.NoSuchElementException
 *
 *  特殊值     插入  成功 true 失败 false
 *             移除  成功返回元素  没有就返回null
 *             peek
 *
 *
 *   一直阻塞     满时   put()   一直阻塞
 *                空时   take()   一直阻塞
 *
 *   超时退出   阻塞一段时间 就推出
 *
 */
public class BlockingQueueDemo {


    public static void main(String[] args) throws Exception {
       // BlockingQueneTest();
        //同步队列
        SynchronousQueueTest();


    }

    private static void BlockingQueneTest() throws InterruptedException {
        //有界 有长度 的阻塞队列   指定 3 只能放3个元素
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);


        /**
         * 抛出异常 add  remove  element()
         */
        // add 超出范围 java.lang.IllegalStateException: Queue full
        //boolean a = blockingQueue.add("a");
        /*System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));*/

        //String s = blockingQueue.remove();
        //先进先出  移除也是有顺序
        // 空时 remove  java.util.NoSuchElementException
        /*System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());*/

        //检查是否存在
        /*blockingQueue.element();*/


        /**
         * 特殊值   offer(e)   poll（）  peek()
         */
        // 添加offer(e) 返回true false
        /*System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));*/

        //移除 poll() 有返回移除的对象  没有返回null
       /* System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());*/


        // 检查 peek ()  true  /false
        /* System.out.println(blockingQueue.peek());*/

        /**
         *  阻塞  一直阻塞
         *   put(e) 添加  take() 移除
         */

        //  put 无返回值 满时 一直阻塞
//        blockingQueue.put("a");
//        blockingQueue.put("a");
//        blockingQueue.put("a");

        //take() 移除  有就删除 返回元素 没有就一直阻塞
        //String take = blockingQueue.take();
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());


        /**
         *  添加 offer    移除poll 超时机制
         */
        System.out.println(blockingQueue.offer("a",3l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b",3l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c",3l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("d",3l, TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(3l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3l, TimeUnit.SECONDS));
    }


    public static void SynchronousQueueTest(){
        //同步队列
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread( () -> {
            try {
                System.out.println(Thread.currentThread().getName()+"\t put  1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+"\t put  2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+"\t put  3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAAA").start();



        new Thread( () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+ "\t" +blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+ "\t" +blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+ "\t" +blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBBB").start();

    }



}

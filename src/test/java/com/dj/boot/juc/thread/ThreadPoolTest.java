package com.dj.boot.juc.thread;

import java.util.concurrent.*;

/**
 * 线程池的优势 ：
 *      控制线程数量 处理过程将任务放入队列 如果线程数量超过最大线程数 超出的线程排队等待
 *
 *  特点 ： 线程复用 控制最大并发数 管理线程
 *
 *     1 降低资源消耗 复用已经创建的线程 避免频繁的创建销毁
 *     2 提高响应速度  任务来的时候  直接从线程池获取  不需要创建线程
 *     3 提高线程可管理型
 *
 *   七大参数
 *    int corePoolSize,  核心线程数   线程池中的常驻核心线程数
 *    int maximumPoolSize,  最大线程数  线程池能同时容纳的最大线程数 此值必须大于等于1
 *    long keepAliveTime,   保持时间 当前线程池数量超过corePoolSize时   非核心线程 空闲的时间   达到这个时间非核心线程会被销毁  只剩下核心线程数
 *    TimeUnit unit,         keepAliveTime 时间单位
 *    BlockingQueue<Runnable> workQueue, 任务队列 被提交还没执行的任务   仅当队列已满时才会创建新线程
 *    ThreadFactory threadFactory,   线程工厂 用于创建线程
 *    RejectedExecutionHandler handler  拒绝策略  当队列满了 工作线程大于线程池最大线程数 来拒绝
 *
 *
 *    提交任务  线程数超过核心线程数  然后放进队列
 *
 *    队列满了 创建新线程 到最大线程数  超过最大线程数  拒绝策略
 *
 *
 *    拒绝策略
 *       队列满了 放不下任务 达到最大线程数  不会在创建线程  拒绝机制
 *      AbortPolicy() 默认的拒绝策略  丢弃并抛弃异常
 *      DiscardPolicy() 丢弃但不抛出异常
 *      CallerRunsPolicy()  由主线程执行
 *      DiscardOldestPolicy() 丢弃队列最前面的任务 重新尝试执行
 *
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        /**
         * Executors 创建的对象 请求长度Integer.MAX_VALUE  可能堆积大量请求  导致OOM
         */
        //默认线程池
        //最大线程数 分为 CPU 密集型  IO 密集型
       // CPU密集型：核心线程数 = CPU核数 + 1
       //IO密集型：核心线程数 = CPU核数 * 2  核心线程数 = CPU核数 / （1-阻塞系数）
        final int count = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                3,
                count + 1,
                3,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        try {
            for (int i = 1; i <= 10; i++) {
               // TimeUnit.MILLISECONDS.sleep(200);
                threadPool.execute( () -> {
                    System.out.println(Thread.currentThread().getName() + "\t" + "办理业务");

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void pool() {
        //定长线程池  可控制并发数 其他的任务等待  实际底层创建了一个ThreadPoolExecutor
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //单线程的线程池  只有一个工作线程   实际底层创建了一个ThreadPoolExecutor
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //创建可缓存的线程池  任务过多 创建线程 若线程池大于处理的任务 回收线程   实际底层创建了一个ThreadPoolExecutor
        // ExecutorService threadPool = Executors.newCachedThreadPool();
        //定长线程池  支持周期 定时
        //ExecutorService threadPool = Executors.newScheduledThreadPool(3);
        try {
            for (int i = 1; i <= 10; i++) {
                TimeUnit.MILLISECONDS.sleep(200);
                threadPool.execute( () -> {
                    System.out.println(Thread.currentThread().getName() + "\t" + "办理业务");

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}

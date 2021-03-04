package com.dj.boot.common.threadpoolutil;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 核心线程数  任务队列  最大线程数  拒绝策略
 * @author ext.wangjia
 */
public class ThreadPoolUtils {
    /**
     * 根据cpu的数量动态的配置核心线程数和最大线程数
     */
    @SuppressWarnings("unused")
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数 = CPU核心数 + 1
     */
    //private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 3;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 3;
    /**
     * 非核心线程闲置时超时10s
     */
    private static final long KEEP_ALIVE_TIME = 10;
    /**
     * 时间单位 秒
     */
    private static TimeUnit unit = TimeUnit.SECONDS;
    /**
     * 任务队列
     */
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(20);
    /**
     * 线程工厂 当任务大于核心线程数 由ThreadFactory 创建线程  受最大线程数限制
     */
    private static  ThreadFactory threadFactory = new NameTreadFactory();
    /**
     * 拒绝策略   自定义拒绝策略
     */
    private static RejectedExecutionHandler handler = new MyIgnorePolicy();
    //private static RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

    /**
     * 自定义线程工厂
     */
    private static ThreadFactory factory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "SyncSave:" + integer.getAndIncrement());
        }
    };

    private static class ThreadPool {
        private static final ThreadPoolExecutor POOL_ = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, unit, workQueue, threadFactory, handler);
    }
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        // 预启动所有核心线程
        /*ThreadPoolUtils.ThreadPool.POOL_.prestartAllCoreThreads();*/
        return ThreadPoolUtils.ThreadPool.POOL_;
    }


    /**
     * 自定义线程工厂
     */
    static class NameTreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    /**
     * 自定义拒绝策略
     */
    public static class MyIgnorePolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            // 可做日志记录等
            System.err.println( r.toString() + " rejected");
//          System.out.println("completedTaskCount: " + e.getCompletedTaskCount());
        }
    }

}

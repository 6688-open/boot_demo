package com.dj.boot.common.threadpoolutil;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ext.wangjia
 */
public class ThreadUtils2 {
    private static final int CORE_POOL_SIZE = 23;

    private static final int MAX_POOL_SIZE = 23;

    private static final long KEEP_ALIVE_TIME = 35;

    /**
     * 自定义线程工厂  创建了一个“SyncSave "线程
     */
    private static ThreadFactory factory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "SyncSave:" + integer.getAndIncrement());
        }
    };

    private static class SingleThreadPool {
        private static final ThreadPoolExecutor POOL_ = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),factory);
    }
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return ThreadUtils2.SingleThreadPool.POOL_;
    }

}

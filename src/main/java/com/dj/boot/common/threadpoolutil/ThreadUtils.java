package com.dj.boot.common.threadpoolutil;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadPoolUtils
 *
 * @author: wj
 * @version: 2019-09-20 11:42
 */
public class ThreadUtils {
    /**
     * 根据cpu的数量动态的配置核心线程数和最大线程数
     */
    @SuppressWarnings("unused")
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数 = CPU核心数 + 1
     */
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    /**
     * 线程池最大线程数 = CPU核心数  + 1
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT + 1;
    /**
     * 非核心线程闲置时超时1s
     */
    private static final int KEEP_ALIVE = 1;


    private static ThreadFactory factory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "feapiThread:" + integer.getAndIncrement());
        }
    };

    /**
     * ExecutorServiceHolder
     */
    private static class ExecutorServiceHolder {

        /**
         * Default ThreadPoolExecutor object
         */
        public static ExecutorService executorService = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                factory,
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * Get default ThreadPoolExecutor
     *
     * @return ExecutorService
     */
    public static ExecutorService threadPoolExecutor() {
        return ThreadUtils.ExecutorServiceHolder.executorService;
    }
}

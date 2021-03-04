package com.dj.boot.juc.async;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 异步回调
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //voidMethod();

        // 重新开启一个线程 有返回值     Supplier<U> supplier  无参  有返回值
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 有返回值");
            //int age = 10/0;
            return 1024;
        });

        //返回值                          BiConsumer 两个参数 无返回值
        //  成功时  whenComplete   异常时
        CompletableFuture<Integer> future = completableFuture.whenComplete((t, u) -> {
            //返回值
            System.out.println("********t: " + t);
            //异常
            System.out.println("********u: " + u);
        }).exceptionally(e -> {
            System.out.println("exceptionally" + e.getMessage());
            return 1024;
        });
        //返回值
        future.get();
        System.out.println(future.get());


    }

    private static void voidMethod() throws InterruptedException, ExecutionException {
        System.out.println("开始进入main  方法");

        // 重新开启一个线程  且没有返回值
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(Thread.currentThread().getName() + "\t" + "没有返回值");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        System.out.println(111);

        // main 主线程会等待 completableFuture这个线程
        completableFuture.get();

        System.out.println(222);
    }
}

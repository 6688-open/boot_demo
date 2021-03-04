package com.dj.boot.juc.thread;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  读写锁 ReadWriteLock
 *
 *    多个线程同时读一个资源类的时候没有问题  为了满足并发 读取共享资源可以同时满足
 *     如果有一个线程区写共享资源 就不应该让其他的线程资源进行读和写
 *
 *     读---读 可共存
 *     读---写  不可共存
 *     写---写  不可共存
 *
 *
 */
class MyCache {

    private volatile Map<String, Object> map =  new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put (String k, Object v) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 开始写入数据" + "\t" + k );
            //暂停一会
            try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
            map.put(k, v);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成" );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get (String k) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 开始读取数据" );
            //暂停一会
            try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
            Object o = map.get(k);
            System.out.println(Thread.currentThread().getName() + "\t 读取数据完成" +"\t" + o);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

}

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread( () -> {
                myCache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread( () -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }


    }
}

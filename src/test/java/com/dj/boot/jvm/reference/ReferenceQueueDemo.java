package com.dj.boot.jvm.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * gc之后  对象被回收  放到了引用队列  后续处理
 */
public class ReferenceQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(o1, referenceQueue);


        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());



        System.out.println("==========================");
        o1 = null;
        System.gc();
        TimeUnit.SECONDS.sleep(1);


        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());
    }
}

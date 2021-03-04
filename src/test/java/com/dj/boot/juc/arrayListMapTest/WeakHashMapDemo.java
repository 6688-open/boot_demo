package com.dj.boot.juc.arrayListMapTest;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 和HashMap一样，WeakHashMap 也是一个散列表，它存储的内容也是键值对(key-value)映射，而且键和值都可以是null。
 *    不过WeakHashMap的键是“弱键”。在 WeakHashMap 中，当某个键不再正常使用时，会被从WeakHashMap中被自动移除。
 *    更精确地说，对于一个给定的键，其映射的存在并不阻止垃圾回收器对该键的丢弃，
 *    这就使该键成为可终止的，被终止，然后被回收。某个键被终止时，它对应的键值对也就从映射中有效地移除了。
 *    这个“弱键”的原理呢？大致上就是，通过WeakReference和ReferenceQueue实现的。
 *    WeakHashMap的key是“弱键”，即是WeakReference类型的；ReferenceQueue是一个队列，它会保存被GC回收的“弱键”。
 *    实现步骤是：
 *     (01) 新建WeakHashMap，将“键值对”添加到WeakHashMap中。
 *           实际上，WeakHashMap是通过数组table保存Entry(键值对)；
 *           每一个Entry实际上是一个单向链表，即Entry是键值对链表。
 *     (02) 当某“弱键”不再被其它对象引用，并被GC回收时。在GC回收该“弱键”时，
 *          这个“弱键”也同时会被添加到ReferenceQueue(queue)队列中。
 *     (03) 当下一次我们需要操作WeakHashMap时，会先同步table和queue。
 *           table中保存了全部的键值对，而queue中保存被GC回收的键值对；
 *           同步它们，就是删除table中被GC回收的键值对。
 *    这就是“弱键”如何被自动从WeakHashMap中删除的步骤了。
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {

        myMap();
        System.out.println("=========================");
        myWeakMap();

    }

    public static void myMap() {
        Map<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "WeakHashMap";

        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map + "\t" + map.size());
    }

    public static void myWeakMap() {
        WeakHashMap<Integer, String> weakHashMap = new WeakHashMap<>();
        Integer key = new Integer(1);
        String value = "WeakHashMap";

        weakHashMap.put(key, value);
        System.out.println(weakHashMap);

        key = null;
        System.out.println(weakHashMap);

        System.gc();
        System.out.println(weakHashMap + "\t" + weakHashMap.size());

    }
}

package com.dj.boot.jvm.reference;

import com.dj.boot.pojo.User;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 强引用（Strong Reference)
 * 软引用（Soft Reference)
 * 弱引用（Weak Reference)
 * 虚引用（Phantom Reference)
 */
public class ReferenceTest {
    public static void main(String[] args) {


        /**
         * 3.1 强引用
         * 强引用类似Object obj = new Object()这类似的引用，
         * 只要强引用在，垃圾搜集器永远不会搜集被引用的对象
         * 。也就是说，宁愿出现内存溢出，也不会回收这些对象。
         */
        User user = new User();


        /**3.2 软引用
         * 软引用是用来描述一些有用 但并不是必需的对象，
         * 在Java中用java.lang.ref.SoftReference类来表示。
         * 对于软引用关联着的对象，只有在内存不足的时候JVM才会回收该对象。
         * 因此，这一点可以很好地用来解决OOM的问题，
         * 并且这个特性很适合用来实现缓存：比如网页缓存、图片缓存等
         */
        SoftReference<User> softReference = new SoftReference<>(new User());
        //get方法用来获取与软引用关联的对象的引用，如果该对象被回收了，则返回null。
        User user1 = softReference.get();
        user1.setUserName("eee");


        /**
         *  3.3 弱引用
         * 弱引用也是用来描述非必需对象的，当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。
         * 在java中，用java.lang.ref.WeakReference类来表示。下面是使用示例：
         */

        WeakReference<User>  userWeakReference = new WeakReference<>(new User());
        //get方法用来获取与软引用关联的对象的引用，如果该对象被回收了，则返回null。
        User user2 = userWeakReference.get();


        /**
         * 3.4 虚引用
         * 虚引用和前面的软引用、弱引用不同，它并不影响对象的生命周期。
         * 在java中用java.lang.ref.PhantomReference类表示。
         * 如果一个对象与虚引用关联，在任何时候都可能被垃圾回收器回收。
         * 要注意的是，虚引用必须和引用队列关联使用，
         * 当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会把这个虚引用加入到与之关联的引用队列中。
         * 程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。
         * 如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。
         */

        ReferenceQueue<User> queue = new ReferenceQueue<>();
        PhantomReference<User> pr = new PhantomReference<>(new User(), queue);
        //虚引用中有一个构造函数，可以看出，其必须和一个引用队列一起存在。get()方法永远返回null，因为虚引用永远不可达。
        User user3 = pr.get();


    }



}

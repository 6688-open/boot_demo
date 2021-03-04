package com.dj.boot.test.iotest;

import org.junit.Test;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.test.iotest
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-09-18-10-33
 */
public class IntBufferTest {


    /**
     * 缓冲区（Buffer）
     * 缓冲区是存储数据的区域，在 Java 中，缓冲区就是数组，为了可以操作不同数据类型的数据，
     * Java 提供了许多不同类型的缓冲区，除了布尔类型以外，其它基本数据类型都有对应的缓冲区数组对象。
     *
     * 为什么没有布尔类型的缓冲区呢？
     *
     * 在 Java 中，boolean 类型数据只占用 1 bit，而在 IO 传输过程中，
     * 都是以字节为单位进行传输的，所以 boolean 的 1 bit 完全可以使用 byte 类型的某一位，
     * 或者 int 类型的某一位来表示，没有必要为了这 1 bit 而专门提供多一个缓冲区。
     *
     * 缓冲区	解释
     * ByteBuffer	存储字节数据的缓冲区
     * CharBuffer	存储字符数据的缓冲区
     * ShortBuffer	存储短整型数据的缓冲区
     * IntBuffer	存储整型数据的缓冲区
     * LongBuffer	存储长整型数据的缓冲区
     * FloatBuffer	存储单精度浮点型数据的缓冲区
     * DoubleBuffer	存储双精度浮点型数据的缓冲区
     * 分配一个缓冲区的方式都高度一致：使用allocate(int capacity)方法。
     *
     * 例如需要分配一个 1024 大小的字节数组，代码就是下面这样子。
     *
     * ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
     * 缓冲区读写数据的两个核心方法：
     *
     * put()：将数据写入到缓冲区中
     * get()：从缓冲区中读取数据
     * 缓冲区的重要属性：
     *
     * capacity：缓冲区中最大存储数据的容量，一旦声明则无法改变
     *
     * limit：表示缓冲区中可以操作数据的大小，limit 之后的数据无法进行读写。必须满足 limit <= capacity
     *
     * position：当前缓冲区中正在操作数据的下标位置，必须满足 position <= limit
     *
     * mark：标记位置，调用 reset() 将 position 位置调整到 mark 属性指向的下标位置，实现多次读取数据
     *
     * 缓冲区为高效读写数据而提供的其它辅助方法：
     *
     * flip()：可以实现读写模式的切换，我们可以看看里面的源码
     * public final Buffer flip() {
     *     limit = position;
     *     position = 0;
     *     mark = -1;
     *     return this;
     * }

     * 调用 flip() 会将可操作的大小 limit 设置为当前写的位置，操作数据的起始位置 position 设置为 0，即从头开始读取数据。
     *
     * rewind()：可以将 position 位置设置为 0，再次读取缓冲区中的数据
     * clear()：清空整个缓冲区，它会将 position 设置为 0，limit 设置为 capacity，可以写整个缓冲区
     * 更多的方法可以去查阅 API 文档，本文碍于篇幅原因就不贴出其它方法了，主要是要理解缓冲区的作用
     *
     *
     * 执行结果如下图所示，首先我们往缓冲区中写入 2 个数据，position 在写模式下指向下标 2，
     * 然后调用 flip() 方法切换为读模式，limit 指向下标 2，position 从 0 开始读数据，
     * 读到下标为 2 时发现到达 limit 位置，不可继续读。
     *
     * 整个过程可以用下图来理解，调用 flip() 方法以后，读出数据的同时 position 指针不断往后挪动，
     * 到达 limit 指针的位置时，该次读取操作结束。
     */
    @Test
    public void intBufferTest () {
        // 分配内存大小为11的整型缓存区
        IntBuffer buffer = IntBuffer.allocate(11);
        // 往buffer里写入2个整型数据
        for (int i = 0; i < 2; ++i) {
            int randomNum = new SecureRandom().nextInt();
            buffer.put(randomNum);
        }
        // 将Buffer从写模式切换到读模式
        buffer.flip();
        System.out.println("position >> " + buffer.position()
                + "limit >> " + buffer.limit()
                + "capacity >> " + buffer.capacity());
        // 读取buffer里的数据
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        System.out.println("position >> " + buffer.position()
                + "limit >> " + buffer.limit()
                + "capacity >> " + buffer.capacity());
    }
}

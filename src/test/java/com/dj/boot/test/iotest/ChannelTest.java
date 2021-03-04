package com.dj.boot.test.iotest;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.test.iotest
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-09-18-10-49
 */
public class ChannelTest {


    /**
     * 上面我们介绍过，通道是作为一种连接资源，作用是传输数据，而真正存储数据的是缓冲区，
     * 所以介绍完缓冲区后，我们来学习通道这一块。
     *
     * 通道是可以双向读写的，传统的 BIO 需要使用输入/输出流表示数据的流向，在 NIO 中可以减少通道资源的消耗。
     *
     * 通道类都保存在 java.nio.channels 包下，我们日常用到的几个重要的类有 4 个：
     *
     * IO 通道类型	具体类
     * 文件 IO	FileChannel（用于文件读写、操作文件的通道）
     * TCP 网络 IO	SocketChannel（用于读写数据的 TCP 通道）、ServerSocketChannel（监听客户端的连接）
     * UDP 网络 IO	DatagramChannel（收发 UDP 数据报的通道）
     * 可以通过 getChannel() 方法获取一个通道，支持获取通道的类如下：
     *
     * 文件 IO：FileInputStream、FileOutputStream、RandomAccessFile
     * TCP 网络 IO：Socket、ServerSocket
     * UDP 网络 IO：DatagramSocket
     * 示例：文件拷贝案例
     *
     * 我们来看一个利用通道拷贝文件的例子，需要下面几个步骤：
     *
     * 打开原文件的输入流通道，将字节数据读入到缓冲区中
     * 打开目的文件的输出流通道，将缓冲区中的数据写到目的地
     * 关闭所有流和通道（重要！）
     * 这是一张小菠萝的照片，它存在于d:\小菠萝\文件夹下，我们将它拷贝到 d:\小菠萝分身\ 文件夹下。
     */

    /** 缓冲区的大小 */
    public static final int SIZE = 1024;

    @Test
    public void channelTest() throws IOException {

        // 打开文件输入流
        FileChannel inChannel = new FileInputStream("d:/小菠萝/小菠萝.jpg").getChannel();
        // 打开文件输出流
        FileChannel outChannel = new FileOutputStream("d:/小菠萝分身/小菠萝-拷贝.jpg").getChannel();
        // 分配 1024 个字节大小的缓冲区
        ByteBuffer dsts = ByteBuffer.allocate(SIZE);
        // 将数据从通道读入缓冲区
        while (inChannel.read(dsts) != -1) {
            // 切换缓冲区的读写模式
            dsts.flip();
            // 将缓冲区的数据通过通道写到目的地
            outChannel.write(dsts);
            // 清空缓冲区，准备下一次读
            dsts.clear();
        }
        inChannel.close();
        outChannel.close();


    }


    /**
     * 选择器（Selectors）
     * 选择器是提升 IO 性能的灵魂之一，它底层利用了多路复用 IO机制，让选择器可以监听多个 IO 连接，
     * 根据 IO 的状态响应到服务器端进行处理。通俗地说：选择器可以监听多个 IO 连接，
     * 而传统的 BIO 每个 IO 连接都需要有一个线程去监听和处理。
     */
}

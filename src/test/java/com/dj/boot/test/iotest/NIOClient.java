package com.dj.boot.test.iotest;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.test.iotest
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-09-18-11-25
 */
public class NIOClient {
    public static final int CAPACITY = 1024;

    public static void main(String[] args) throws Exception {
        ByteBuffer dsts = ByteBuffer.allocate(CAPACITY);
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 3333));
        socketChannel.configureBlocking(false);
        Scanner sc = new Scanner(System.in);
        while (true) {
            String msg = sc.next();
            dsts.put(msg.getBytes());
            dsts.flip();
            socketChannel.write(dsts);
            dsts.clear();
        }
    }
}

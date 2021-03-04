package com.dj.boot.test.trycatchTest;

import org.apache.ibatis.session.SqlSession;
import redis.clients.jedis.Connection;

import java.io.FileInputStream;
import java.io.IOException;

public class TryCatchTest {

    public static void main(String[] args) {
        // 方案一 try-with-resource
        // FileInputStream extends InputStream    InputStream implements Closeable  Closeable extends AutoCloseable
        try (FileInputStream fileInputStream = new FileInputStream("A")) {
            System.out.println("111");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //自动关闭资源
        AutoCloseable autoCloseable = new AutoCloseable() {
            @Override
            public void close() throws Exception {

            }
        };


        Connection connection = new Connection();
        //Connection implements Closeable             Closeable extends AutoCloseable

        SqlSession sqlSession = null;
        // SqlSession  extends Closeable    Closeable extends AutoCloseable


        //方案二   finally 代码块
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("A");
            System.out.println("111");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

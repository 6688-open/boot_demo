package com.dj.boot.juc.prodconsumer.synchronize;

import com.dj.boot.pojo.User;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

public class SyncTest {


    public static void main(String[] args) {
        User user = new User();
        synchronized (user.getClass()){
            System.out.println(Integer.toHexString(user.hashCode()));
            //查看对象内部信息
            System.out.println(ClassLayout.parseInstance(user).toPrintable());
        }


        //查看对象外部信息
        System.out.println(GraphLayout.parseInstance(user).toPrintable());

        //获取对象总大小
        System.out.println("size : " + GraphLayout.parseInstance(user).totalSize());
    }
}

package com.dj.boot.test.single;

public class SingletonObject1 {

    // 利用静态变量来存储唯一实例
    private static final SingletonObject1 instance = new SingletonObject1();

    // 私有化构造函数
    private SingletonObject1(){
        // 里面可能有很多操作
    }

    // 提供公开获取实例接口
    public static SingletonObject1 getInstance(){
        return instance;
    }

}

package com.dj.boot.test.single;

public class SingletonObject6 {
    private SingletonObject6(){

    }
    // 单例持有者
    private static class InstanceHolder{
        private  final static SingletonObject6 instance = new SingletonObject6();

    }

    //
    public static SingletonObject6 getInstance(){
        // 调用内部类属性
        return InstanceHolder.instance;
    }



}

package com.dj.boot.test.initia;

import com.dj.boot.common.util.LogUtils;

/**
 * @ClassName InitiaTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/8 15:08
 * @Version 1.0
 **/
public class SonClass extends FatherClass{
    //静态变量
    public static String staticField = "子类  -------------静态变量";
    //变量
    public String field = "------子类 ----普通变量-------------";

    //静态代码块
    static {
        LogUtils.info(staticField);
        LogUtils.info("----子类 ----   静态代码块");
    }


    //初始化块  非静态代码块
    {
        LogUtils.info(field);
        LogUtils.info("------子类-------非静态代码块 -----------");
    }

    //构造方法

    public SonClass() {
        LogUtils.info("子类======================构造方法");
    }


    public static void main(String[] args) {
        LogUtils.info("子类main方法");
        new SonClass();
    }



    //继承关系
    //父类静态代码块  子类静态代码块    父类非静态代码块  父类构造函数   子类非静态代码块 子类构造函数





}

package com.dj.boot.test.object;

import com.dj.boot.common.util.LogUtils;

import java.lang.reflect.Constructor;

/**
 * @ClassName CreateObject
 * @Description TODO
 * @Author wj
 * @Date 2020/1/8 15:25
 * @Version 1.0
 **/
public class CreateObject {

    /**
     *     使用new关键字
     *     Class对象的newInstance()方法
     *     构造函数对象的newInstance()方法
     *     对象反序列化
     *     Object对象的clone()方法
     *     使用Unsafe类创建对象
     */


    public static void main(String[] args) {
        /**
         * 1 使用new关键字
         */
        ObjectTest objectTest = new ObjectTest();
        //objectTest.getName();
        objectTest.eat();


        /**
         * class的 newInstance()方法
         */
        try {
            String className = "com.dj.boot.Test.object.ObjectTest";
            Class  aClass = Class.forName(className);
            ObjectTest objectTest1 =(ObjectTest) aClass.newInstance();
            objectTest1.eat();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * (3)构造函数的newInstance()方法
         */
        Constructor<ObjectTest> constructors;
        try {
            constructors = ObjectTest.class.getConstructor();
            ObjectTest objectTest1 = constructors.newInstance();
            objectTest1.eat();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 5）clone方式   重写.clone() 方法
         */

        ObjectTest objectTest1 = new ObjectTest();
        objectTest1.setAge(12);
        objectTest1.setName("zs");
        try {
            ObjectTest clone = (ObjectTest)objectTest1.clone();
            LogUtils.info( clone.getName());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


        /**
         * 使用Unsafe类创建对象
         * @return
         */
//        Unsafe unsafe = getUnsafe();
//        try {
//            ObjectTest event = (ObjectTest) unsafe.allocateInstance(ObjectTest.class);
//            LogUtils.info(event.getName());
//            event.eat();
//
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }


    }



//    private static Unsafe getUnsafe(){
//        try {
//            Field field = Unsafe.class.getDeclaredField("theUnsafe");
//            //由于JDK的安全检查耗时较多.所以通过setAccessible(true)的方式关闭安全检查就可以达到提升反射速度的目的
//            field.setAccessible(true);
//            Unsafe unsafe = (Unsafe)field.get(null);
//            return unsafe;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}

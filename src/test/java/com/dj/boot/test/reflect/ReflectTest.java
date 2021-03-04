package com.dj.boot.test.reflect;

import com.dj.boot.common.util.map.BeanMapUtils;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserExtend;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取一个类的class文件对象的三种方式
 *    1.对象获取
 *    2.类名获取
 *    3.Class类的静态方法获取
 */
public class ReflectTest {

    @Test
    public void test() throws Exception {
        //反射获取对象
        getObject();
        //获取属性名数组
        getFiledName();

        ReflectDemo reflectDemo = ReflectDemo.class.newInstance();
        // 反射获取方法
        getMethod(reflectDemo);
        //反射获取属性
        getFiled(reflectDemo);
        //反射回去构造函数
        getConstructor(reflectDemo);

    }

    private void getFiledName() throws Exception {
        List<User> userList = Lists.newArrayList();
        User user1 = new User();
        user1.setUserName("key1");
        user1.setPassword("value1");
        User user2 = new User();
        user2.setUserName("key2");
        user2.setPassword("value2");
        User user3 = new User();
        user3.setUserName("key3");
        user3.setPassword("value3");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        Map<String, Object> map5 = userList.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.collectingAndThen(Collectors.toList(), value -> value.get(0).getPassword())));
        map5.put("key4", 4);
        UserExtend userExtend = new BeanMapUtils<>(UserExtend.class).mapToBean(map5);
    }

    private void getConstructor(ReflectDemo reflectDemo) throws Exception{
        Class aClass = reflectDemo.getClass();
        //获取public构造方法
        Constructor[] constructors = aClass.getConstructors();
        //获取所有构造方法
        Constructor[] declaredConstructors = aClass.getDeclaredConstructors();

        Constructor constructor = aClass.getDeclaredConstructor();
    }

    private void getFiled(ReflectDemo reflectDemo) throws Exception{
        Class aClass = reflectDemo.getClass();
        //获取全部的属性
        Field[] declaredFields = aClass.getDeclaredFields();
        //获取public 的属性
        Field[] fields = aClass.getFields();
        //age  是private            getField获取public  抛出异常
        //Field age = aClass.getField("age");

        Field age1 = aClass.getDeclaredField("age");
        //全部
        Field sex = aClass.getDeclaredField("sex");


    }

    private void getMethod(ReflectDemo reflectDemo) throws Exception {
        Class aClass = reflectDemo.getClass();

        //根据名称获取方法
        Method method1 = aClass.getMethod("say");

        //获取所有的方法
        Method[] methods = aClass.getMethods();

        //获取私有的方法
        Method[] declaredMethods = aClass.getDeclaredMethods();


        Method method = reflectDemo.getClass().getDeclaredMethod("say");
        method.invoke(reflectDemo);
    }


    public void getObject() throws ClassNotFoundException {
        //1.对象获取
        ReflectDemo reflectDemo = new ReflectDemo();
        //调用ReflectDemo类的父类的方法getClass
        Class c = reflectDemo.getClass();
        System.out.println(c);

        //2.类名获取
        //每个类型，包括基本和引用，
        Class c1=ReflectDemo.class;
        System.out.println(c1);

        //3.Class类的静态方法forName(字符串的类名)包名.类名
        Class c2=Class.forName("com.dj.boot.test.reflect.ReflectDemo");
        System.out.println(c2);
    }
}

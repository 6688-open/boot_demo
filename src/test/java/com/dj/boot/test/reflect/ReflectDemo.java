package com.dj.boot.test.reflect;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 反射测试对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReflectDemo {

    private String name;

    private String age;

    public String sex;

    public void say() {
        System.out.println("say ................");
    }

    private void work() {
        System.out.println("work ................");
    }


    private ReflectDemo(String age) {
        this.age = age;
    }
}

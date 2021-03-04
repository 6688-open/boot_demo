package com.dj.boot.controller.person;

import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @ClassName TestOne
 * @Description TODO
 * @Author wj
 * @Date 2019/12/23 16:37
 * @Version 1.0
 **/
public class PersonTest extends BootDemoApplicationTests {
    @Autowired
    private Person person;
    @Override
    public void run() {
        System.out.println(person);
    }
}

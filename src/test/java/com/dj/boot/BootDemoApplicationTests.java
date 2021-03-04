package com.dj.boot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootDemoApplication.class)
//@TestPropertySource(locations = "classpath:system.properties")
public abstract class BootDemoApplicationTests {

    @Test
    public  void contextLoads() {
    }
    /**
     * run void
     */
    @Test
    public abstract void run() throws Exception;

    @Before
    public void init() {
        System.out.println("-----------------      开始测试   -----------------");
    }

    @After
    public void after() {
        System.out.println("-----------------     测试结束    -----------------");
    }


}

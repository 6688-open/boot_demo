package com.dj.boot.controller.thread;

import com.dj.boot.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "多线程操作接口")
@RestController
@RequestMapping("/thread/")
public class ThreadTestController {


    @Autowired
    private UserService userService;

    @ApiOperation(value = "多线程  开启一个线程", notes="多线程  开启一个线程")
    @PostMapping("thread")
    public String thread(){
        userService.threadTest();
        return "";
    }

    @ApiOperation(value = "多线程  多个任务 开启多个线程", notes="多线程  多个任务 开启多个线程")
    @PostMapping("thread1")
    public String thread1(){
        userService.threadTest1();
        return "";
    }



}

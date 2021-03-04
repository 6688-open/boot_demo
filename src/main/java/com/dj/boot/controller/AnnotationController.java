package com.dj.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName TestOneController
 * @Description TODO
 * @Author wj
 * @Date 2019/11/22 15:53
 * @Version 1.0
 **/
@Api(value = "获取类上面的注解值操作接口")
@RestController
@RequestMapping("/annotation/")
@Order(3)
public class AnnotationController {


    @ApiOperation(value = "获取类上面的注解值", notes="获取类上面的注解值")
    @PostMapping("annotationTest")
    public void annotationTest (){
        //获取类上面的注解值
        Order annotation = AnnotationController.class.getAnnotation(Order.class);
        if(annotation != null){
            int i = annotation.value();
            Method[] met = annotation.annotationType().getDeclaredMethods();
            for(Method me : met ){
                if(!me.isAccessible()){
                    me.setAccessible(true);
                }
                try {
                    System.out.println(me.invoke(annotation, (Object) null));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

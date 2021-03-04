package com.dj.boot.controller.aspect;

import com.dj.boot.annotation.Login;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AspectController
 * @Description TODO
 * @Author wj
 * @Date 2019/12/5 15:56
 * @Version 1.0
 **/
@Api(value = "自定义注解  切面 ")
@RequestMapping("/aspect/")
@RestController
public class AspectController {

    @ApiOperation(value = "测试切面注解", notes="测试切面注解")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "根据标id查询", paramType = "query",  required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "name", paramType = "query",  required = true , dataType = "String")
    })
    @PostMapping("test")
    //@Test(value = 1)
    //@Test(valueStr = "www")
    @Login(1)
    public String test(String id, String name){
        return "boot,ok";
    }
}

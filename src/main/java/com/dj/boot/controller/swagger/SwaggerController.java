package com.dj.boot.controller.swagger;

import com.dj.boot.common.base.ResultModel;
import com.dj.boot.common.util.UUIDUtil;
import com.dj.boot.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * http://localhost:8082/swagger-ui.html
 * @ClassName SwaggerController
 * @Description TODO
 * @Author wj
 * @Date 2019/12/5 14:33
 * @Version 1.0
 **/
@RestController
@RequestMapping("/swagger/")
@Api("swagger 测试接口 ")
public class SwaggerController {

    @ApiOperation(value="多个参数说明", notes="多个参数说明")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "签约根据标id查询", paramType = "query",  required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token", value = "token", paramType = "query",  required = true , dataType = "String")
    })
    @PostMapping("sureOrder")
    public ResultModel sureOrder(String token,  Integer id){
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("token", token);
            return new ResultModel().success("签约成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }


    @ApiOperation(value="单个参数说明", notes="单个参数说明")
    @ApiImplicitParam(name = "token", value = "token", paramType = "query", required = true , dataType = "String")
    @GetMapping("swaggerTest")
    public ResultModel swaggerTest( @RequestParam(name = "token") String token){
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", token);
            return new ResultModel().success("签约成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }
    }



    /**
     * 用户注册 测试实体类接参数
     * @return
     */
    @ApiOperation(value=" 测试实体类接参数", notes=" 测试实体类接参数  Json 传参")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
    @PostMapping("register")
    public ResultModel register(@RequestBody User user){
        try {
        HashMap<String, Object> map = new HashMap<>();
        //断言非空判断
        Assert.notNull(user.getUserName(), "null");
            map.put("user",user);
            map.put("token","token");
            return new ResultModel().success("success",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("异常"+e.getMessage());
        }

    }
}

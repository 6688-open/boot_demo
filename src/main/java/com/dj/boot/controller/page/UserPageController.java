package com.dj.boot.controller.page;

import com.dj.boot.common.enums.BusinessSignEnum;
import com.dj.boot.configuration.quartz.util.SpringUtil;
import com.dj.boot.pojo.Person;
import com.dj.boot.pojo.User;
import com.dj.boot.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * http://localhost:8082/toLogin
 *
 * @ClassName UserPageController
 * @Description TODO
 * @Author wj
 * @Date 2019/12/5 15:29
 * @Version 1.0
 **/
@Api(value = "用户跳转页面接口")
@Controller
public class UserPageController {

    @Autowired
    private UserService userService;

    @Value("${qiniu.url}")
    private String qiniuUrl;

    @Autowired
    private Person person;

    @Autowired
    private Environment environment;

    @ApiOperation(value = "跳转登录页面", notes="跳转登录页面")
    @GetMapping("toLogin")
    public String toLogin(Model model){
        return "login";
    }

    /**
     * 去注册
     * @return
     */
    @GetMapping("toRegister")
    public String toRegister(){
        return "register";
    }

    /**
     * 忘记密码  手机号找回密码
     * @return
     */
    @GetMapping("toFindPassword")
    public String toFindPassword(){
        return "find_password";
    }
    /**
     * 跳转详情页
     * @return
     */
    @GetMapping("toFindDetails")
    public String toFindDetails(){
        return "details";
    }
    /**
     * 跳转详情页
     * @return
     */
    @GetMapping("toFindTime")
    public String toFindTime(){
        return "times";
    }
    /**
     * 跳转展示树
     * @return
     */
    @GetMapping("toFindZTree")
    public String toFindZTree(){
        return "ztree";
    }
    /**
     * 测试jq 传值
     * @return
     */
    @GetMapping("transmissionValue")
    public String transmissionValue(Integer id, String year, String createTime){
        System.out.println(id);
        return "find_password";
    }


    @ApiOperation(value = "跳转用户列表页面", notes="跳转用户列表页面")
    @GetMapping("list")
    public String list(Model model){
        List<User> list = userService.list();
        List<Map<String, String>> businessSignList = BusinessSignEnum.getBusinessSignList();
        model.addAttribute("userList",list);
        model.addAttribute("businessSignList",businessSignList);
        return "userList";
    }

    @ApiOperation(value = "获取配置文件信息 ${qiniu.url}", notes="获取配置文件信息 ${qiniu.url}")
    @GetMapping("testHtml")
    public String testHtml()  {
        System.out.println(qiniuUrl);
        System.out.println(person);
        //获取配置文件里的内容
        String carbonCallInterface = environment.getProperty("commercial.test");
        System.out.println(carbonCallInterface);
        return "test";
    }


    @ApiOperation(value = "测试java 反射机制 ", notes="测试java 反射机制")
    @PostMapping("testInvoke")
    public String testInvoke() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object o = SpringUtil.getBean("secondJop");
        //Object o = SpringUtil.getBean(SecondJop.class);
        if (Objects.isNull(o))  {
            return null;
        }
        //获取本类全部方法
        Method[] methods = o.getClass().getDeclaredMethods();
        Method method1 = o.getClass().getDeclaredMethod("execute");
        method1.invoke(o);
        for (Method method : methods) {
            method.invoke(o);
        }

        return "test";
    }
}

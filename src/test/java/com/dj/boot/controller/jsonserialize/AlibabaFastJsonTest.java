package com.dj.boot.controller.jsonserialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.common.util.security.Sha256Utils;
import com.dj.boot.pojo.User;
import com.dj.boot.service.user.UserService;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName UserTestController
 * @Description TODO
 * @Author wj
 * @Date 2019/12/5 17:33
 * @Version 1.0
 **/

public class AlibabaFastJsonTest extends BootDemoApplicationTests {

    @Autowired
    private UserService userService;


    @Test
    public void testUser(){
        List<User> users = userService.list();
        List<Integer> list = users.stream().map(User::getId).collect(Collectors.toList());
        System.out.println(111);
    }

    /**
     * 加密测试
     */
    @Test
    public void test(){
        //SHA256算法使用的哈希值长度是256位
        String pwd = "123456";
        String pwd1 = "123456";
        String s = Sha256Utils.encipherToHex(pwd);
        String s1 = Sha256Utils.encipherToHex(pwd1);
        if (s.equals(s1)) {
            System.out.println(s);
            System.out.println(s1);
        }

        byte[] bytes = pwd.getBytes();
        byte[] bytes1 = Sha256Utils.encipher(bytes);
    }



    /**
         * fastjson  测试
     */
    @Test
    public void test1() throws IOException {
        Map<String, Object> map = new HashMap<>();
        List<User> users = userService.list();
        Set<User> set1 = users.stream().collect(Collectors.toSet());
        map.put("list", users);
        map.put("test", "test");

        //map list set object -----> json--String
        String s = JSONObject.toJSONString(map);
        String list1 = JSONObject.toJSONString(users);
        String set2 = JSONObject.toJSONString(set1);
        String u = JSONObject.toJSONString(users.get(0));
        Map<String,Object> toom = JSONObject.parseObject(s, Map.class);

        //google json工具类
        final Map<String, Object> parsedHeader = JsonUtil.fromJson(s, new TypeReference<HashMap<String, Object>>() {
        });
        List list = JSONObject.parseObject(list1, List.class);
        Set set = JSONObject.parseObject(set2, Set.class);
        User uu = JSONObject.parseObject(u, User.class);//User
    }

    @Override
    public void run() {
        Map<String, Object> snMt = new HashMap<String, Object>(16);
        snMt.put("warrantyFlag", true);
        snMt.put("warrantyFlag1", false);
        String s = JSONObject.toJSONString(snMt);

        Map map = JSONObject.parseObject(s, Map.class);
        Object warrantyFlag = map.get("warrantyFlag");
        Object warrantyFlag1 = map.get("warrantyFlag1");
        //warrantyFlag1.toString()
        boolean warranty = Boolean.parseBoolean(Objects.isNull(warrantyFlag) ? "false" : "true");

        System.out.println(warranty);
        testParam("1","3");

    }



    private void testParam(String...value){

        System.out.println(Arrays.toString(value));
    }




    @Test
    public void test10(){
        User user = new User();
        user.setSex(1);
        user.setUserName("111");
        user.setPassword("222");
        user.setSalt("33333");

        Object o = JSON.toJSON(user);
        System.out.println(o.toString());

        SerializeConfig mapping = new SerializeConfig();
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));

        Object o1 = JSON.toJSON(user, mapping);
        System.out.println(o1.toString());
    }

}

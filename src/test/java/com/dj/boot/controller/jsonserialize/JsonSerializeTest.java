package com.dj.boot.controller.jsonserialize;

import com.alibaba.fastjson.JSON;
import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.controller.jsonserialize.deserializemodel.StudentTestModel;
import com.dj.boot.controller.jsonserialize.deserializemodel.UserTestModel;
import com.dj.boot.controller.jsonserialize.model.Address;
import com.dj.boot.controller.jsonserialize.model.StudentTest;
import com.dj.boot.controller.jsonserialize.model.UserTest;
import com.dj.boot.controller.jsonserialize.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JsonSerialize
 * @Description TODO
 * @Author wj
 * @Date 2020/1/17 11:12
 * @Version 1.0
 **/
public class JsonSerializeTest extends BootDemoApplicationTests {
    @Override
    public void run() {
        //将 map. ..... 对象序列化
        Map<String, Object> map = transationMap();
        /**
         * 序列化
         */
        //String serialize1 = JSONObject.toJSONString(map);
        //String json= JSON.toJSONString(map);
        /**
         * json -- json数组
         */
        //JSONArray jsonArray = JSONObject.parseArray(json);
        /**
         * 反序列化
         */
        //Map<String, Object> object = JSONObject.parseObject(serialize1, Map.class);
        String serialize = "";
        try {
            serialize = JsonUtils.serialize(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //反序列化
        Map<String, Object> partOrderMap = JSON.parseObject(serialize, Map.class);
        Object studentTest = partOrderMap.get("studentTest");
        Object userTest = partOrderMap.get("userTest");
        //将对象序列化   然后再反序列化成对象
        String studentTestSerialize = "";
        String userTestSerialize = "";
        try {
            studentTestSerialize = JsonUtils.serialize(studentTest);
            userTestSerialize = JsonUtils.serialize(userTest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            StudentTestModel studentTest1 = JsonUtils.deserialize(studentTestSerialize, StudentTestModel.class);
            UserTestModel userTest1 = JsonUtils.deserialize(userTestSerialize, UserTestModel.class);
            System.out.println("111111111111111111");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Map<String, Object> transationMap(){
        Map<String, Object> map = new HashMap<>();
        ArrayList<Address> list = new ArrayList<>();
        Address addName = new Address().setId(1).setAddressName("add_name");
        Address addName2 = new Address().setId(2).setAddressName("add_name2");
        Address addName3 = new Address().setId(3).setAddressName("add_name3");
        list.add(addName);
        list.add(addName2);
        list.add(addName3);
        StudentTest studentTest = new StudentTest().setId(1).setStudentAge("13岁").setStudentName("马云");
        UserTest userTest = new UserTest().setId(2).setUserAge("15岁").setUserName("wj").setAddressList(list);
        map.put("studentTest", studentTest);
        map.put("userTest", userTest);
        return map;
    }
}

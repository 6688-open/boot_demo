package com.dj.boot.test;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.pojo.KdnTrackReqDto;
import com.dj.boot.pojo.User;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.springframework.cglib.beans.BeanMap;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.dj.boot.common.util.StringUtils.upperFirstCase;

public class TestDemo {



    public static void main(String[] args) {

        KdnTrackReqDto kdnTrackReqDto = new KdnTrackReqDto();
        kdnTrackReqDto.setCustomerName("22222");
        kdnTrackReqDto.setOrderCode("33333");
        final String json = JSONObject.toJSONString(kdnTrackReqDto);
        System.out.println(json);

        String s = transformUpperCase(json);


        Map<String,Object> header = new HashMap<String, Object>();
        header.put("supplyArgs",true);
        header.put("APIKey","9462ab5a-e73e-4a3b-80cf-55a9b7ffc1c0");
        header.put("EBusinessID","1696923");
        header.put("RequestType","8002");
        header.put("DataType","2");

        Object dataType = header.get("DataType1");
        String dataType1 = header.get("DataType1").toString();


        System.out.println(System.nanoTime());

        String a = null;

        List<String> l = new ArrayList<>();
        l.add("111");
        if (l.contains(a)) {
            System.out.println(111);
        }


        List<User> list1 = new ArrayList<>();
//        User user1 = new User();
//        user1.setPassword("2111");
//        User user2 = new User();
//        user2.setPassword("2111");
//        list1.add(user1);
//        list1.add(user2);

        List<User> list = new ArrayList<>();
        User user = new User();
        user.setPassword("2111333");list.add(user);


        list.addAll(list1);


        String url = "我叫%s,今年%s岁。";
        String name = "";
        String age = "23";
        url = String.format(url,name,age);
        System.out.println(url);
    }


    // 将map的Key全部转换为大写
    public static String transformUpperCase(String json) {
        Map<String,Object> orgMap = JSONObject.parseObject(json, Map.class);
        Map<String, Object> resultMap = new HashMap<>();
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            resultMap.put(Character.toUpperCase(key.charAt(0)) + key.substring(1), orgMap.get(key));
        }
        return JSONObject.toJSONString(resultMap);
    }


    @Test
    public void StringLengthTest(){


        List<String> errMsgs = new ArrayList<>();
        errMsgs.add("防重码为空");
        errMsgs.add("仓库名称为空");

        errMsgs.add("物资名称与物资别名二选一必填");
        errMsgs.add("机构名称为空");

        errMsgs.add("采购类型为空");

        errMsgs.add("单据类型名称为空");

        errMsgs.add("物资数量为空");

        errMsgs.add("计划完成日期为空");

        String join = String.join(", ", errMsgs);

        System.out.println(join);

        String str = "qwertyuiop";
        String substring = str.substring(1);
        System.out.println(substring);
    }


    @Test
    public void test2() {

        //List<RequireItem> requireItems = requireItemList.stream().filter(requireItem -> requireItem.getRealCount() != null && requireItem.getRealCount().compareTo(new BigDecimal(0)) > 0).collect(Collectors.toList());

        if (new BigDecimal(-1).compareTo(new BigDecimal(0)) > 0) {

            System.out.println("大于0");
        }
        System.out.println(new BigDecimal(-1).compareTo(new BigDecimal(0)) );
    }
    @Test
    public void test1() {

        equalsTest();
        stringTest();
        test();

    }


    // java中%d %s的含义
    private void test () {
        //%s 替换符
        System.out.println(String.format("第%s条, 一共出现了%s个异常", 2,4));
        System.out.println(String.format("第%10d条出现异常", 2+ 1));

        List<String> errMsg = Lists.newArrayList();
        errMsg.add("java");
        errMsg.add("is");
        errMsg.add("cool");
        String join = String.join("-", errMsg);
        System.out.println(join);
    }

    private void equalsTest() {
        int a = 1;
        String b = "";
        String c = null;
        if (String.valueOf(a).equals(b)) {

            System.out.println(111);
        }
        if (String.valueOf(a).equals(c)) {
            System.out.println(222);
        }
    }

    private void stringTest() {
    /*@Override
    public synchronized StringBuffer append(String str) {
        toStringCache = null;
        super.append(str);
        return this;
    }*/
        StringBuffer stringBuffer = new StringBuffer("111");
        stringBuffer.append("222");

        StringBuilder stringBuilder = new StringBuilder("333");
        short s1 = 1;
        //s1 = s1 + 1;
        s1 += 1;
    }

    @Test
    public void testSet() {
        Byte [] staffs = new Byte[]{3, 4, 5,6,7};

        Set<Byte> staffsSet = new HashSet<>(Arrays.asList(staffs));
        System.out.println(staffsSet);

        Set<Byte> set = new HashSet<>();
        set.add((byte)3);
        set.add((byte)4);
        set.add((byte)5);
        set.add((byte)6);
        set.add((byte)7);
        System.out.println(set);
    }




    @Test
    public void test6(){
        User u = new User();
        u.setSex(11);
        u.setUserName("wj");
        String s = JSONObject.toJSONString(u);

        User u1 = new User();
        u1.setUserName("wwwwwwwww");
        u1.setEmail(s);
        System.out.println(u1);
        String s1 = JSONObject.toJSONString(u1);
        System.out.println(s1);

        User user = new User().setUserName("zs").setSalt("222").setSex(1).setPassword("123");
        //Map<String, Object> map1 = JSONObject.parseObject(JSONObject.toJSONString(user));
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(user), Map.class);
        HashMap hashMap = new HashMap<>(BeanMap.create(user));
        System.out.println(111);
    }




    @Test
    public void test11(){
        User user = new User();
        user.setEmail("100");
        User user1 = new User();
        user1.setEmail("100");

        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        StringBuilder res = null;
        for(User info : list) {
            //二选一
            //net.sf.cglib.beans.BeanMap map = net.sf.cglib.beans.BeanMap.create(info);
            HashMap hashMap = new HashMap<>(BeanMap.create(info));
            String value = (String)hashMap.get("email");
            switch(value) {
                case "100":
                    value="1";
                    break;
                case "200":
                    value="2";
                    break;
                default:
                    value="";
                    break;
            }
            if(res != null) {
                res.append(","+ value);
            }else {
                res = new StringBuilder(value);
            }
        }

        System.out.println(res);

    }


    /**
     * java的八大基本类型
     * byte(字节类型)	1字节	-2^7 ~ 2^7-1
     * short(短整形)	2字节	-2^15 ~ 2^15-1
     * int(整形)	    4字节	-2^31 ~ 2^31-1
     * long(长整型)	8字节	-2^63 ~ 2^63-1
     * float(单精度浮点数)	4字节	-2^128 ~ 2^128
     * double(双精度浮点数)	8字节	-2^1024 ~ 2^1024
     * char(字符型)	1字节
     * Boolean(布尔类型)	2字节
     *
     * 注意:
     * 	(1)整数字面值是默认int类型来存储；如果整数字面值超过int类型，将类型转换为long类型，需要在数值后面加L或l后缀。但如果没超过int类型的范围，又需要用long类型来接收，则可以不加后缀.
     * 	(2)浮点数字面值默认为double，如果想要使用float类型来存储浮点数字面值，则需要在其后加F或者f.
     * 	   double类型是浮点数常用的类型；double类型的字面值后面可以加D或者d，也可以不加.
     * 	   浮点型存储数据可能有精度损失.
     * 	(3)字符类型的数据可以通过int类型的数字来表示,通过ASCII码表转换为对应的值.
     * 	(4)Boolean类型的值只能是true或者false,默认为true.
     * 	(5)基本数据类型都是关键字.
     *
     * 	1、**自动类型转换**
     * 	数值型:从小到大
     * 		整数:byte->short->int->long
     * 		浮点型:float->double
     * 			float自动转为double类型可能有精度损失.
     * 		整形->浮点数
     * 			直接在后面补上 .0
     * 			byte->short->int->long---->float->double(虚线的转换有精度损失)
     * 		字符型:
     * 			char->int->long---->float->double
     * 			char->int
     * 			根据码表将字符对应的ASCII值转为int类型.
     * **2、强制类型转换**
     * 	从大到小:编译会报错
     * 	大的类型转换为小的类型可能会溢出，为了确保程序的安全，编译时就会报错.
     * 	大的数据类型的数据值在小数据类型的范围之内，可以使用强制类型转换.
     *
     * 	格式:
     * 		(type)数据
     * 		将数据的类型强制转换为小括号的数据类型.
     * 	注意:
     * 		1、浮点数强制转换为整数存在精度损失无论小数部分直接舍弃.
     * 		2、类似于Boolean类型无法通过强制类型转换转换为数值型.
     * 		3、当大的数据类型超过小的数据类型范围时，从最低位开始拿对应的位数的二进制.
     */
    @Test
    public void test12(){
        byte b = 1;
        short s = 2;
        int i = 3;
        long l = 4;


        byte be = (byte) s;
        long l1 = s;
        int i1 = (int) l;

        float f = 2.22f;
        double d = 33.55d;
        char c = 5;
        boolean b1 = true;




        long l3 = c;
        int i8 = c;
        double d4 = c;
        float f6 = c;
        byte b7 = (byte) c;
        char c2 = (char) b;
        char c3 = (char) l;
        char c4 = (char) d;



        float f4 = i;
        System.out.println(f4);


        long l9 = (long) d;
        System.out.println(l9);


    }




    @Test
    public void test20(){

        Integer code = 0;
        String msg = "";
        switch (code) {
            case 0:
                System.out.println("0当前code值是:["+code+"]");
                msg="当前code值是:["+code+"]";
                break;
            case 1:
                System.out.println("1当前code值是:["+code+"]");
                msg="当前code值是:["+code+"]";
                break;
            case 2:
                System.out.println("2当前code值是:["+code+"]");
                msg="当前code值是:["+code+"]";
                break;
        }


        System.out.println(msg);

    }




}

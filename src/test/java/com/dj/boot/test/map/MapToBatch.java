package com.dj.boot.test.map;

import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.pojo.User;
import com.dj.boot.service.user.UserService;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *  实现java 中 list集合中有几十万条数据,每100条为一组取出
 *
 *  批次获取 指定数量的数据
 *
 * 将list数据取出来  每次取 2 条数据
 */
public class MapToBatch extends BootDemoApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void testMap(){
        list2(userService.list());
    }

    /**
     *  1  将数据每 2个拆分 放到map里 就可以批次获取  逻辑处理
     * @param list
     * @return
     */
    public Map<String, List<User>> list2(List<User> list)  {
        int listSize = list.size();
        int toIndex = 2;  //每次取2条
        Map<String, List<User>> map = new HashMap<>();     //用map存起来新的分组后数据
        int keyToken = 0;
        for(int i = 0; i<list.size(); i += toIndex){
            if(i+toIndex > listSize){        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                toIndex = listSize-i;
            }
            List<User> newList = list.subList(i,i+toIndex);
            map.put("keyName"+keyToken, newList);
            keyToken++;
        }

        map.forEach((k,v)->{
            System.out.println(k);
            v.forEach(System.out::println);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return map;
    }





    @Override
    public void run() {
        list(userService.list());
    }

    private static final Integer BATCH_NUM = 3;

    /**
     * 批次处理  stream.skip()  .limit()实现
     * @param list
     */
    @SneakyThrows
    private void list(List<User> list) {
        int len = list.size();
        // 计算size
        int size = len / BATCH_NUM;
        // 计算余数
        int remainder = len % BATCH_NUM;
        if (remainder > 0) {
            size = size + 1;
        }
        // 分批异步处理
        for (int i = 0; i < size; i++) {
            List<User> userList = list.stream().skip(i * BATCH_NUM).limit(BATCH_NUM).collect(Collectors.toList());
            userList.forEach(System.out::println);
            TimeUnit.SECONDS.sleep(2);
        }
    }





    @Test
    public void test4(){
        list4(userService.list());
    }
    /**
     *   list的partition 分片
     */
    public void list4(List<User> list){
        //List<List<User>> lists = Lists.partition(list, 2);
        List<List<User>> lists = ListUtils.partition(list, 2);
        lists.forEach(userList -> {
            userList.forEach(u ->{
                System.out.println(u);
            });
        });

    }


    public static void main(String[] args) {
        int remainder = 6 % 3;
        System.out.println(remainder);
    }

}

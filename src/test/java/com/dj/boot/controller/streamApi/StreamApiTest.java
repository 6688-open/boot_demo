package com.dj.boot.controller.streamApi;

import com.alibaba.fastjson.JSON;
import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.common.mapper.convert.UserConvert;
import com.dj.boot.common.util.LogUtils;
import com.dj.boot.common.util.collection.CollectionUtils;
import com.dj.boot.controller.streamApi.dto.Address;
import com.dj.boot.controller.streamApi.dto.Country;
import com.dj.boot.controller.streamApi.dto.UserInfo;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import com.dj.boot.service.user.UserService;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @ClassName StreamApiTest
 * @Description TODO
 * @Author wj
 * @Date 2020/1/2 11:50
 * @Version 1.0
 **/
public class StreamApiTest extends BootDemoApplicationTests {

    @Autowired
    private UserService userService;

    @Override
    public void run() {
        LogUtils.info("StreamApiTest ------  start............");
        List<User> list = userService.list();

        List<User> users = list.stream().map(user -> {
            User u = new User();
            u.setUserName(user.getUserName() + "prase");
            u.setId(user.getId());
            u.setEmail("183@163.com");
            return u;
        }).collect(Collectors.toList());

        users.forEach(System.out::println);


        //排序 String --->  Integer
        List<UserDto> listDto = Lists.newArrayList();
        list.forEach(user -> {
            listDto.add(UserConvert.INSTANCE.voToDto(user));
        });
        List<UserDto> userDtos = listDto.stream().sorted(Comparator.comparing(UserDto::getUserName)).collect(Collectors.toList());
        userDtos.forEach(System.out::println);




        HashMap<Object, Object> map1 = new HashMap<>();
        map1.put("username", "1111");
        map1.put("list", list);
        String json= JSON.toJSONString(map1);
        System.out.println(json);

        //根据name 去重
        ArrayList<User> distinctByName = list.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUserName))),
                                ArrayList::new
                        )
                );

        //取出id的属性值 生成一个新的set/list集合
        Set<Integer> existCustomerIds = list.stream().map(User::getId).collect(Collectors.toSet());
        List<Integer> list1 = list.stream().map(User::getId).collect(Collectors.toList());


        //过滤元素
        List<User> listFilter = list.stream().filter(user -> user.getUserName().equals("zs")).collect(Collectors.toList());


        //过滤加取出id 新的id集成
        List<Integer> list2 = list.stream().filter(user -> user.getUserName().equals("zs")).collect(Collectors.toList())
                .stream().map(User::getId).collect(Collectors.toList());

        //anyMatch 是否有匹配的数据  返回boolean
        //anyMatch表示，判断的条件里，任意一个元素成功，返回true
        //allMatch表示，判断条件里的元素，所有的都是，返回true
        //noneMatch跟allMatch相反，判断条件里的元素，所有的都不是，返回true
        boolean b = list.stream().anyMatch(user -> user.getUserName().equals("1") && user.getSex() == 1);
        System.out.println(b);
        /*入库数量必须大于0*/
        boolean qtyFlag = list.stream()
                .anyMatch(user -> {
                    Pattern pattern=Pattern.compile("(^[1-9](\\d+)?(\\.\\d{1,2})?$)|(^\\d\\.\\d{1,2}$)"); //判断小数点后2位的数字的正则表达式
                    Matcher match=pattern.matcher(String.valueOf(user.getTotalNum()));
                    return match.matches();
                });

        //skip(lang n) 是一个跳过前 n 个元素的中间流操作
        // 。我们编写一个简单的方法来进行skip操作,将流剩下的元素打印出来。
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);
        integerStream.skip(2).forEach(integer -> System.out.println("integer = "  + integer));

        //Optional 类 过滤取第一条
        Optional<User> cartOptional = list.stream().filter(user -> user.getUserName().equals("zs")).findFirst();
        if (cartOptional.isPresent()) {
            //
            LogUtils.info("user  UserName 等于 zs 存在");
            User cart =  cartOptional.get();
        } else {
            // 不存在
            LogUtils.info("user  UserName 等于 zs   不存在");
        }



        //Optional 类  取出最大的一条
        Optional<User> userOp= list.stream().max(Comparator.comparingInt(User ::getId));
        if (userOp.isPresent()) {
            User user = userOp.get();
            LogUtils.info(user.getUserName());
        }


        //分组 合并数量
        /**
         * merge 统计每个学生的 分数总和
         */
        Map<String, Integer> maps = new HashMap<>();
        list.forEach(user -> maps.merge(
                user.getUserName(),
                user.getSex(),
                Integer::sum
        ));
        /**
         *  BigDecimal
         */
        Map<String, BigDecimal> maps1 = new HashMap<>();
        list.forEach(user -> maps1.merge(
                user.getUserName(),
                user.getTotalNum(),
                BigDecimal::add
        ));
        BigDecimal result2 =   maps1.values().stream() .reduce(BigDecimal.ZERO,BigDecimal::add);
                //Integer num = list.stream().mapToInt(User::getId).sum();

        System.out.println(result2);


        /**
         *  list.stream().collect(Collectors.groupingBy 分组相关
         */

        //分组  转成map集合 <String, List<User>>  value对应的是List
        Map<String, List<User>> map = list.stream().collect(Collectors.groupingBy(User::getUserName));
        map.forEach((key, value) -> {
            LogUtils.info(key);
            if (key.equals("zs")) {
                value.forEach(user -> LogUtils.info(user.getUserName()+"value   forEach"));
            }
        });

        // 1 根据userName分组 k 每组数据总个数
        Map<String, Long> map3 = list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.counting()));

        // 2 value 可能是个List 这里在List取出Sex 最大的一条
        Map<String, User> map4 = list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(User::getSex)), Optional::get)));
        //Map<String, User> map4 = list.stream().collect(Collectors.toMap(User::getUserName, Function.identity(), BinaryOperator.maxBy(Comparator.comparingInt(User::getSex))));

        // 3 value 返回的是集合  取出第一条
        Map<String, User> map5 = list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.collectingAndThen(Collectors.toList(), value -> value.get(0))));
        //Map<String, String> map5 = list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.collectingAndThen(Collectors.toList(), value -> value.get(0).getUserName())));


        //3 value 返回的是集合  取出第一条
        Map<String, User> lineNoMaps = Maps.uniqueIndex(list.iterator(), new Function<User, String>() {
            @Override
            public String apply(User user) {
                return user.getUserName();
            }
        });

        //取出List 中的第一条数据中的某个属性值
        Map<String, BigDecimal> map6 = list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.collectingAndThen(Collectors.toList(), value -> value.get(0).getTotalNum())));


        // 4 根据userName分组 求和 int类型
        Map<String, Integer> map7 = list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.summingInt(User::getId)));

        // 5 k 对应集合的某个属性的总和  BigDecimal类型
        Map<String, BigDecimal> map8 = list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.collectingAndThen(Collectors.toList(), userList -> {
            AtomicReference<BigDecimal> totalNum = new AtomicReference<>(BigDecimal.ZERO);
            // BigDecimal 的add 的结果 返回值才是和  所以把和加到AtomicReference 进行求和
            userList.forEach(user -> {
                totalNum.set(totalNum.get().add(user.getTotalNum()));
            });
            return totalNum.get();
        })));

        // 6 求和 仿照Collectors.summingInt 源码 声明函数式接口 求和 BigDecimal类型
        Map<String, BigDecimal> map9 = list.stream()
                .collect(Collectors.groupingBy(User::getUserName,CollectorsUtil.summingBigDecimal(User::getTotalNum)));

        //7 根据多个属性分组
        Map<String, BigDecimal> map11 = list.stream()
                .collect(Collectors.groupingBy(user -> user.getUserName()+"_"+user.getPassword()+"_"+user.getSalt(),CollectorsUtil.summingBigDecimal(User::getTotalNum)));

        //8 根据id分组
        Map<Integer, User> map2 = list.stream().collect(Collectors.toMap(User::getId, x -> x));
//        List<ReceiptsPerformItemDto>  receiptsPerformItemDtos = receiptsPerformDto.getReceiptsPerformItemDtoList();
//        /*物资商品维度 进行合并数量*/
//        Map<String, BigDecimal> goodsApplyQtymap = receiptsPerformItemDtos.stream()
//                .collect(Collectors.groupingBy(ReceiptsPerformItemDto::getGoodsNo,CollectorsUtil.summingBigDecimal(ReceiptsPerformItemDto::getGoodsApplyQty)));
//
//        //3、构建明细
//        List<ReceiptsPerformItem> receiptsPerformItemList = new ArrayList<>();
//        Set<String> goodsNoSet= new HashSet<>();
//        for (ReceiptsPerformItemDto item : receiptsPerformDto.getReceiptsPerformItemDtoList()) {
//            /*去重,存在不在设置明细*/
//            if(!goodsNoSet.contains(item.getGoodsNo())){
//                goodsNoSet.add(item.getGoodsNo());
//                ReceiptsPerformItem receiptsPerformItem = new ReceiptsPerformItem();
//                receiptsPerformItem.setReceiptsPerformNo(receiptsPerform.getReceiptsPerformNo());
//                receiptsPerformItem.setGoodsApplyQty(goodsApplyQtymap.get(item.getGoodsNo()));
//                receiptsPerformItemList.add(receiptsPerformItem);
//            }
//
//        }
//


        // 9 联合其他收集器 k分组后的 value 的List 取出里面的某个值 返回的集合
        Map<String, Set<Integer>> map10 = list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.mapping(User::getId, Collectors.toSet())));

//=================================================================================================================================================================================
//=================================================================================================================================================================================







        //计数
        Integer num = list.stream().mapToInt(User::getId).sum();



        //取出时间最大的一条   根据时间倒叙  取第一条
        List<User> list3 = list.stream().filter(user -> user.getCreateTime() != null).sorted((s1, s2) -> s2.getCreateTime().compareTo(s1.getCreateTime())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(list3)) {
            User user = list3.get(0);
            LogUtils.info("取出时间最大的一条" + "-------" + user.getUserName());
        }

        //排序
        List<User> userList = list.stream().sorted(Comparator.comparing(User::getUserName, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());

        //根据两个条件排序
        list.stream().filter(user -> user.getSex() != null && StringUtils.isNoneBlank(user.getUserName())).sorted(Comparator
                .comparing(User::getSex)   // 先按照升序
                .reversed()              //再将其倒叙
                .thenComparing(User::getUserName))
                .forEach(System.out::println);






        //limit 方法用于获取指定数量的流。 以下代码片段使用 limit 方法打印出 10 条数据：
        List<User> list4 = list.stream().limit(3).collect(Collectors.toList());



        //并行（parallel）程序
        //parallelStream 是流并行处理程序的代替方法。以下实例我们使用 parallelStream 来输出空字符串的数量：
        long count = list.parallelStream().filter(user -> user.getUserName().equals("zs")).count();
        LogUtils.info(count+"");



        //统计
        //一些产生统计结果的收集器也非常有用。它们主要用于int、double、long等基本类型上，它们可以用来产生类似如下的统计结果。

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        LogUtils.info("列表中最大的数 : " + stats.getMax());
        LogUtils.info("列表中最小的数 : " + stats.getMin());
        LogUtils.info("所有数之和 : " + stats.getSum());
        LogUtils.info("平均数 : " + stats.getAverage());



        List<String> list5 = new ArrayList<>();
        list5.add("111");
        list5.add("222");
        list5.add("333");
        String join = String.join("+", list5);


        int sum = IntStream.rangeClosed(1, 100)   // 生成 1 100 数字
                .filter(a -> a % 2 == 0)
                .sum();



        //将int[] 转换成integer[] 相互转换
        int [] nums = {9, 5, 2, 7};
        Integer [] integernums = new Integer[nums.length];

        for (int i = 0; i < nums.length; i++) {
            integernums[i] = nums[i];
        }
        System.out.println(Arrays.toString(integernums));

        Integer[] array = Arrays.stream(nums).boxed().toArray(Integer[]::new);
        System.out.println(Arrays.toString(array));


    }











    //==============================================================================================
    //==============================================================================================
    @Test
    public void flatMapTest(){
        List<String> teamIndia = Arrays.asList("Virat", "Dhoni", "Jadeja");
        List<String> teamAustralia = Arrays.asList("Warner", "Watson", "Smith");
        List<String> teamEngland = Arrays.asList("Alex", "Bell", "Broad");
        List<String> teamNewZeland = Arrays.asList("Kane", "Nathan", "Vettori");
        List<String> teamSouthAfrica = Arrays.asList("AB", "Amla", "Faf");
        List<String> teamWestIndies = Arrays.asList("Sammy", "Gayle", "Narine");
        List<String> teamSriLanka = Arrays.asList("Mahela", "Sanga", "Dilshan");
        List<String> teamPakistan = Arrays.asList("Misbah", "Afridi", "Shehzad");

        List<List<String>> playersInWorldCup2016 = new ArrayList<>();
        playersInWorldCup2016.add(teamIndia);
        playersInWorldCup2016.add(teamAustralia);
        playersInWorldCup2016.add(teamEngland);
        playersInWorldCup2016.add(teamNewZeland);
        playersInWorldCup2016.add(teamSouthAfrica);
        playersInWorldCup2016.add(teamWestIndies);
        playersInWorldCup2016.add(teamSriLanka);
        playersInWorldCup2016.add(teamPakistan);

        // Let's print all players before Java 8
        List<String> listOfAllPlayers = new ArrayList<>();

        for(List<String> team : playersInWorldCup2016){
            for(String name : team){
                listOfAllPlayers.add(name);
            }
        }

        System.out.println("Players playing in world cup 2016");
        System.out.println(listOfAllPlayers);


        // Now let's do this in Java 8 using FlatMap
        List<String> flatMapList = playersInWorldCup2016.stream()
                .flatMap(pList -> pList.stream())
                .collect(Collectors.toList());

        System.out.println("List of all Players using Java 8");
        System.out.println(flatMapList);




        List<User> uList = Lists.newArrayList();
        User u1 = new User();
        u1.setUserName("a1;a2;a3;a4;a5");

        User u2 = new User();
        u2.setUserName("b1;b2;b3;b4;b5");

        uList.add(u1);
        uList.add(u2);

        List<String> addrList = uList.stream().map(User::getUserName).flatMap(x->Arrays.stream(x.split(";"))).collect(Collectors.toList());
        //或者
        List<String> ridStrList = uList.stream().map(u -> u.getUserName()).map(userName -> userName.split(";")).flatMap(Arrays::stream).collect(Collectors.toList());

        List<String> ridStrList1 = uList.stream().map(u -> u.getUserName()).map(userName -> Arrays.asList(userName.split(";"))).flatMap(Collection::stream).collect(Collectors.toList());

        System.out.println(addrList);
    }









    //==============================================================================================
    //==============================================================================================
    // 在 Java 开发中，通常会使用 if-else 逻辑判断来解决 NullPointerException
    // 问题，当有对象模型嵌套过多时，就会因过多的 if-else 判断而形成代码累赘。
    // 代码清单 2-4
    public String getUserCountryCodByOptional2(UserInfo user) {
        return Optional.ofNullable(user)
                .map(UserInfo::getAddress)
                .map(Address::getCountry)
                .map(Country::getCountryCode)
                .orElseThrow(() -> new IllegalArgumentException(" 无法获取到新的值")).toUpperCase();
    }


    public static void main(String[] args) {

        //======================================================================================
        //Optional 对象创建
        //可通过Optional类提供的三个静态方法empty()、of(T value)、ofNullable(T value)来创建Optinal对象

        //第二种方式 通过 of 方法为非null的值创建一个Optional。of方法通过工厂方法创建Optional类。需要注意的是，创建对象时传入的参数不能为null。如果传入参数为null，则抛出NullPointerException 。

        //第三种方式 ofNullable 为指定的值创建一个Optional，如果指定的值为null，则返回一个空的Optional。

        // 第一种 创建一个包装对象值为空的Optional对象 一般不使用
        Optional<String> optStr = Optional.empty();
        // 第二种 创建包装对象值非空的Optional对象
        Optional<String> optStr1 = Optional.of("optional");
        // 第三种 创建包装对象值允许为空的Optional对象
        Optional<String> optStr2 = Optional.ofNullable(null);

    }

    public void testMap(){
        // 第二种 创建包装对象值非空的Optional对象
        Optional<String> optStr1 = Optional.of(" 测试 map 方法数据");

        ///获取 optStr1 中的值
        Optional<String> stringOptional = optStr1.map((value) -> value);
        ///optStr1 有值 所以输出的是  optional
        System.out.println(stringOptional.orElse("orElse输出的内容"));
    }


    //=====================================================================================
//    orElse方法，对应的 Optional 如果有值则将其返回，否则返回指定的其它值。

//    orElseGet与orElse方法类似，区别在于得到的默认值，orElse方法将传入的字符串作为默认值，
//    orElseGet方法可以接受Supplier接口的实现用来生成默认值 。

//    orElseThrow如果有值则将其返回，否则抛出supplier接口创建的异常，
//    在orElseThrow中我们可以传入一个lambda表达式或方法，如果值不存在来抛出异常。

    public void testOrElse(){
        // 第一种 创建一个包装对象值为空的Optional对象
        Optional<String> optStr = Optional.empty();
        //optStr 无值 所以输出的是 orElse中的内容 "测试 orelse  "
        //orElse 方法接收的参数是字符串
        System.out.println("测试1  "+optStr.orElse("测试 orelse  "));

        // orElseGet 方法接收的是一个函数方法
        System.out.println("测试2  "+optStr.orElseGet(()->"测试 orElseGet  "));

        // orElseGet 方法接收的是一个函数方法 返回值是一个 Throwable
        System.out.println("测试3  "+optStr.orElseThrow(()-> new IllegalArgumentException("空值")));

        // 第二种 创建包装对象值非空的Optional对象
        Optional<String> optStr1 = Optional.of("optional");
        ///optStr1 有值 所以输出的是  optional
        System.out.println(optStr1.orElse("orElse输出的内容"));
    }






}

package com.dj.boot.controller.redis;

import com.dj.boot.annotation.ConcurrentLock;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.constant.GoodsConstant;
import com.dj.boot.common.redis.RedisService;
import com.dj.boot.controller.UserController;
import com.dj.boot.pojo.User;
import com.dj.boot.service.redis.PublishService;
import com.dj.boot.service.test.TestUserService;
import com.dj.boot.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Api(value = "redis测试操作接口")
@RestController
@RequestMapping("/redis/")
public class RedisTestController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @Autowired
    private PublishService service;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Redisson redisson;

    private static final String JOIN_KEY = "_";

    private static final Logger logger = LoggerFactory.getLogger(RedisTestController.class);



    @ApiOperation(value = "redis 测试redis list  string  hash 数据类型", notes="redis 测试redis list  string  hash 数据类型")
    @PostMapping("testRedis")
    public Response<Object> testRedis(String wbNo){
        Response<Object> response = Response.success();
        String generateKey = redisService.generateKey(GoodsConstant.GOODS_FULL_KEY_PERFIX, "111111", "222222", "333333");
        System.out.println(generateKey);

        boolean isOnly = redisService.setNX(generateKey, wbNo);
        if (!isOnly) {
            redisService.expireKey(generateKey, 60 * 10);
            response.setCode(400);
            response.setMsg("该单据的已经处理过了,不重复消费该消息" + generateKey);
            logger.error("该单据的已经处理过了,不重复消费该消息" + generateKey);
            return response;
        }

        List<User> list = userService.list();
        //String
        redisService.set("user", list.get(0));
        Object user = redisService.get("user");
        redisService.del("user");

        //List
        redisService.lPush("123", list);
        //取出最左边的元素(取出后List中该元素消失)
        Object o = redisService.lPorp("123");
        //取出所有的数据
        List<User> listAll = redisService.getListAll("123");
        redisService.del("123");


        //hash
        redisService.pushHash("1", "456", list);
        Map<Object, Object> hashALL = redisService.getHashALL("1");
        List<User> list3 = redisService.getHash("1", "456");

        response.setData(list);
        return response;
    }

    @ApiOperation(value = "redis 发布消息", notes="redis 发布消息")
    @PostMapping("contextLoads")
    public String contextLoads(@RequestHeader("CSRFToken")String CSRFToken){
        for (int i = 0; i < 10; i++) {
            service.publish("test-topic", "hello~~~" + i);
        }
        return "test";
    }



    @ApiOperation(value = "批量插入  批量获取", notes="批量插入  批量获取")
    @PostMapping("msSet")
    public String msSet(){
        Map<String, String> map = new HashMap<>(16);
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        map.put("k4", "v4");
        redisTemplate.opsForValue().multiSet(map);


        List<String> list = new ArrayList<>();
        list.add("k1");
        list.add("k2");
        list.add("k3");
        List<String> list1 = redisTemplate.opsForValue().multiGet(list);
        list1.forEach(System.out::println);
        return "";
    }




    @ApiOperation(value = "设置库存 ", notes="设置库存")
    @PostMapping("setCount")
    public Boolean setCount(){
        redisTemplate.opsForValue().set("productCount", "20");
        return true;
    }

    @ApiOperation(value = "setNX ", notes="setNX")
    @PostMapping("setNX")
    public Boolean setNX(){
        redis_setNX();
        return redissionTest();

    }

    private Boolean redis_setNX() {
        String userId = UUID.randomUUID().toString();
        String lockKey = "productKey_001";
        try {
            Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(lockKey, userId, 10, TimeUnit.SECONDS);
            if (Objects.isNull(ifAbsent) || !ifAbsent) {
                return false;
            }
            //扣减库存
            stockCount();
        } finally {
            if (Objects.equals(userId, redisTemplate.opsForValue().get(lockKey))) {
                redisTemplate.delete(lockKey);
            }
        }
        return true;
    }

    private Boolean redissionTest() {
        String lockKey = "productKey_001";
        RLock redissonLock = redisson.getLock(lockKey);
        try {
            //加锁
            redissonLock.lock(30, TimeUnit.SECONDS);
            //扣减库存
            stockCount();
        } finally {
            //释放锁
            redissonLock.unlock();
        }
        return true;
    }

    private void stockCount() {
        int count = Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get("productCount")));
        if (count > 0) {
            int result = count - 1;
            redisTemplate.opsForValue().set("productCount", result + "");
            System.out.println("扣减库存  剩余库存： " + result + "");
        } else {
            System.out.println("扣减失败 库存不足");
        }
    }


    //连接到 redis 服务
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());


        jedis.set("runoobkey", "www.runoob.com");
        String runoobkey = jedis.get("runoobkey");



        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()){
            String key = it.next();
            System.out.println(key);
        }


        JedisPool jedisPool = new JedisPool();
        Jedis jedis1 = jedisPool.getResource();

    }

    /**
     * 切面拦截注解  并发锁  支持接口 controller
     * @param user
     * @return
     */
    @ApiOperation(value = "测试redis并发锁ConcurrentLock", notes="测试redis并发锁ConcurrentLock")
    @GetMapping("concurrentLock")
    @ConcurrentLock(prefix = "RECEIPTS_PERFORM_lOCK", bizNo = "#user.id", suffix = "LOCK_1")
    public String concurrentLock(User user) {
        //String s = userService.testConcurrentLock(user);
        return "OK!!!!!!";
    }




}

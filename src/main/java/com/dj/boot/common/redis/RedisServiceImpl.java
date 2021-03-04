package com.dj.boot.common.redis;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
//@org.springframework.stereotype.Service
public class RedisServiceImpl implements RedisService {

    /** 日志 */
    private static Logger logger = LogManager.getLogger(RedisServiceImpl.class.getName());

    @Autowired
    private RedisTemplate redisTemplate;
    private static final String JOIN_KEY = "_";

    //    ======================================String===========================================

    @Override
    public boolean set(String key, Object value) {
        try {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            logger.info("Redis String set {} success.", key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Redis String set {} fail:{}", key, e);
            return false;
        }
    }

    @Override
    public boolean set(String key, Object value, long timeOut) {
        try {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value, timeOut, TimeUnit.SECONDS);
            logger.info("Redis String set {}, timeout:{}/s success.", key, timeOut);
            return true;
        } catch (Exception e) {
            logger.error("Redis String set {} fail:{}", key, e);
            return false;
        }
    }

    @Override
    public <T> T get(String key) {
        try {
            ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
            T result = valueOperations.get(key);
            logger.info("Redis String get {} success.", key);
            return result;
        } catch (Exception e) {
            logger.error("Redis String get {} fail:{}", key, e);
            return null;
        }
    }

    @Override
    public Long incr(String key, long start) {
        try{
            ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
            Long result = valueOperations.increment(key, start);
            return result;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public Double incr(String key, double start) {
        try{
            ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
            Double result = valueOperations.increment(key, start);
            return result;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean append(String key, String value) {
//        try{
//            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//            valueOperations.append(key, new String(redisTemplate.getDefaultSerializer().serialize(value)));
//            return true;
//        } catch (Exception e){
//            return false;
//        }
        return false;
    }

    @Override
    public boolean setNX(String key, Object value) {
        try {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            boolean result = valueOperations.setIfAbsent(key, value);
            return result;
        } catch (Exception e){
            return false;
        }

    }
//    ======================================Hash===========================================

    @Override
    public boolean pushHash(String key, String hashKey, Object hashValue) {
        try {
            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.put(key, hashKey, hashValue);
            logger.info("Redis Hash push [{}-{}] success.", key, hashKey);
            return true;
        } catch (Exception e) {
            logger.error("Redis Hash push [{}-{}] fail:{}", key, hashKey, e);
            return false;
        }
    }

    @Override
    public boolean pushHash(String key, String hashKey, Object hashValue, long timeOut) {
        try {
            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.put(key, hashKey, hashValue);
            redisTemplate.expireAt(key, new Date(System.currentTimeMillis() + (timeOut * 1000)));
            logger.info("Redis Hash push [{}-{}] timeout:{}/s success.", key, hashKey, timeOut);
            return true;
        } catch (Exception e) {
            logger.error("Redis Hash push [{}-{}] fail:{}", key, hashKey, e);
            return false;
        }
    }

    @Override
    public boolean pushHashAll(String key, Map<String, Object> hash) {
        try {
            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.putAll(key, hash);
            logger.info("Redis Hash pushALL {} success.", key);
            return true;
        } catch (Exception e){
            logger.error("Redis Hash pushALL [{}-{}] fail:{}", key, e);
            return false;
        }
    }

    @Override
    public <T> T getHash(String key, String hashKey) {
        try {
            HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
            T result = hashOperations.get(key, hashKey);
            logger.info("Redis Hash getHash [{}-{}] success.", key, hashKey);
            return result;
        } catch (Exception e) {
            logger.error("Redis Hash getHash [{}-{}] fail:{}", key, hashKey, e);
            return null;
        }
    }

    @Override
    public <T> Set<T> getHashKeys(String key) {
        try {
            HashOperations<String, T, ?> hashOperations = redisTemplate.opsForHash();
            Set<T> result = hashOperations.keys(key);
            logger.info("Redis Hash getHashKeys [{}] success.", key);
            return result;
        } catch (Exception e) {
            logger.error("Redis Hash getHashKeys [{}] fail:{}", key, e);
            return null;
        }
    }

    @Override
    public <T> List<T> getHashValues(String key) {
        try {
            HashOperations<String, ?, T> hashOperations = redisTemplate.opsForHash();
            List<T> result = hashOperations.values(key);
            logger.info("Redis Hash getHashValues [{}] success.", key);
            return result;
        } catch (Exception e) {
            logger.error("Redis Hash getHashValues [{}] fail:{}", key, e);
            return null;
        }
    }

    @Override
    public <K, V> Map<K, V> getHashALL(String key) {
        try {
            HashOperations<String, K, V> hashOperations = redisTemplate.opsForHash();
            Cursor<Map.Entry<K, V>> curosr = hashOperations.scan(key, ScanOptions.NONE);
            Map<K, V> result = new HashMap<>();
            while (curosr.hasNext()){
                Map.Entry<K, V> entry = curosr.next();
                result.put(entry.getKey(), entry.getValue());
            }
            logger.info("Redis Hash getHashALL [{}] success.", key);
            return result;
        } catch (Exception e) {
            logger.error("Redis Hash getHashALL [{}] fail:{}", key, e);
            return null;
        }
    }

    @Override
    public boolean delHash(String key, String hashKey) {
        HashOperations<String, String, ?> hashOperations = redisTemplate.opsForHash();
        try {
            hashOperations.delete(key, hashKey);// 删除key存在返回1 不存在返回0
            logger.info("Redis Hash delHash [{}-{}] success.", key, hashKey);
            return true;
        } catch (Exception e){
            logger.error("Redis Hash delHash [{}-{}] fail:{}", key, hashKey, e);
            return false;
        }
    }

    @Override
    public long rPush(String key, List<?> valueList) {
        try {
            ListOperations<String, Object> listListOperations = redisTemplate.opsForList();
            long index = listListOperations.rightPushAll(key, valueList.toArray());
            return index;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long rPush(String key, Object value) {
        try {
            ListOperations<String, Object> listListOperations = redisTemplate.opsForList();
            long index = listListOperations.rightPush(key, value);
            return index;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long lPush(String key, List<?> valueList) {
        try {
            ListOperations<String, Object> listListOperations = redisTemplate.opsForList();
            long index = listListOperations.leftPushAll(key, valueList.toArray());
            return index;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long lPush(String key, Object value) {
        try {
            ListOperations<String, Object> listListOperations = redisTemplate.opsForList();
            long index = listListOperations.leftPush(key, value);
            return index;
        } catch (Exception e) {
            return 0;
        }
    }

   /* @Override
    public long lInsert(String key, BinaryClient.LIST_POSITION where, Object pivot, Object value) {
        return 0;
    }*/

    @Override
    public <T> List<T> lRange(String key, int startIndex, int endIndex) {
        try {
            ListOperations<String, T> listListOperations = redisTemplate.opsForList();
            List<T> list = listListOperations.range(key, startIndex, endIndex);
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> List<T> getListAll(String key) {
        return lRange(key, 0, -1);
    }

    @Override
    public <T> T lPorp(String key) {
        try {
            ListOperations<String, T> listListOperations = redisTemplate.opsForList();
            T result = listListOperations.leftPop(key);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> T rPorp(String key) {
        try {
            ListOperations<String, T> listListOperations = redisTemplate.opsForList();
            T result = listListOperations.rightPop(key);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> T lIndex(String key, int index) {
        try {
            ListOperations<String, T> listListOperations = redisTemplate.opsForList();
            T result = listListOperations.index(key, index);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean lSet(String key, int index, Object vaule) {
        try {
            ListOperations<String, Object> listListOperations = redisTemplate.opsForList();
            listListOperations.set(key, index, vaule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean del(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean checkKeyIsExist(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean expireKey(String key, long timeOut) {
        try {
            boolean flag = redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
            return flag;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean expireAt(String key, Date timeOut) {
        try {
            boolean flag = false;
            // 检查Key是否存在
            if (checkKeyIsExist(key)) {
                flag = redisTemplate.expireAt(key, timeOut);
            }
            return flag;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public DataType type(String key) {
        try {
            if (checkKeyIsExist(key)) {
                return redisTemplate.type(key);
            }
        } catch (Exception e) {
            return DataType.NONE;
        }
        return DataType.NONE;
    }

    @Override
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public <T> Set<T> keys(String pattern) {
        if (pattern.equals("*")){
            return null;
        }
        return redisTemplate.keys(pattern);
    }

    @Override
    public String generateKey(String perfix, String... name) {
        StringBuilder key = new StringBuilder();
        key.append(perfix).append(JOIN_KEY);
        if(name.length > 0) {
            key.append(StringUtils.join(name,JOIN_KEY));
        }
        return key.toString();
    }

}

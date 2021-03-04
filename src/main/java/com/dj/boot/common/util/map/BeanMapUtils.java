package com.dj.boot.common.util.map;

import com.dj.boot.common.excel.exc.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2021-02-02-17-02
 */
public class BeanMapUtils<T> {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public Class<T> clazz;

    public BeanMapUtils(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    /**
     * 将对象属性转化为map结合
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map集合中的数据转化为指定对象的同名属性中
     */
    public static <T> T mapToBean(Map<String, Object> map,Class<T> clazz) throws Exception {
        T bean = clazz.newInstance();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }



    /**
     * map 转 实体类
     * @param map
     * @return
     * @throws Exception
     */
    public T mapToBean(Map<String, Object> map) throws Exception {
        T instance = (T) clazz.getDeclaredConstructor().newInstance();
        if(map != null){
            Field[] declaredFields = instance.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Set<String> mapKeys = map.keySet();
                for (String mapKey : mapKeys) {
                    if(declaredField.getType().toString().contains("Integer"))//判断属性类型 进行转换,map中存放的是Object对象需要转换 实体类中有多少类型就加多少类型,实体类属性用包装类;
                        if(declaredField.getName().equals(mapKey)){
                            declaredField.set(instance,Integer.valueOf(map.get(mapKey).toString()));
                            break;
                        }
                    if(declaredField.getType().toString().contains("String") )//判断属性类型 进行转换;
                        if(declaredField.getName().equals(mapKey)){
                            declaredField.set(instance,map.get(mapKey));
                            break;
                        }
                }
            }

        }
        return instance;
    }
}

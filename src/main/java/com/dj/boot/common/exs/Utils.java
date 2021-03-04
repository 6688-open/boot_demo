package com.dj.boot.common.exs;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 常用工具类
 * */
public class Utils {
    /**
     * 将List的Map指定名称值转换为字符串List
     * */
    public static List<String> getColStringListByName(List<Map<String,String>> input,String key){
        return input.stream().map(stringStringMap -> stringStringMap.get(key)).collect(Collectors.toList());
    }

    public static List<String> getColStringListByNameOrdered(List<LinkedHashMap<String,String>> input,String key){
        return input.stream().map(stringStringMap -> stringStringMap.get(key)).collect(Collectors.toList());
    }

    /**
     * 生成饼图数据
     * @param maps
     * @param name 饼图的标题
     * @return
     */
    public static JsonData generatePipChar(List<Map<String, String>> maps,String name){
        if(ObjectUtils.isEmpty(maps)){
            return new JsonData();
        }
        List<Map<String,Object>> jsons = new ArrayList<>(16);
        for(Map<String, String> map:maps){
            Map<String,Object> json = new HashMap<>(5);
            map.forEach(json::put);
            jsons.add(json);
        }
        return new JsonData(jsons,name);
    }

    /**
     * 生成单柱状图数据
     * @param maps 原始数据 key-列名 value-行值
     * @param name 图标题
     * @return
     */
    public static JsonData generateHistogram(List<Map<String, String>> maps,String name){
        JsonData json = new JsonData();
        List<String> xs = new ArrayList<>(16);
        List<String> data1 = new ArrayList<>(16);
        List<Serie> ySeries = new ArrayList<>(16);
        for(Map<String, String> row : maps){
            for(Map.Entry<String,String> entry : row.entrySet()){
                if("name".equalsIgnoreCase(entry.getKey())){
                    //x轴列名是name
                    xs.add(entry.getValue());
                }else{
                    //Y轴列
                    data1.add(entry.getValue());
                }
            }
        }
        ySeries.add(new Serie(name,data1));
        //X轴数据列表
        json.setXaxis(xs);
        //Y轴数据列表
        json.setSeries(ySeries);
        return json;
    }

    /**
     * 行列转换
     * @param maps
     * @return
     */
    public static List<Map<String, String>> convert(List<Map<String, String>> maps){
        List<Map<String, String>> pips = new ArrayList<>(10);
        maps.get(Constant.ZERO).forEach((key, value)->{
            Map<String, String> pipMap = new HashMap<>(10);
            pipMap.put("name",key);
            pipMap.put("value",value);
            pips.add(pipMap);
        });
        return pips;
    }

    /**
     * 生成双柱状图数据
     * @param maps 原始数据 key-列名 value-行值
     * @param name 图标题
     * @param value1 柱名
     * @param value2 柱名
     * @return
     */
    public static JsonData generateDoubleHistogram(List<Map<String, String>> maps,String name,String value1,String value2){
        List<String> data1 = new ArrayList<>(16);
        List<String> data2 = new ArrayList<>(16);
        List<String> xs = new ArrayList<>(16);
        List<Serie> series = new ArrayList<>(16);
        for(Map<String, String> map : maps){
            data1.add(map.get(value1));
            data2.add(map.get(value2));
            xs.add(map.get("name"));
        }
        series.add(new Serie(value1, data1));
        series.add(new Serie(value2, data2));
        JsonData json = new JsonData();
        json.setSeries(series);
        json.setXaxis(xs);
        Map<String,Object> title = new HashMap<>(1);
        title.put("title",name);
        json.setOtherData(title);
        return json;
    }

    /**
     * 判断数组是否为空
     * @param arr
     * @return
     */
    public static boolean checkIsNotNullArray(String[] arr){
        if(ObjectUtils.isEmpty(arr)){
            return false;
        }else if(StringUtils.isEmpty(arr[Constant.ZERO])|| "null".equals(arr[Constant.ZERO])|| "\"\"".equals(arr[Constant.ZERO])){
            return false;
        }
        return true;
    }
}

package com.dj.boot.common.enums;


import java.util.*;

/**
 * @描述:TB配置业务标识枚举值
 * @Author:haosijia
 * @Date:2020/10/20 22:22
 */
public enum BusinessSignEnum {
    SIGN_CLOUD_WAREHOUSE(1, "云仓"),
    SIGN_ECONOMY_WAREHOUSE(2, "小件经济仓"),
    SIGN_COLD_CHAIN(3, "冷链"),
    SIGN_LARGE_ECONOMY_WAREHOUSE(4, "大件经济仓"),
    ;

    private static Map<Integer, BusinessSignEnum> SIGN_MAP = new HashMap<Integer, BusinessSignEnum>();

    static {
        for (BusinessSignEnum em : EnumSet.allOf(BusinessSignEnum.class)) {
            SIGN_MAP.put(em.code, em);
        }
    }

    /**
     * 业务标识编码
     */
    private Integer code;
    /**
     * 业务标识名称
     */
    private String name;

    BusinessSignEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 通过编码获取业务标识名称
     */
    public static String getBusinessSignNameByCode(Integer code) {
        if (code == null) {
            return null;
        }
        if (SIGN_MAP.get(code) == null) {
            return null;
        }
        return SIGN_MAP.get(code).getName();

    }

    /**
     * 获取业务标识列表
     */
    public static List<Map<String, String>> getBusinessSignList() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        for (Map.Entry<Integer, BusinessSignEnum> entry : SIGN_MAP.entrySet()) {
            map = new HashMap<String, String>();
            map.put("code", String.valueOf(entry.getKey()));
            map.put("name", entry.getValue().getName());
            result.add(map);
        }
        return result;
    }

    public static void main(String[] args) {
        List<Map<String, String>> businessSignList = BusinessSignEnum.getBusinessSignList();
        for (Map<String, String> stringStringMap : businessSignList) {
            stringStringMap.get("code");
            stringStringMap.get("name");
        }
        String name  = BusinessSignEnum.getBusinessSignNameByCode(2);
    }

}

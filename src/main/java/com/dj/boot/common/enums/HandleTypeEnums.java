package com.dj.boot.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口处理类型枚举
 **/
public enum HandleTypeEnums {
    STOCK_PAGE_QUERY("STOCK_PAGE_QUERY","库存查询","stockPageQueryHandler"),
    USER_PAGE_QUERY("USER_PAGE_QUERY","用户查询","userPageQueryHandler"),
    PEOPLE_PAGE_QUERY("PEOPLE_PAGE_QUERY","人员查询","pepolePageQueryHandler"),

            ;

    public static final Map<String, HandleTypeEnums> TYPE2ENUM_MAP = new HashMap<String, HandleTypeEnums>();

    static {
        for (HandleTypeEnums e : EnumSet.allOf(HandleTypeEnums.class)) {
            TYPE2ENUM_MAP.put(e.getType(),e);
        }
    }

    HandleTypeEnums(String type, String info, String handlerName) {
        this.type = type;
        this.info = info;
        this.handlerName = handlerName;
    }

    private final String type;
    private final String info;
    private final String handlerName;

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public String getHandlerName() {
        return handlerName;
    }}

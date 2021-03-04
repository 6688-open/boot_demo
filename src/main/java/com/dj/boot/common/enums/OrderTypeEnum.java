package com.dj.boot.common.enums;


import com.dj.boot.common.enums.base.BaseOptionEnum;
import com.dj.boot.common.enums.base.ValidEnum;
import com.dj.boot.common.util.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 调拨入库
 * 归还入库
 * 回收入库
 * 页面该字段默认展示为  调拨入库；
 */
public enum OrderTypeEnum implements BaseOptionEnum, ValidEnum {

    ALLOC(1, "调拨入库"),
    RETURN(2, "归还入库"),
    RECYCLED(3, "回收入库"),
    ;

    private Integer code;
    private String name;

    OrderTypeEnum() {
    }

    OrderTypeEnum(Integer code, String name) {
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




    public static Map<Integer, String> ENUM_MAP = new HashMap<>();


    public static Map<Integer, OrderTypeEnum> MAP = new HashMap<>();

    public static final List<Option> OPTIONS = new ArrayList<Option>();


    public static String parse(Integer code) {
        OrderTypeEnum[] array = OrderTypeEnum.values();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getCode() == code) {
                return array[i].getName();
            }
        }
        return "";
    }

    public static Integer parse(String name) {
        OrderTypeEnum[] array = OrderTypeEnum.values();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getName().equals(name)) {
                return array[i].getCode();
            }
        }
        return null;
    }

    static {
        for (OrderTypeEnum e : EnumSet.allOf(OrderTypeEnum.class)) {
            Option op = new Option();
            op.setCode(String.valueOf(e.code));
            op.setName(e.getName());
            OPTIONS.add(op);
            ENUM_MAP.put(e.code, e.name);
        }
    }


    static {
        Iterator i$ = EnumSet.allOf(OrderTypeEnum.class).iterator();

        while(i$.hasNext()) {
            OrderTypeEnum e = (OrderTypeEnum)i$.next();
            MAP.put(e.code, e);
        }

    }

    /**
     * 用于统一枚举 维护 下拉框
     * @return
     */
    @Override
    public List<Option> getOptionList() {
        return OPTIONS;
    }

    /**
     * 校验参数   比如传来的参数类型是否可以找到
     * @param code
     * @return
     */
    @Override
    public boolean isValidCode(Object code) {
        if (code == null) {
            return Boolean.FALSE;
        }
        return MAP.containsKey(code);
    }






    public static void main(String[] args) {
        OrderTypeEnum statusEnum = MAP.get(1);
        boolean validCode = statusEnum.isValidCode(9);

        OrderTypeEnum[] performTypeEnums = OrderTypeEnum.values();
        List<String> list = Arrays.stream(performTypeEnums).map(OrderTypeEnum::getName).collect(Collectors.toList());
        LogUtils.info("success");

        List<Option> OPTIONS  = OrderTypeEnum.OPTIONS;
        for (Option option : OPTIONS) {
            System.out.println(option.getCode());
            System.out.println(option.getName());
        }
    }
}

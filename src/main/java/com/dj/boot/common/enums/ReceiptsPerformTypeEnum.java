package com.dj.boot.common.enums;


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
 * @author ext.wangjia
 */
public enum ReceiptsPerformTypeEnum {

    ALLOC(1, "调拨入库"),
    RETURN(2, "归还入库"),
    RECYCLED(3, "回收入库"),
    ;

    private Integer code;
    private String name;

    ReceiptsPerformTypeEnum() {
    }

    ReceiptsPerformTypeEnum(Integer code, String name) {
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


    public static Map<Integer, ReceiptsPerformTypeEnum> MAP = new HashMap<>();

    public static final List<Option> OPTIONS = new ArrayList<Option>();


    public static String parse(Integer code) {
        ReceiptsPerformTypeEnum[] array = ReceiptsPerformTypeEnum.values();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getCode().equals(code)) {
                return array[i].getName();
            }
        }
        return "";
    }

    public static Integer parse(String name) {
        ReceiptsPerformTypeEnum[] array = ReceiptsPerformTypeEnum.values();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getName().equals(name)) {
                return array[i].getCode();
            }
        }
        return null;
    }

    static {
        for (ReceiptsPerformTypeEnum e : EnumSet.allOf(ReceiptsPerformTypeEnum.class)) {
            Option op = new Option();
            op.setCode(String.valueOf(e.code));
            op.setName(e.getName());
            OPTIONS.add(op);
            ENUM_MAP.put(e.code, e.name);
        }
    }


    static {
        Iterator i$ = EnumSet.allOf(ReceiptsPerformTypeEnum.class).iterator();

        while(i$.hasNext()) {
            ReceiptsPerformTypeEnum e = (ReceiptsPerformTypeEnum)i$.next();
            MAP.put(e.code, e);
        }

    }



    @GetNameByCode
    public static String getNameByCode(Object object) {
        if (object == null) {
            return "";
        }
        int code;
        try {
            code = Integer.parseInt(object.toString());
        } catch (Exception e) {
            return "";
        }
        ReceiptsPerformTypeEnum statusEnum = MAP.get(code);
        if (statusEnum == null) {
            return "";
        } else {
            return statusEnum.getName();
        }
    }

    @GetCodeByName
    public static Integer getCodeByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        Integer code = parse(name);
        if (null != code) {
            return code;
        } else {
            return null;
        }

    }


    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface GetCodeByName {

    }
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface GetNameByCode {

    }



    public static void main(String[] args) {
        ReceiptsPerformTypeEnum statusEnum = MAP.get(1);

        ReceiptsPerformTypeEnum[] performTypeEnums = ReceiptsPerformTypeEnum.values();
        List<String> list = Arrays.stream(performTypeEnums).map(ReceiptsPerformTypeEnum::getName).collect(Collectors.toList());
        LogUtils.info("success");

        List<Option> OPTIONS  = ReceiptsPerformTypeEnum.OPTIONS;
        for (Option option : OPTIONS) {
            System.out.println(option.getCode());
            System.out.println(option.getName());
        }
    }
}

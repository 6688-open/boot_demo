package com.dj.boot.common.enums;

import java.util.*;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.enums
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-17-10-52
 */
public enum LockType {

    LOCK(1, "lock1"),
    LOCK_TEST(2, "lock2"),
            ;

    private Integer code;
    private String name;

    LockType() {
    }

    LockType(Integer code, String name) {
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


    public static Map<Integer, LockType> MAP = new HashMap<>();

    public static final List<Option> OPTIONS = new ArrayList<Option>();


    public static String parse(Integer code) {
        LockType[] array = LockType.values();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getCode().equals(code)) {
                return array[i].getName();
            }
        }
        return "";
    }

    public static Integer parse(String name) {
        LockType[] array = LockType.values();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getName().equals(name)) {
                return array[i].getCode();
            }
        }
        return null;
    }

    static {
        for (LockType e : EnumSet.allOf(LockType.class)) {
            Option op = new Option();
            op.setCode(String.valueOf(e.code));
            op.setName(e.getName());
            OPTIONS.add(op);
            ENUM_MAP.put(e.code, e.name);
        }
    }


    static {
        Iterator i$ = EnumSet.allOf(LockType.class).iterator();

        while(i$.hasNext()) {
            LockType e = (LockType)i$.next();
            MAP.put(e.code, e);
        }

    }
}

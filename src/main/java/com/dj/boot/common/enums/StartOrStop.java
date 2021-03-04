package com.dj.boot.common.enums;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;

/**
 * 主数据状态 -1初始 0启用 1停用
 */
public enum StartOrStop  {

    Init((byte) -1, "初始"),
    Start((byte) 0, "启用"),
    Stop((byte) 1, "停用");


    public static final Map<Byte, StartOrStop> StartStatusMAP;
    public static final List<Option> StartOrStop_opList = new ArrayList<Option>();

    private Byte code;
    private String name;

    public static StartOrStop getByCode(Byte code) {
        return StartStatusMAP.get(code);
    }
    
    @StartOrStopConvert
    public static String getNameByCode(Object object) {
        if(object == null) {
            return "";
        }
        Byte code;
        try {
            code = Byte.valueOf(object.toString());
        } catch (Exception e) {
            return "";
        }
        return StartStatusMAP.get(code) == null ? "" : StartStatusMAP.get(code).getName();
    }

    StartOrStop(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static {
        StartStatusMAP = new HashMap();
        Iterator i$ = EnumSet.allOf(StartOrStop.class).iterator();
        while (i$.hasNext()) {
            StartOrStop e = (StartOrStop) i$.next();
            StartStatusMAP.put(e.getCode(), e);
            Option op = new Option();
            op.setCode(e.getCode().toString());
            op.setName(e.name);
            StartOrStop_opList.add(op);
        }
    }

    public List<Option> getOptionList() {
        return StartOrStop_opList;
    }
    
    public boolean isValidCode(Object code) {
        if(code instanceof String) {
            return StartStatusMAP.containsKey(Byte.valueOf((String)code));
        } else if(code instanceof Integer) {
            return StartStatusMAP.containsKey(((Integer)code).byteValue());
        } else {
            return StartStatusMAP.containsKey(code);
        }
    }
    
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface StartOrStopConvert {
    }
}

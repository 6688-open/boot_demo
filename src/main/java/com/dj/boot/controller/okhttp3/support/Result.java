package com.dj.boot.controller.okhttp3.support;

public class Result<T> {
    public static final int SUCCESS = 200;
    public static final int BAD_REQ = 400;
    public static final int ERROR = 500;
    private String desc;
    private int code;
    private T data;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }





}

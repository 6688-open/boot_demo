package com.dj.boot.aspect.util;

import lombok.Data;

/**
 * @ClassName BaseResponse
 * @Description TODO
 * @Author wj
 * @Date 2019/12/20 16:35
 * @Version 1.0
 **/
@Data
public class BaseResponse <T>{
    private boolean result = true;
    private String message;
    private int code;
    private T data;

    public BaseResponse() {
    }

    public void setErrorMessage(String message) {
        this.result = false;
        this.message = message;
    }
}

package com.dj.boot.btp.exception;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.btp.exception
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-09-29-14-43
 */
public class SomsBizException extends RuntimeException{
    private final short lever;
    public String code;

    public SomsBizException() {
        this.lever = 0;
        this.code = "";
    }

    public SomsBizException(String code) {
        this(code, (String)null, (Throwable)null);
    }

    public SomsBizException(Throwable cause) {
        this((String)null, (String)null, cause);
    }

    public SomsBizException(String code, String msg) {
        this(code, msg, (Throwable)null);
    }

    public SomsBizException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.lever = 0;
        this.code = "";
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

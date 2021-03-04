package com.dj.boot.btp.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @Date 2019-11-29 15:37
 */
public class BizException extends RuntimeException {

    /**
     * 自定义异常编号
     */
    private int code;

    public BizException(int code, String message) {
        super(message);
        this.code = code;

    }

    public BizException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}

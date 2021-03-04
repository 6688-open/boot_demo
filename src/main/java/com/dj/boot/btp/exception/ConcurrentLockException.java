package com.dj.boot.btp.exception;

/**
 * 时间转换异常
 *
 * @author wj
 * @Date 2019-10-24 9:35
 */
public class ConcurrentLockException extends BizException {

    public ConcurrentLockException(int code, String message) {
        super(code, message);
    }

    public ConcurrentLockException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

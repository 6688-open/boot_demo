package com.dj.boot.common.base;
/**
 * 
 * OperationResult
 */
public class Response<T> extends BaseResponse<T>  {
    private static final long serialVersionUID = 1L;

    public Response(T data) {
        super(data);
    }

    public Response(Integer code, String msg) {
        super(code, msg);
    }

    public Response(Integer code, String msg, T data, Throwable exception) {
        super(code, msg, data, exception);
    }

    public Response(Integer code, String msg, T data) {
        super(code, msg, data);
    }

    /** @deprecated */
    @Deprecated
    public Response() {
    }

    public static <T> Response<T> success() {
        return new Response((Object)null);
    }

    public static <T> Response<T> success(T data) {
        return new Response(data);
    }

    public static <T> Response<T> success(String msg) {
        return new Response(200, msg);
    }

    public static <T> Response<T> success(Integer code, String msg) {
        return new Response(code, msg);
    }

    public static <T> Response<T> success(Integer code, String msg, T data) {
        return new Response(code, msg, data);
    }

    public static <T> Response<T> error(Integer code, String msg) {
        return new Response(code, msg);
    }

    public static <T> Response<T> error(Integer code, String msg, T data) {
        return new Response(code, msg, data);
    }

    public static <T> Response<T> error(Integer code, String msg, Throwable t) {
        return new Response(code, msg, (Object)null, t);
    }

    public static <T> Response<T> error(Integer code, String msg, T data, Throwable t) {
        return new Response(code, msg, data, t);
    }
}
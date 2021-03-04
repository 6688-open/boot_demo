package com.dj.boot.configuration.okhttp3.common;


import com.dj.boot.configuration.okhttp3.domain.CommonTransport;

public interface CommonSynchronousService {

    /**
     * 同步获取外部接口值统一方法
     * @param commonTransport
     * @return Map
     * @throws Exception
     */
    Object handler(CommonTransport commonTransport) throws Exception;
}

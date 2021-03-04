package com.dj.boot.configuration.okhttp3.provider;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;

/**
 * OkHttpClientFactoryBean,产生OkHttpClient，非单例。
 * 仅设置基本参数,其他参数待需要时添加
 */
public class OkHttpClientFactoryBean implements FactoryBean<OkHttpClient>, InitializingBean {

    private long connectTimeout=5L;
    private long readTimeout=5L;
    private long writeTimeout=5L;
    private boolean retryOnConnectionFailure=true;
    private OkHttpClient okHttpClient;


    @Override
    public OkHttpClient getObject() throws Exception {
        return okHttpClient;
    }

    @Override
    public Class<?> getObjectType() {
        return OkHttpClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Long getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Long writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public boolean isRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }

    public void setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
        this.retryOnConnectionFailure = retryOnConnectionFailure;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(connectTimeout,TimeUnit.SECONDS);
        builder.readTimeout(readTimeout,TimeUnit.SECONDS);
        builder.writeTimeout(writeTimeout,TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(retryOnConnectionFailure);
        okHttpClient=builder.build();
    }
}

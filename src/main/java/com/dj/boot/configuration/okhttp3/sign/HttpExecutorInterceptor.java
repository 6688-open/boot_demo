package com.dj.boot.configuration.okhttp3.sign;

public interface HttpExecutorInterceptor {

    void preExecute(HttpExecutionContext.Builder builder, String body, String header)throws Exception;

    void postExecute(HttpExecutionContext context, HttpExecutionResult result)throws Exception;
}

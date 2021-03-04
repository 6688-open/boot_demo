package com.dj.boot.configuration.okhttp3.interceptor.support;


import com.dj.boot.configuration.okhttp3.sign.HttpExecutionContext;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutionResult;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutorInterceptor;

public abstract class ExecutorInterceptorAdapter implements HttpExecutorInterceptor {
    @Override
    public void preExecute(HttpExecutionContext.Builder builder, String body, String header)throws Exception {

    }

    @Override
    public void postExecute(HttpExecutionContext context, HttpExecutionResult result)throws Exception {

    }
}

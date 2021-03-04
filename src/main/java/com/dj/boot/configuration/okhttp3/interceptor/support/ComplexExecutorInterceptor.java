package com.dj.boot.configuration.okhttp3.interceptor.support;

import com.dj.boot.configuration.okhttp3.sign.HttpExecutionContext;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutionResult;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutorInterceptor;
import org.apache.log4j.Logger;

public class ComplexExecutorInterceptor implements HttpExecutorInterceptor {
    private static final Logger log = Logger.getLogger(ComplexExecutorInterceptor.class);
    private HttpExecutorInterceptor prevInterceptor;
    private HttpExecutorInterceptor nextInterceptor;
    @Override
    public void preExecute(HttpExecutionContext.Builder builder, String body, String header) throws Exception {
        log.info("执行组合拦截器["+ComplexExecutorInterceptor.class.getCanonicalName()+"]PreExecute");
        log.info("执行组合拦截器prevInterceptor["+prevInterceptor.getClass().getCanonicalName()+"]");
        log.info("执行组合拦截器nextInterceptor["+nextInterceptor.getClass().getCanonicalName()+"]");
        prevInterceptor.preExecute(builder,body,header);
        nextInterceptor.preExecute(builder, body, header);
    }

    @Override
    public void postExecute(HttpExecutionContext context, HttpExecutionResult result) throws Exception {
        log.info("执行组合拦截器["+ComplexExecutorInterceptor.class.getCanonicalName()+"]postExecute");
        log.info("执行组合拦截器prevInterceptor["+prevInterceptor.getClass().getCanonicalName()+"]");
        log.info("执行组合拦截器nextInterceptor["+nextInterceptor.getClass().getCanonicalName()+"]");
        prevInterceptor.postExecute(context,result);
        nextInterceptor.postExecute(context,result);
    }

    public HttpExecutorInterceptor getPrevInterceptor() {
        return prevInterceptor;
    }

    public void setPrevInterceptor(HttpExecutorInterceptor prevInterceptor) {
        this.prevInterceptor = prevInterceptor;
    }

    public HttpExecutorInterceptor getNextInterceptor() {
        return nextInterceptor;
    }

    public void setNextInterceptor(HttpExecutorInterceptor nextInterceptor) {
        this.nextInterceptor = nextInterceptor;
    }
}

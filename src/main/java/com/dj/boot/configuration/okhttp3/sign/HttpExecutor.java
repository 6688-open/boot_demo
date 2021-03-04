package com.dj.boot.configuration.okhttp3.sign;

public interface HttpExecutor {

    byte[] execute(HttpExecutionContext context)throws Exception;
}

package com.dj.boot.configuration.okhttp3.executor;


import com.dj.boot.configuration.okhttp3.sign.HttpExecutionContext;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutor;
import okhttp3.*;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;

public class PostFormBodyExecutor implements HttpExecutor {

    private static final Logger log = Logger.getLogger(PostFormBodyExecutor.class);
    private OkHttpClient okHttpClient;
    @Override
    public byte[] execute(HttpExecutionContext context)throws Exception{
        log.info("填充请求参数:["+context.toString()+"]");
        final Request.Builder builder = new Request.Builder();
        builder.url(context.url());
        builder.headers(Headers.of(context.headers()));
        final FormBody.Builder bodyBuilder=new FormBody.Builder();
        if(context.params()!=null&&!context.params().isEmpty()){
            final Set<Map.Entry<String, Object>> params = context.params().entrySet();
            for (Map.Entry<String, Object> param : params) {
                bodyBuilder.addEncoded(param.getKey(), param.getValue().toString());
            }
        }
        final Request request = builder.post(bodyBuilder.build()).build();
        log.info("发起请求:["+context.toString()+"]");
        final Response response = okHttpClient.newCall(request).execute();
        byte[] returnByte = null;
        if (response.body()!=null){
            returnByte = response.body().bytes();
        }
        //InterfaceLogCache.print(String.valueOf(context.extras().get(HttpExecutionContext.BIZ_NO)),context.url(),returnByte!=null?new String(returnByte,"utf-8"):"null",context.params(),context.headers());
        final StringBuilder message = new StringBuilder();
        message.append(context.toString());
        message.append(",");
        message.append(response.toString());
        if (response.isSuccessful()) {
            log.info("请求调用成功:"+message.toString());
             return returnByte;
        }else {
            message.append(",Body[");
            message.append(returnByte!=null?new String(returnByte,"utf-8"):"null");
            message.append("]");
        }
        log.error("请求调用失败:"+message.toString());
        throw new RuntimeException("请求调用失败:"+message.toString());
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }
}

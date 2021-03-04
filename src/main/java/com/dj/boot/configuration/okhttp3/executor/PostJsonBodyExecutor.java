package com.dj.boot.configuration.okhttp3.executor;

import com.dj.boot.configuration.okhttp3.serializer.ParamsSerializer;
import com.dj.boot.configuration.okhttp3.serializer.support.DefaultJsonParamsSerializer;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutionContext;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutor;
import okhttp3.*;
import org.apache.log4j.Logger;

import java.util.Map;

public class PostJsonBodyExecutor implements HttpExecutor {
    private static final Logger log = Logger.getLogger(PostJsonBodyExecutor.class);
    private static final ParamsSerializer SERIALIZER = new DefaultJsonParamsSerializer();
    private OkHttpClient okHttpClient;
    private Map<Object,ParamsSerializer> serializers;
    @Override
    public byte[] execute(HttpExecutionContext context) throws Exception {
        log.info("填充请求参数:["+context.toString()+"]");
        final Request.Builder builder = new Request.Builder();
        builder.url(context.url());
        builder.headers(Headers.of(context.headers()));
        final Object serializerKey = context.extras().get(HttpExecutionContext.PARAMS_SERIALIZER_KEY);
        final ParamsSerializer serializer = getSerializer(serializerKey);
        final String json = serializer.serialize(context.params());
        final RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        final Request request = builder.post(body).build();
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

    private ParamsSerializer getSerializer(Object key){
        if(serializers==null||serializers.isEmpty()){
            return SERIALIZER;
        }
        final  ParamsSerializer serializer = serializers.get(key);
        return serializer !=null?serializer:SERIALIZER;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public void setSerializers(Map<Object, ParamsSerializer> serializers) {
        this.serializers = serializers;
    }
}

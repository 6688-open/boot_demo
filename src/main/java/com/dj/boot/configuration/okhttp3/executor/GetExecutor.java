package com.dj.boot.configuration.okhttp3.executor;

import com.dj.boot.configuration.okhttp3.sign.HttpExecutionContext;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wj
 * @description get请求执行类
 * @date 2019-05-10 16:11
 **/
public class GetExecutor implements HttpExecutor {

    /**
     * log
     */
    public static final Logger logger = LogManager.getLogger(GetExecutor.class);

    /**
     * 执行类
     */
    private OkHttpClient okHttpClient;

    @Override
    public byte[] execute(HttpExecutionContext context) throws Exception {
        logger.info("开始执行GET请求");
        Request.Builder builder = new Request.Builder();
        builder.url(context.url());
        Request request = builder.get().build();
        Response response = null;
        byte[] returnByte = null;
        try{
            logger.info("发起请求:["+context.toString()+"]");
            response = okHttpClient.newCall(request).execute();
            if (response.body()!=null){
                returnByte = response.body().bytes();
            }
        } catch (Exception e){
            logger.error("请求调用失败:"+response.toString());
            throw new RuntimeException("请求调用失败:"+response.toString());
        }
        logger.info("GET请求调用成功:"+response.toString());
        return returnByte;

    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }
}

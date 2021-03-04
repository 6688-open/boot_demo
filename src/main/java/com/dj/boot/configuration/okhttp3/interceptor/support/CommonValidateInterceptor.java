package com.dj.boot.configuration.okhttp3.interceptor.support;

import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutionContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

public class CommonValidateInterceptor extends ExecutorInterceptorAdapter {
    private static final Logger log = Logger.getLogger(CommonValidateInterceptor.class);
    @Override
    public void preExecute(HttpExecutionContext.Builder builder, String body, String header) throws Exception {
        log.info("执行验证拦截器["+CommonValidateInterceptor.class.getCanonicalName()+"}");
        final Map<String, String> parsedHeader = JsonUtil.fromJson(header, new TypeReference<HashMap<String, String>>() {
        });
        final String url=parsedHeader.get(HttpExecutionContext.URL_KEY);
        if(StringUtils.isEmpty(url)){
            throw new RuntimeException("缺失请求路径参数["+HttpExecutionContext.URL_KEY+"]");
        }
        builder.url(url);
        //executor=Method_BodyType 如 POST_JSON
        final String executor = parsedHeader.get(HttpExecutionContext.EXECUTOR_KEY);
        if(StringUtils.isEmpty(executor)){
            throw new RuntimeException("缺失执行器参数["+HttpExecutionContext.EXECUTOR_KEY+"]");
        }
        final String[] splits=executor.split("_");
        builder.method(HttpExecutionContext.Method.valueOf(splits[0].toUpperCase()));
        builder.bodyType(HttpExecutionContext.BodyType.valueOf(splits[1].toUpperCase()));
        final String serializer = parsedHeader.get(HttpExecutionContext.PARAMS_SERIALIZER_KEY);
        if(StringUtils.isNotEmpty(serializer)){
            builder.addExtra(HttpExecutionContext.PARAMS_SERIALIZER_KEY,serializer);
        }
        builder.addExtra(HttpExecutionContext.BIZ_NO,String.valueOf(parsedHeader.get(HttpExecutionContext.BIZ_NO)));
    }

    public static void main(String[] args) {
        System.out.println();//"null"
    }

}

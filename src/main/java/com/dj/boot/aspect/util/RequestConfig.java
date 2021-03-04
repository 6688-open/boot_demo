package com.dj.boot.aspect.util;

import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName RequestConfig
 * @Description TODO
 * @Author wj
 * @Date 2019/12/20 16:29
 * @Version 1.0
 **/
public class RequestConfig {
    public static final String HEADER_PARAMETER_ANTI_CSRF_TOKEN = "CSRFToken";
    public static final List<String> REQUEST_METHODS_NEED_ANTI_CSRF_TOKEN;

    public RequestConfig() {
    }

    static {
        REQUEST_METHODS_NEED_ANTI_CSRF_TOKEN = Arrays.asList(HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name());
    }
}

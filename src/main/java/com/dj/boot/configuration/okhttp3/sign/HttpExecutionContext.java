package com.dj.boot.configuration.okhttp3.sign;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class HttpExecutionContext implements Serializable {
    public static final String URL_KEY="url";
    public static final String EXECUTOR_KEY="executor";
    public static final String EXECUTION_TYPE_KEY ="exec";
    public static final String PARAMS_SERIALIZER_KEY="serializer";
    public static final String BIZ_NO="bizNoSign";
    public HttpExecutionContext(Method method, String url, BodyType bodyType,
                                Map<String, Object> params,
                                Map<String, String> headers,
                                Map<String, Object> extras) {
        this.method = method;
        this.url = url;
        this.bodyType = bodyType;
        this.params = params;
        this.headers = headers;
        this.extras = extras;
    }

    private Method method;

    private String url;

    private BodyType bodyType;

    private Map<String, Object> params;

    private Map<String, String> headers;

    private Map<String, Object> extras;


    public Method method() {
        return method;
    }

    public String url() {
        return url;
    }

    public BodyType bodyType() {
        return bodyType;
    }

    public Map<String, Object> params() {
        return params;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public Map<String, Object> extras() {
        return extras;
    }

    @Override
    public String toString() {
        return "HttpExecutionContext{" +
                "method=" + method +
                ", url='" + url + '\'' +
                ", bodyType=" + bodyType +
                ", params=" + params +
                ", headers=" + headers +
                ", extras=" + extras +
                '}';
    }

    public static Builder of() {
        return new Builder();
    }

    public static class Builder {
        private Method method;

        private String url;

        private BodyType bodyType;

        private Map<String, Object> params = new HashMap<String, Object>();

        private Map<String, String> headers = new HashMap<String, String>();

        private Map<String, Object> extras = new HashMap<String, Object>();

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder method(String method) {
            return method(Method.valueOf(method));
        }

        public Builder method(int method) {
            return method(Method.parseOf(method));
        }

        public Builder bodyType(BodyType bodyType) {
            this.bodyType = bodyType;
            return this;
        }

        public Builder bodyType(int bodyType) {
            return bodyType(BodyType.parseOf(bodyType));
        }

        public Builder params(Map<String, Object> params) {
            this.params.putAll(params);
            return this;
        }

        public Builder addParam(String name, Object value) {
            this.params.put(name, value);
            return this;
        }

        public Builder extras(Map<String, Object> extras) {
            this.extras.putAll(extras);
            return this;
        }

        public Builder addExtra(String name, Object value) {
            this.extras.put(name, value);
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public Builder addHeader(String name, String value) {
            this.headers.put(name, value);
            return this;
        }

        public HttpExecutionContext build() {
            return new HttpExecutionContext(method, url, bodyType, params, headers,extras);
        }
    }

    public enum Method {
        GET(1, "GET"),
        POST(2, "POST"),
        PUT(3, "PUT"),
        DELETE(4, "DELETE");
        private int code;
        private String name;


        Method(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static Method parseOf(int code) {
            final EnumSet<Method> enumSet = EnumSet.allOf(Method.class);
            for (Method method : enumSet) {
                if (method.code == code) {
                    return method;
                }
            }
            throw new IllegalArgumentException("无效的请求方法code[" + code + "]");
        }

    }

    public enum BodyType {
        FORM(1, "表单请求体"),
        JSON(2, "JSON请求体");
        private int code;
        private String name;

        BodyType(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static BodyType parseOf(int code) {
            final EnumSet<BodyType> enumSet = EnumSet.allOf(BodyType.class);
            for (BodyType type : enumSet) {
                if (type.code == code) {
                    return type;
                }
            }
            throw new IllegalArgumentException("无效的请求体类型code[" + code + "]");
        }
    }


}

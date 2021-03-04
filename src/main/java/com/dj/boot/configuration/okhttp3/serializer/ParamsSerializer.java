package com.dj.boot.configuration.okhttp3.serializer;

import java.util.Map;

public interface ParamsSerializer {
    String serialize(Map<String, Object> params);
}

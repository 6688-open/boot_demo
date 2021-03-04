package com.dj.boot.configuration.okhttp3.serializer.support;


import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.serializer.ParamsSerializer;

import java.util.Map;

public class DefaultJsonParamsSerializer  implements ParamsSerializer {
    @Override
    public String serialize(Map<String, Object> params) {
        return JsonUtil.toJson(params);
    }
}

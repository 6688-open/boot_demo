package com.dj.boot.configuration.okhttp3.serializer.support;

import com.dj.boot.configuration.okhttp3.serializer.ParamsSerializer;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 固定参数取值
 */
public class FixedValueParamSerializer implements ParamsSerializer {
    /**
     * 序列化参数key
     */
    public final static String FIXED_VALUE = "FIXED_VALUE";

    @Override
    public String serialize(Map<String, Object> params) {
        Object result;
        if (MapUtils.isEmpty(params) || (result = params.get(FIXED_VALUE)) == null) {
            return StringUtils.EMPTY;
        }
        return StringUtils.defaultIfBlank(result.toString(), StringUtils.EMPTY);
    }
}

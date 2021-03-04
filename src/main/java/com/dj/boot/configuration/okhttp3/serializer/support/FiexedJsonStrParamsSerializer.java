package com.dj.boot.configuration.okhttp3.serializer.support;


import com.dj.boot.btp.exception.BizException;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.serializer.ParamsSerializer;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

/**
 * Created by fangkun on 2018/7/4.
 */
public class FiexedJsonStrParamsSerializer implements ParamsSerializer {
    /**
     * 序列化参数key
     */
    public final static String JSON_PARAM_SERI_KEY = FixedValueParamSerializer.FIXED_VALUE;


    @Override
    public String serialize(Map<String, Object> params) {
        String serResult = null;
        if(MapUtils.isEmpty(params)){
            return serResult;
        }
        Object paramValue = params.get(JSON_PARAM_SERI_KEY);
        if(paramValue == null){
            return serResult;
        }
        if(paramValue instanceof String){
            serResult = paramValue.toString();
        }else {
            throw new BizException(600, "字符串序列化不支持非字符串序列化,参数:" + JsonUtil.toJson(params));
        }
        return serResult;
    }
}

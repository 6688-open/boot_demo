package com.dj.boot.configuration.okhttp3.interceptor;

import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.interceptor.support.DefaultHandleInterceptor;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutionContext;
import com.dj.boot.configuration.okhttp3.sign.HttpExecutionResult;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 快递鸟即时接口
 */
public class KdnApiInterceptor extends DefaultHandleInterceptor {

    private static final Logger log = LogManager.getLogger(KdnApiInterceptor.class);
    private static final String OBTAIN_TRACE = "1002";
    private static final String SUBSCRIBE_TRACE = "1008";
    private static final String SURPLUS_ORDER = "1127";
    private static final String GET_WAYBILL = "1007";
    private static final String SUCCESS_CODE = "200";
    private static final String ACQUIRE_TRACE = "8002";

    @Override
    public void preExecute(HttpExecutionContext.Builder builder, String body, String header) throws Exception {
        body = body.replaceAll("[\\+#&<>%]+", " "); //快递鸟规定的特殊字符
        body = body.replaceAll("\\\\+b", " ");
        body = body.replaceAll("\\\\+n", " ");
        final Map<String, Object> parsedBody = JsonUtil.fromJson(body, new TypeReference<HashMap<String, Object>>() {
        });
        final Map<String, Object> parsedHeader = JsonUtil.fromJson(header, new TypeReference<HashMap<String, Object>>() {
        });
        Object appKey = parsedBody.get("APIKey");
        Object eBusinessID = parsedBody.get("EBusinessID");
        Object requestType = parsedBody.get("RequestType");
        Object dataType = parsedBody.get("DataType");
        String requestData = JsonUtil.toJson(parsedBody.get("RequestData"));
        //判断来源取header里面的参数
        if(parsedHeader.containsKey("supplyArgs")&&(Boolean) parsedHeader.get("supplyArgs")){
            appKey = parsedHeader.get("APIKey");
            eBusinessID = parsedHeader.get("EBusinessID");
            requestType = parsedHeader.get("RequestType");
            dataType = parsedHeader.get("DataType");
            requestData = body;
        }

        String dataSign = encrypt(requestData, appKey.toString());
        builder.addParam("RequestData", urlEncoder(requestData));
        builder.addParam("EBusinessID", eBusinessID);
        builder.addParam("RequestType", requestType);
        builder.addParam("DataSign", urlEncoder(dataSign));
        builder.addParam("DataType", dataType);
        //放入请求上下文 处理返回值是使用
        builder.addExtra("RequestType", requestType);
    }


    @Override
    public void postExecute(HttpExecutionContext context, HttpExecutionResult result) throws Exception {
        super.postExecute(context, result);
        final String json = result.getResult();
        final Map<String, Object> resultMap = JsonUtil.fromJson(json, new TypeReference<Map<String, Object>>() {
        });
        final String resultCode = String.valueOf(resultMap.get("code"));
        final Boolean success = (Boolean) resultMap.get("success");
        final String msg = (String) resultMap.get("msg");
        if(!isSuccess(success,resultCode)){
            final Map<String, Object> processed = new HashMap<String, Object>();
            processed.put("code", 200);
            processed.put("msg", msg);
            result.setResult(JsonUtil.toJson(processed));
            return;
        }
        final String requestType = (String) context.extras().get("RequestType");
        if (StringUtils.equals(OBTAIN_TRACE, requestType)) {
            processObtainTrace(result, resultMap);
        } else if (StringUtils.equals(SUBSCRIBE_TRACE, requestType)) {
            processSubscribeTrace(result, resultMap);
        } else if (StringUtils.equals(SURPLUS_ORDER, requestType)) {
            processSurplusOrder(result, resultMap);
        } else if (StringUtils.equals(ACQUIRE_TRACE, requestType)) {
            processAcquireTrace(result, resultMap);
        }
    }

    /**
     * 获取轨迹接口
     *
     * @param result
     * @param resultMap
     */
    private void processAcquireTrace(HttpExecutionResult result, Map<String, Object> resultMap) {
        final Map<String, Object> processed = new HashMap<String, Object>();
        final String msg = (String) resultMap.get("msg");
        processed.put("code", 100);
        processed.put("msg", msg);
        processed.put("result",result.getResult());
        result.setResult(JsonUtil.toJson(processed));
    }

    /**
     * 获取轨迹即时接口
     *
     * @param result
     * @param resultMap
     */
    private void processObtainTrace(HttpExecutionResult result, Map<String, Object> resultMap) {
        final Map<String, Object> processed = new HashMap<String, Object>();
        processed.put("code", 100);
        processed.put("data",result.getResult());
        result.setResult(JsonUtil.toJson(processed));
    }

    /**
     * 订阅接口
     *
     * @param result
     * @param resultMap
     */
    private void processSubscribeTrace(HttpExecutionResult result, Map<String, Object> resultMap) {
        final Map<String, Object> processed = new HashMap<String, Object>();
        processed.put("code", 100);
        result.setResult(JsonUtil.toJson(processed));
    }

    /**
     * 单量剩余接口
     *
     * @param result
     * @param resultMap
     */
    private void processSurplusOrder(HttpExecutionResult result, Map<String, Object> resultMap) {
        final Map<String, Object> surplusOrderMap = (Map<String, Object>) resultMap.get("EorderBalance");
        final Map<String, String> surplusOrderNumMap = new HashMap<String, String>();
        if (surplusOrderMap.get("TotalNum") != null) {
            surplusOrderNumMap.put("totalNum", String.valueOf(surplusOrderMap.get("TotalNum")));
        }
        if (surplusOrderMap.get("AvailableNum") != null) {
            surplusOrderNumMap.put("availableNum", String.valueOf(surplusOrderMap.get("AvailableNum")));
        }
        Map<String, Object> processed = new HashMap<String, Object>();
        String date = JsonUtil.toJson(surplusOrderNumMap);
        processed.put("data", date);
        processed.put("code", 100);
        result.setResult(JsonUtil.toJson(processed));
    }




    private boolean isSuccess(boolean success, String resultCode) {
        return (success || StringUtils.equals(resultCode, SUCCESS_CODE));
    }


    /**
     * MD5加密
     *
     * @param str 内容
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String md5(String str) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes("UTF-8"));
        byte[] result = md.digest();
        StringBuilder sb = new StringBuilder(32);
        for (byte b : result) {
            int val = b & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     *
     * @param str 内容
     * @throws UnsupportedEncodingException
     */
    private String enBase64(String str) throws Exception {
        return Base64.encode(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64编码 解密
     * @param str 内容
     * @throws UnsupportedEncodingException
     */
    private String deBase64(String str) throws Exception {
        byte[] decode = Base64.decode(str);
        return new String(decode);
    }


    /**
     * 处理url特殊参数  加密处理
     * @param str
     * @return
     * @throws Exception
     */
    private String urlEncoder(String str) throws Exception {
        return URLEncoder.encode(str, "UTF-8");
    }

    /**
     * 处理url特殊参数  解密处理
     * @param str
     * @return
     * @throws Exception
     */
    private String urlDecoder(String str) throws Exception {
        return URLDecoder.decode(str, "utf-8");
    }

    /**
     * @param content  内容
     * @param keyValue Appkey
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    private String encrypt(String content, String keyValue) throws Exception {
        if (keyValue != null) {
            return enBase64(md5(content + keyValue));
        }
        return enBase64(md5(content));
    }

}

package com.dj.boot.configuration.okhttp3.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.btp.exception.BizException;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.common.CommonHttpExecutionService;
import com.dj.boot.configuration.okhttp3.common.CommonSynchronousService;
import com.dj.boot.configuration.okhttp3.domain.CommonExtraMsg;
import com.dj.boot.configuration.okhttp3.domain.CommonTransport;
import com.dj.boot.configuration.okhttp3.domain.TransportWarehouse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;


@Component("commonSynchronousService")
public class CommonSynchronousServiceImpl implements CommonSynchronousService {

    private static final Logger log = LogManager.getLogger(CommonSynchronousServiceImpl.class);

    private static final String dtPrefix="dt://";

    private static final String BIZ_NO="bizNoSign";

    /**
     * UMP监控Key参数名称
     */
    private static final String MONITOR_KEY = "monitorKey";

    @Autowired
    private CommonHttpExecutionService commonHttpExecutionService;

//    @Resource
//    private TransportWarehouseServiceImpl transportWarehouseService;
//
//    @Resource
//    private VelocityProxy conversionService;
//
//    @Resource
//    private EdiDtProxy ediDtProxy;

    @Override
    public Object handler(CommonTransport commonTransport) throws Exception {
        log.error("CommonSynchronousServiceImpl==>handler info :{}", JsonUtil.toJson(commonTransport));
        //校验参数
        verify(commonTransport);

        //step 2:获取数据库配置信息
        TransportWarehouse tw = new TransportWarehouse();// getConfig(commonTransport);
        tw.setServiceUri("22222");

        //step 3:根据模板和业务数据转换参数信息
        Object bodyObj = new Object();//getBodyObject(commonTransport, tw);

        //step 4:构造下发配置并返回拓展报文
        Map<String, Object> headers = buildCommonTransport(commonTransport, tw);


        Map<String, Object> header = commonTransport.getHeader();
        header.put("executor", "POST_JSON");
        header.put("exec", "kdn");
        header.put("url", "http://localhost:8082/user/test");
        header.put("method", "jingdong.hufu.transferorder.report");
        header.put("secret_key", "VQWXFRYEPBKUGJTDOCNZHILMSANBICVO");
        header.put("uri", "/router/service/transferOrderReport");
        header.put("customerId", "hanxiaotest001");
        header.put("appKey", "SXOJDRBATYEUFGCPNWKQIMLVHZMMDHKN");


        try {
            log.info("invoke came-jsf, param body is {} and header is {}", JSONObject.toJSONString(commonTransport), header);
            //返回的是元接口属性的key:value
            String msg = commonHttpExecutionService.executeHttp(JSONObject.toJSONString(commonTransport), JSONObject.toJSONString(header));
            log.info("invoke camel-jsf result is {}", msg);
            return msg;
        } catch (Exception e) {
            log.error("invoke exception,params:{}", commonTransport.toString(), e);
            throw new BizException(600,"invoke exception,params:{" + commonTransport.toString() + "}", e);
        }
    }

    /*private Object getBodyObject(CommonTransport commonTransport, TransportWarehouse tw) {
        Object obj = null;
        try {
            if (StringUtils.isBlank(tw.getTemplateFile())){
                obj = commonTransport.getBodyMsg();
            }else {
                String jsonStr = commonTransport.getBodyMsg().replace("\\\\+n","\\\\n");
                Map<String, Object> map = JsonUtil.fromJson(jsonStr, new TypeReference<Map>() {
                });
                log.error("==> getBodyObject 转换成MAP后的信息是：" + map.toString());
                if(tw.getTemplateFile().startsWith(dtPrefix)){
                    obj = ediDtProxy.convertPreset(map,tw.getTemplateFile().replace(dtPrefix,""));
                }else {
                    obj = conversionService.convertPreset(map,tw.getTemplateFile());
                }
                log.error("==> getBodyObject 按照模板转后的报文是：" + obj.toString());
            }
        } catch (Exception e) {
            log.error("根据报文及模板信息获取模板数据异常,数据：{}，模板：{}", commonTransport.getBodyMsg(), tw.getTemplateFile(),e);
            throw new TransportException("转换模板数据异常", e);
        }
        return obj;
    }*/


    /*private TransportWarehouse getConfig(CommonTransport ct) {
        TransportWarehouse tw = new TransportWarehouse();
        tw.setWarehouseType(ct.getWarehouseType());
        tw.setBizType(ct.getBizType());
        try {
            tw = transportWarehouseService.findTransportWarehouse(tw);
        } catch (Exception e) {
            log.error("获取下发配置错误", e);
            throw new TransportException("获取下发配置错误", e);
        }
        if (tw == null) {
            log.error("获取下发配置为空");
            throw new TransportException("获取下发配置位空");
        }
        return tw;
    }*/

    private void verify(CommonTransport commonTransport) {
        if (commonTransport == null) {
            log.error("下发任务对象为空");
            throw new BizException(600,"下发任务对象为空");
        }

        if (commonTransport.getWarehouseType() == null) {
            log.error("下发任务库房类型为空");
            throw new BizException(600,"下发任务库房类型为空");
        }

    }


    private Map<String, Object> buildCommonTransport(CommonTransport commonTransport, TransportWarehouse tw) {

        Map<String, Object> extraMsg = new HashedMap();
        String twExtraMsg = tw.getExtraMsg();
        try {
            if (commonTransport.getHeader() != null) {
                extraMsg.putAll(commonTransport.getHeader());
            }
            CommonExtraMsg ctExtraMsg = commonTransport.getExtraMsg();
            if (ctExtraMsg != null) {

                Map<String, String> ctMap = BeanUtils.describe(ctExtraMsg);

                extraMsg.putAll(ctMap);
            }
            if (StringUtils.isNotBlank(twExtraMsg)) {
                Map<String, Object> twMap = JsonUtil.fromJson(twExtraMsg, new TypeReference<Map>() {
                });
                extraMsg.putAll(twMap);
            }
            extraMsg.put(BIZ_NO, StringUtils.defaultString(commonTransport.getBizNo(),ctExtraMsg != null?ctExtraMsg.getBizNo():"null")); //记录业务编号

            // ump 监控key
            String bizName = null;
            if(tw.getBizType()!=null){
                bizName = tw.getBizType().toString();
            }else {
                bizName = "unknow";
            }

            String performSystemName = null;
            if(tw.getWarehouseType()!=null){;
                performSystemName = tw.getWarehouseType().toString();
            }else {
                performSystemName = "unknow";
            }

            extraMsg.put(MONITOR_KEY,bizName + "." + performSystemName); //记录监控的业务编号

            String jsonMap = JsonUtil.toJson(extraMsg);
            extraMsg = new HashMap();
            extraMsg.put("heard",jsonMap);
            return extraMsg;
        } catch (Exception e) {
            log.error(String.format("合并拓展报文发生错误,ctExtraMsg:%s,twExtraMsg:%s", twExtraMsg), e);
            throw new BizException(600, String.format("合并拓展报文发生错误,ctExtraMsg:%s,twExtraMsg:%s", commonTransport.getHeader(), twExtraMsg), e);
        }

    }
}

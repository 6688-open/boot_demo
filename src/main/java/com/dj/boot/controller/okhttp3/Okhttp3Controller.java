package com.dj.boot.controller.okhttp3;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.common.CommonHttpExecutionService;
import com.dj.boot.configuration.okhttp3.common.CommonSynchronousService;
import com.dj.boot.configuration.okhttp3.domain.CommonTransport;
import com.dj.boot.controller.okhttp3.support.Result;
import com.dj.boot.pojo.KdnTrackReqDto;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wangjia
 */
@RestController
@RequestMapping("/okhttp3/")
public class Okhttp3Controller {

    private static final Logger logger = LoggerFactory.getLogger(Okhttp3Controller.class);

    @Resource
    private CommonSynchronousService commonSynchronousService;

    /**
     * 测试
     * @return 返回数据
     */
    @ApiOperation(value = "测试")
    @PostMapping("fetchKdnTraces")
    public Result<String> fetchKdnTraces(KdnTrackReqDto req){

        final Result<String> result = new Result();

        if(StringUtils.isBlank(req.getLogisticCode())){
            result.setCode(Result.BAD_REQ);
            result.setDesc("缺失参数【logisticCode】");
            return result;
        }

        final String json = transformUpperCase(JsonUtil.toJson(req));
        try {
            final CommonTransport transport = new CommonTransport();
            transport.setBizNo(req.getLogisticCode());
            Map<String,Object> header = new HashMap<String, Object>();
            header.put("supplyArgs",true);
            header.put("APIKey","9462ab5a-e73e-4a3b-80cf-55a9b7ffc1c0");
            header.put("EBusinessID","1696923");
            header.put("RequestType","8002");
            header.put("DataType","2");
            transport.setHeader(header);
            transport.setBizType(389);
            transport.setWarehouseType(4);
            transport.setBodyMsg(json);

            logger.error("QueryKdnTrackServiceImpl-fetchKdnTraces 入参{}", JSONObject.toJSONString(transport));
            final String data = (String) commonSynchronousService.handler(transport);
            logger.error("QueryKdnTrackServiceImpl-fetchKdnTraces 响应结果{}", data);
            final ResultData resultData = JsonUtil.fromJson(data, ResultData.class);
            if(resultData.code==100){
                result.setCode(Result.SUCCESS);
                result.setData(resultData.result);
                result.setDesc(resultData.msg);
            }else {
                result.setCode(Result.ERROR);
                result.setDesc(resultData.msg);
            }
        } catch (Exception ex) {
            logger.error("【"+json+"】拉取轨迹异常", ex);
            result.setCode(Result.ERROR);
            result.setDesc("拉取轨迹异常");
        }
        return result;
    }



    // 将map的Key全部转换为大写
    public  String transformUpperCase(String json) {
        Map<String,Object> orgMap = JSONObject.parseObject(json, Map.class);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            resultMap.put(Character.toUpperCase(key.charAt(0)) + key.substring(1), orgMap.get(key));
        }
        return JSONObject.toJSONString(resultMap);
    }

    static class ResultData {
        private Integer code;
        private String msg;
        private String result;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }













}

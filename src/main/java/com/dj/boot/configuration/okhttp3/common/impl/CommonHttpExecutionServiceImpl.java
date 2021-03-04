package com.dj.boot.configuration.okhttp3.common.impl;

import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.okhttp3.common.CommonHttpExecutionService;
import com.dj.boot.configuration.okhttp3.interceptor.support.ComplexExecutorInterceptor;
import com.dj.boot.configuration.okhttp3.interceptor.support.DefaultHandleInterceptor;
import com.dj.boot.configuration.okhttp3.sign.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import java.util.*;


public class CommonHttpExecutionServiceImpl implements CommonHttpExecutionService {

    private static final Logger log = Logger.getLogger(CommonHttpExecutionServiceImpl.class);
    private Map<String/*执行器*/, HttpExecutor> executors;
    private Map<String/*执行类型:业务类型*/, List<HttpExecutorInterceptor>> handleInterceptors;
    private List<HttpExecutorInterceptor> commonInterceptors;
    private HttpExecutorInterceptor defaultHandleInterceptor =new DefaultHandleInterceptor();
    @Override
    public String executeHttp(String body, String header) {
        return doInvokeHttp(body, header).getResult();
    }

    private HttpExecutionResult doInvokeHttp(String body, String header) {
        if(log.isInfoEnabled()){
            log.info("body:" + body);
            log.info("header:" + header);
        }
        final HttpExecutionContext.Builder builder = new HttpExecutionContext.Builder();//请求上下文
        try {
            final HttpExecutionChain executionChain = getExecutionChain(header); //从容器获取 执行器和匹配拦截器 封装到HttpExecutionChain对象里
            executionChain.applyPreExecute(builder, body, header); // 执行http之前 参数封装到builder对象里
            final HttpExecutionContext context = builder.build();   // 将builder里面参数 构造方法 构造HttpExecutionContext对象
            final HttpExecutor executor = executionChain.getExecutor(); //获取执行器
            final byte[] rawResult = executor.execute(context);         //执行器 执行封装的参数  返回结果
            final HttpExecutionResult result = new HttpExecutionResult(rawResult); //将返回结果byte 封装到 HttpExecutionResult对象 byte[] rawResult
            executionChain.applyPostExecute(context, result);  //执行http之后 结果封装到HttpExecutionResult对象的 String result 属性
            return result;
        } catch (Exception e) {
            log.error("CommonHttpExecutionServiceImpl执行出错", e);
            throw new RuntimeException("CommonHttpExecutionServiceImpl执行出错", e);
        }
    }

    private HttpExecutionChain getExecutionChain(String header) throws Exception {
        final Map<String, String> parsedHeader = JsonUtil.fromJson(header, new TypeReference<HashMap<String, String>>() {
        });
        final String executorTag = parseExecutorTag(parsedHeader);
        final String interceptorTag = parseInterceptorTag(parsedHeader);

        final HttpExecutor httpExecutor = matchExecutor(executorTag);
        final List<HttpExecutorInterceptor> interceptors = new ArrayList<>(commonInterceptors);
        //匹配指定拦截器
        final List<HttpExecutorInterceptor> matchedInterceptor = matchHandleInterceptor(interceptorTag);
        if (CollectionUtils.isNotEmpty((matchedInterceptor))) {//如果配置的指定拦截器匹配
            log.info("添加匹配拦截器:["+interceptorTag+"]");
            interceptors.addAll(matchedInterceptor);
        } else {//如果未配置匹配则 根据应用默认拦截器
            log.info("添加默认处理拦截器:["+defaultHandleInterceptor.getClass().getCanonicalName()+"]");
            interceptors.add(defaultHandleInterceptor);
        }
        return new HttpExecutionChain(httpExecutor, interceptors);
    }

    /**
     * 解析执行器标记
     *
     * @param parsedHeader
     * @return
     */
    private String parseExecutorTag(Map<String, String> parsedHeader) {
        return parsedHeader.get(HttpExecutionContext.EXECUTOR_KEY);
    }

    /**
     * 解析拦截器标记
     *
     * @param parsedHeader
     * @return
     */
    private String parseInterceptorTag(Map<String, String> parsedHeader) {
        log.info("解析拦截器标记");
        final String executionType = parsedHeader.get(HttpExecutionContext.EXECUTION_TYPE_KEY);
        if (StringUtils.isNotEmpty(executionType) ) {
            return executionType;
        }
        return null;
    }

    private List<HttpExecutorInterceptor> matchHandleInterceptor(String interceptorTag) {
        log.info("查找匹配拦截器:["+interceptorTag+"]");
        //添加匹配拦截器
        if (StringUtils.isEmpty(interceptorTag)) {
            log.info("未找到指定匹配拦截器:["+interceptorTag+"]");
            return Collections.emptyList();
        }
        final List<HttpExecutorInterceptor> interceptors=handleInterceptors.get(interceptorTag);
        if(CollectionUtils.isNotEmpty(interceptors)){
            for (HttpExecutorInterceptor interceptor: interceptors) {
                if(interceptor instanceof ComplexExecutorInterceptor){
                    log.info("匹配到拦截器为:["+ComplexExecutorInterceptor.class.getCanonicalName()+"]");
                    if(((ComplexExecutorInterceptor)interceptor).getPrevInterceptor()==null){
                        log.info("拦截器未设置PrevInterceptor使用默认处理拦截器:["+defaultHandleInterceptor.getClass().getCanonicalName()+"]");
                        ((ComplexExecutorInterceptor)interceptor).setPrevInterceptor(defaultHandleInterceptor);
                    }
                }
            }
        }
        return interceptors ;
    }

    private HttpExecutor matchExecutor(String executorTag) {
        if (!executors.containsKey(executorTag)) {
            throw new RuntimeException("未找到指定执行器,执行器标记[" + executorTag + "]");
        }
        return executors.get(executorTag);
    }


    public Map<String, HttpExecutor> getExecutors() {
        return executors;
    }


    public Map<String, List<HttpExecutorInterceptor>> getInterceptors() {
        return handleInterceptors;
    }


    public List<HttpExecutorInterceptor> getCommonInterceptors() {
        return commonInterceptors;
    }

    public void setExecutors(Map<String, HttpExecutor> executors) {
        this.executors = executors;
    }

    public void setInterceptors(Map<String, List<HttpExecutorInterceptor>> interceptors) {
        this.handleInterceptors = interceptors;
    }

    public void setCommonInterceptors(List<HttpExecutorInterceptor> commonInterceptors) {
        this.commonInterceptors = commonInterceptors;
    }
}

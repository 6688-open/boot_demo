package com.dj.boot.configuration.okhttp3.sign;

import java.util.ArrayList;
import java.util.List;

public class HttpExecutionChain {
    private HttpExecutor executor;
    private List<HttpExecutorInterceptor> interceptors;

    public HttpExecutionChain(HttpExecutor executor) {
        this.executor = executor;
        this.interceptors=new ArrayList<HttpExecutorInterceptor>();
    }

    public HttpExecutionChain(HttpExecutor executor, List<HttpExecutorInterceptor> interceptors) {
        this.executor = executor;
        this.interceptors = interceptors;
    }


    public void applyPreExecute(HttpExecutionContext.Builder builder,String body,String header) throws Exception {
        if(interceptors!=null&&!interceptors.isEmpty()){
            for (int i=0;i<interceptors.size();i++){
                interceptors.get(i).preExecute(builder, body, header);
            }
        }
    }

    public void applyPostExecute(HttpExecutionContext context,HttpExecutionResult result) throws Exception {
        if(interceptors!=null&&!interceptors.isEmpty()){
            for (int i=interceptors.size()-1;i>=0;i--){
                interceptors.get(i).postExecute(context, result);
            }
        }
    }

    public HttpExecutor getExecutor() {
        return executor;
    }

    public List<HttpExecutorInterceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<HttpExecutorInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public void addInterceptor(HttpExecutorInterceptor interceptor){
        this.interceptors.add(interceptor);
    }
}



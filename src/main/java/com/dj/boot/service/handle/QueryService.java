package com.dj.boot.service.handle;

import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Request;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface QueryService {


    /**
     * 支持分页查询
     * list查询
     * @param request
     * @return
     */
    BaseResponse<T> queryStock(Request<T> request);

    /**
     * handlerType
     * @param handlerType
     * @return
     */
    List<String> handlerType(String handlerType);
}

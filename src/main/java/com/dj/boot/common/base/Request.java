package com.dj.boot.common.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request<T> implements Serializable {

    private T data;
    private String operateType;
    private Map<String, Object> attachments = new HashMap();
}

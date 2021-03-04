package com.dj.boot.controller.enumtest;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 枚举查询参数
 */
@Data
public class EnumQueryParam {
    @NotNull(message = "查询类型不能为空 !")
    private String enumType;
}

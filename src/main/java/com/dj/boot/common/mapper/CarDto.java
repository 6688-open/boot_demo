package com.dj.boot.common.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private String username;
    private String createTime;
    private boolean flag;

    /**
     * 测试 vo dto  属性名不相同 通过mapping映射
     */
    private String userPassDto;
    private String userSexDto;
    private Integer receiptsPerformTypeCode;
}

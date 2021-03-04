package com.dj.boot.common.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarVo {
    private String username;
    private Date createTime;
    private boolean flag;



    /**
     * 测试 vo dto  属性名不相同 通过mapping映射
     */
    private String userPassVo;
    private String userSexVo;
    private String receiptsPerformTypeName;
}

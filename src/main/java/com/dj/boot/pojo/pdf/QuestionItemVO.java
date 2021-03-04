package com.dj.boot.pojo.pdf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.pojo.pdf
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-03-12-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionItemVO {
    private String answer;
    private String itemName;
    private String itemType;
}

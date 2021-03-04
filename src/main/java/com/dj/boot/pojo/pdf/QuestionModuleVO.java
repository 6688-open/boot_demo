package com.dj.boot.pojo.pdf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.pojo.pdf
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-03-10-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionModuleVO {
    private String groupName;
    private List<ProjectQuestionVO> questions;
}

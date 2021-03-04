package com.dj.boot.controller.streamApi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.controller.streamApi.dto
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-09-02-14-01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Address address;
}

package com.dj.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.pojo
 * @Author: wangJia
 * @Date: 2021-02-26-11-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantDto {

    private String id;
    private String name;

}

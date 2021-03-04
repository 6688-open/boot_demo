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
 * @Date: 2020-07-03-10-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProjectRequestVO {

    private String projectName;
    private String firstName;
    private String lastName;
    private String description;
    private String phoneNumber;
    private String email;
    private UpdateAddressVo updateAddress;
}

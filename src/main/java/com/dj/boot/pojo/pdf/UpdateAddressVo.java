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
 * @Date: 2020-07-03-10-36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAddressVo {
    private String street;
    private String address2;
    private String address3;
    private String city;
    private String stateName;
    private String postalCode;
    private String companyName;
}

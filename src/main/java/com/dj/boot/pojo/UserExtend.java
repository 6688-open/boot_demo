package com.dj.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.pojo
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2021-02-02-17-26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserExtend {

    private String key1;
    private String key2;
    private String key3;
    private Integer key4;


    public static void main(String[] args) {

        UserExtend.UserExtendBuilder userExtendBuilder = UserExtend.builder()
                .key1("111")
                .key2("222");
        UserExtend userExtend = userExtendBuilder.build();


        UserExtend userExtend1 = UserExtend.builder()
                .key1("222")
                .key2("222")
                .build();


    }

}

package com.dj.boot.pojo.useritem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.pojo.useritem
 * @Author: wangJia
 * @Date: 2020-08-18-11-07
 */
@Data
@Accessors(chain = true)
@TableName("user_item")
@AllArgsConstructor
@NoArgsConstructor
public class UserItem {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String nickName;
}

package com.dj.boot.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.pojo
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-10-12-18-10
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserCommon<T> implements Serializable {
    /**
     * 前端时间戳 1602497978  后端 Date接收参数
     *
     * 或者 Long 接收参数 new Date(Long.valueOf(text));
     */
    @TableField(exist = false)
    private T startTime;
    @TableField(exist = false)
    private T endTime;
}

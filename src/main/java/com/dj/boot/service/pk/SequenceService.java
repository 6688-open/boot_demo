package com.dj.boot.service.pk;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.boot.pojo.pk.Sequence;

import java.util.List;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.pk
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-13-11-21
 */
public interface SequenceService  extends IService<Sequence> {
    /**
     * 获取ID序列
     * @param tableName
     * @return
     */
    public long insertAndGetSequence(String tableName);

    /**
     * 获取MergeID序列
     * @param tableName
     * @param deptId
     * @return
     */
    public long insertAndGetMergeSequence(String tableName,long deptId);

    /**
     * 批量获取ID序列值(num的最大数量为500)
     * @param tableName
     * @param num
     * @return
     */
    public List<String> insertAndGetBatchSequence(String tableName, int num);

    /**
     * 批量获取mergeID序列值(num的最大数量为500)
     * @param tableName
     * @param deptId
     * @param num
     * @return
     */
    public List<String> insertAndGetBatchMergeSequence(String tableName,long deptId,int num);
}

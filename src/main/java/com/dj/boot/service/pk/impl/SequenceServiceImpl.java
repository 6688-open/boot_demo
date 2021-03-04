package com.dj.boot.service.pk.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.boot.manager.SequenceManager;
import com.dj.boot.mapper.pk.SequenceMapper;
import com.dj.boot.pojo.pk.Sequence;
import com.dj.boot.service.pk.IdProcessor;
import com.dj.boot.service.pk.SequenceService;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.pk.impl
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-13-11-21
 */
@Service
public class SequenceServiceImpl extends ServiceImpl<SequenceMapper, Sequence> implements SequenceService {
    private static final Logger log = LogManager.getLogger(SequenceServiceImpl.class);

    /**
     * 当前可用的SequenceManager组
     */
    private Map<Integer, SequenceManager> sequenceManagerMap;

    /**
     * 当前使用的SequenceManager对应的key
     */
    private Integer currentSequenceManagerIndex;

    public void setSequenceManagerMap(Map<Integer, SequenceManager> sequenceManagerMap) {
        this.sequenceManagerMap = sequenceManagerMap;
    }

    public void setCurrentSequenceManagerIndex(Integer currentSequenceManagerIndex) {
        if(log.isInfoEnabled()){
            log.info("setCurrentSequenceManagerIndex:{}",currentSequenceManagerIndex);
        }
        this.currentSequenceManagerIndex = currentSequenceManagerIndex;
    }

    /**
     * 因为当前统一配置在spring当中的属性无法ref到一个对象上,
     * 但是我们可以通过增加sequenceManagerMap和currentSequenceManagerIndex,动态切换currentSequenceManagerIndex值来达到ref的效果
     *
     * @return SequenceManager
     */
    private SequenceManager getCurrentSequenceManager() {
        if(log.isInfoEnabled()){
            log.info("sequenceManagerMap:{},currentSequenceManagerIndex:{}", sequenceManagerMap.toString(), currentSequenceManagerIndex);
        }
        if (MapUtils.isEmpty(sequenceManagerMap) || currentSequenceManagerIndex == null) {
            throw new IllegalStateException("sequenceManagerMap isEmpty or currentSequenceManagerIndex isNull");
        }
        SequenceManager currentSequenceManager = sequenceManagerMap.get(currentSequenceManagerIndex);
        if(log.isInfoEnabled()){
            log.info("getCurrentSequenceManager:{}",currentSequenceManager.toString());
        }
        if (currentSequenceManager == null) {
            throw new IllegalStateException(
                    "can not get currentSequenceManager, please check sequenceManagerMap and currentSequenceManagerIndex");
        }
        return currentSequenceManager;
    }

    /**
     * 获取ID序列
     *
     * @param tableName
     * @return
     */
    @Override
    public long insertAndGetSequence(String tableName) {
        if(log.isInfoEnabled()){
            log.info("开始调用getSequence……");
        }
        return getCurrentSequenceManager().insertAndGetCurrentId(tableName);
    }

    /**
     * 获取MergeID序列
     *
     * @param tableName
     * @param deptId
     * @return
     */
    @Override
    public long insertAndGetMergeSequence(String tableName, long deptId) {
        long id = getCurrentSequenceManager().insertAndGetCurrentId(tableName);
        return IdProcessor.mergeId(deptId, id);
    }

    /**
     * 批量获取ID序列值
     *
     * @param tableName
     * @param num
     * @return
     */
    @Override
    public List<String> insertAndGetBatchSequence(String tableName, int num) {
        return getCurrentSequenceManager().insertAndGetIdLists(tableName, num);
    }

    /**
     * 批量获取mergeID序列值
     *
     * @param tableName
     * @param deptId
     * @param num
     * @return
     */
    @Override
    public List<String> insertAndGetBatchMergeSequence(String tableName, long deptId, int num) {
        List<String> mergeSequenceList = new ArrayList<String>();
        List<String> ids = getCurrentSequenceManager().insertAndGetIdLists(tableName, num);
        if (ids != null && ids.size() > 0) {
            for (String s : ids) {
                long mergeSequence = IdProcessor.mergeId(deptId, Long.parseLong(s));
                mergeSequenceList.add(String.valueOf(mergeSequence));
            }
        }
        return mergeSequenceList;
    }


}

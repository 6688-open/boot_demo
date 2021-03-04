package com.dj.boot.service.goods.manager.impl;

import com.dj.boot.btp.common.util.idUtil.PkTable;
import com.dj.boot.mapper.goods.GoodsCategoryDao;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.goods.*;
import com.dj.boot.service.goods.manager.GoodsCategoryManager;
import com.dj.boot.service.pk.SequenceService;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 租户级商品分类管理器实现
 *
 * @author zhuhua
 * @Date 2019-7-30 18:3:20
 */
@Component
public class GoodsCategoryManagerImpl implements GoodsCategoryManager {


    private static final Logger logger = LoggerFactory.getLogger(GoodsCategoryManagerImpl.class);

    @Resource
    private GoodsCategoryDao goodsCategoryDao;

    @Autowired
    private SequenceService sequenceService;

    /**
     * 获取租户级商品分类
     *
     * @param id
     * @return the GoodsCategory
     */
    @Override
    public GoodsCategoryDto getGoodsCategoryById(Long id) {
        return goodsCategoryDao.getGoodsCategoryById(id);
    }

    /**
     * 插入租户级商品分类
     *
     * @param goodsCategory
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer insertGoodsCategory(GoodsCategory goodsCategory) {
        long id = sequenceService.insertAndGetSequence("dj_user");

        goodsCategory.setId(id);
        goodsCategory.setCategoryNo(id + "");
        if (goodsCategory.getLevel() == 1) {
            goodsCategory.setTreeCode(id + "");
        } else {
            goodsCategory.setTreeCode(goodsCategory.getPTreeCode() + "-" + id);
        }
        goodsCategoryDao.insertGoodsCategory(goodsCategory);
        return (int) id;
    }


    @Override
    public Integer updateGoodsCategory(GoodsCategory goodsCategory) {
        return goodsCategoryDao.updateGoodsCategory(goodsCategory);
    }

    @Override
    public Integer deleteGoodsCategory(Long id) {
        return goodsCategoryDao.deleteGoodsCategory(id);
    }

    @Override
    public List<GoodsCategoryDto> queryViewList(CategoryViewCondition param) {
        return goodsCategoryDao.queryViewList(param);
    }

    @Override
    public List<GoodsCategoryDto> queryPPList(PPCategoryCondition param) {
        return goodsCategoryDao.queryPPList(param);
    }

    @Override
    public List<GoodsCategoryDto> queryChildList(ParentCategoryCondition param) {
        return goodsCategoryDao.queryChildList(param);
    }

    @Override
    public void updateStartOrStop(GoodsCategoryFormDto param) {
        goodsCategoryDao.startOrStop(param);
    }

    @Override
    public boolean checkDuplicate(GoodsCategory goodsCategory) {
        return goodsCategoryDao.getDuplicateQty(goodsCategory)>0;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer insertBatchGoodsCategory(List<GoodsCategory> list) {
        List<GoodsCategory> insertList = new ArrayList<>();
        int len = list.size();
        List<String> idList = sequenceService.insertAndGetBatchSequence("user_item", len);
        Map<String, String> nodeCodeToIdMap = new HashMap();
        Map<String, String> nodeCodeToTreeCodeMap = new HashMap();
        // 遍历并赋值id no pid treeCode
        try {
            for(int i=0; i<len; i++) {
                GoodsCategory gc = list.get(i);
                System.out.println(gc.getCategoryName()+"==========================================="+i);
                String id = idList.get(i);
                String nodeCode = gc.getPTreeCode();// 当前节点的标识,以-分割。
                // 不同层级分配pid,treeCode
                String pid, treeCode;
                Byte level = gc.getLevel();
                if(level.intValue() == 1) {
                    pid = "0";
                    treeCode = id+"";
                } else {
                    // 定位父id和父treeCode
                    String pNodeCode = nodeCode.substring(0, nodeCode.lastIndexOf("-"));
                    pid = nodeCodeToIdMap.get(pNodeCode);
                    String pTreeCode = nodeCodeToTreeCodeMap.get(pNodeCode);
                    treeCode = pTreeCode+"-"+id;
                }
                // 赋值
                gc.setId(Long.parseLong(id));
                String[] split = gc.getCategoryName().split("=");
                gc.setCategoryNo(split[1]);
                gc.setCategoryName(split[0]);
                gc.setPid(Long.parseLong(pid));
                gc.setTreeCode(treeCode);
                insertList.add(gc);
                // 记录分类与id的关系
                if(nodeCodeToIdMap.get(nodeCode) == null) {
                    nodeCodeToIdMap.put(nodeCode, id);
                }
                if(nodeCodeToTreeCodeMap.get(nodeCode) == null) {
                    nodeCodeToTreeCodeMap.put(nodeCode, treeCode);
                }
            }
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return goodsCategoryDao.batchInsertGoodsCategory(list);
    }

    @Override
    public Integer getCount(CategoryViewCondition param) {
        return goodsCategoryDao.getCount(param);
    }
}
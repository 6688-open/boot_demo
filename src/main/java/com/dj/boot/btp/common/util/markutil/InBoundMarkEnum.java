package com.dj.boot.btp.common.util.markutil;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * 入库单标记位
 *
 * @author wj
 * @Date 2020-04-24 10:10
 * Mark的定位：只用作数据存储，应用内部的业务逻辑判断，不作为新建的入参载体
 */
public enum InBoundMarkEnum implements MarkEnum {
    //入库通用
    MARK_1_0(1, '0', "默认"),
    MARK_1_1(1, '1', "SAP虚拟仓"),
    MARK_2_0(2, '0', "默认"),
    MARK_2_1(2, '1', "回传商家"),
    MARK_3_0(3, '0', "默认"),
    MARK_3_1(3, '1', "溯源码采集"),
    MARK_4_0(4, '0', "默认"),
    MARK_4_1(4, '1', "WMS生成调整单"),
    MARK_5_0(5, '0', "默认"),
    MARK_5_1(5, '1', "指定批次入库"),
    MARK_6_0(6, '0', "默认"),
    MARK_6_1(6, '1', "在途可售"),
    MARK_7_0(7, '0', "默认"),
    MARK_7_1(7, '1', "无库房接单"),
    MARK_8_0(8, '0', "整单"),
    MARK_8_1(8, '1', "分批"),//回传商家方式
    MARK_9_1(9, '1', "自动生成执行单"),//自动生成执行单
    MARK_10_1(10, '1', "可分批入库"),//采购单分批生成入库单 采购
    MARK_10_2(10, '2', "已分批入库"),//采购单分批生成入库单
//    MARK_11_1(11, '1', "取消后可重建"),//取消后可重建
//    MARK_12_1(12, '1', "是否生成调整单"),//wms是否生成调整单，质检
    MARK_13_0(13, '0', "WMS按单回传"),//WMS按单回传 入库
    MARK_13_1(13, '1', "WMS按板回传"),//WMS按板回传
    MARK_14_0(14, '0', "是否允许修订"),//是否允许修订 采购
    MARK_14_1(14, '1', "是否允许修订"),//是否允许修订
    MARK_15_1(15, '1', "询价"),//询问价格-价格中心 采购
    MARK_16_0(16, '0', "采购审核-自动审核默认"),//是否进行采购审核
    MARK_16_1(16, '1', "采购审核-人工审核"),//是否进行采购审核
    MARK_17_1(17, '1', "协同到货"),//协同到货
    MARK_18_1(18, '1', "下发供应商系统"),//下发供应商系统
    MARK_19_1(19, '1', "自动下发下发供应商系统"),//自动下发供应商系统 采购
    MARK_20_1(20, '1', "是否下发财务系统"),//是否下发财务系统
    MARK_21_1(21, '1', "ECLP拒收退货入库单"),//ECLP拒收退货入库单 入库
    MARK_22_0(22, '0', "下发WMS-默认"),// 入库
    MARK_22_1(22, '1', "不下发WMS"),// 入库
    MARK_23_1(22, '1', "直发"),// 入库
    MARK_24_1(22, '1', "以销订采"),// 入库
    /**
     * 采购单创建时不允许供应商为空
     */
    MARK_25_0(25, '0', "默认"),
    /**
     * 采购单创建时允许供应商为空
     */
    MARK_25_1(25, '1', "允许供应商为空"),
    ;


    private static final String split = "-";

    /**
     * 位置
     */
    private int bit;

    /**
     * 标记位值
     */
    private char value;

    /**
     * 描述
     */
    private String desc;

    InBoundMarkEnum(int bit, char value, String desc) {
        this.bit = bit;
        this.value = value;
        this.desc = desc;
    }


    private static HashMap<String, InBoundMarkEnum> enumHashMap = new HashMap<String, InBoundMarkEnum>();

    static {
        for (InBoundMarkEnum enumItem : EnumSet.allOf(InBoundMarkEnum.class)) {
            enumHashMap.put(enumItem.bit() + split + enumItem.value(), enumItem);
        }
    }

    public int bit() {
        return bit;
    }

    public char value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    public MarkEnum getMarkEnum(int bit, char value) {
        return enumHashMap.get(bit + split + value);
    }
}

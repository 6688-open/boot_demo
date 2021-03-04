package com.dj.boot.common.constant;

/**
 * 商品相关常量
 */
public class GoodsConstant {
    
    /**
     * 商品外部映射缓存前缀
     */
    public static final String GOODS_OUT_BIZ_KEY_PERFIX = "GSEB";
    
    /**
     * 货主商品映射缓存前缀
     */
    public static final String GOODS_OWNER_BIZ_KEY_PERFIX = "GOEB";

    /**
     * 商品全量信息缓存前缀
     */
    public static final String GOODS_FULL_KEY_PERFIX = "GF";

    /**
     * 商品生成防并发KEY
     */
    public static final String GOODS_CREATE_LOCK = "goods_create_lock_";
    /**
     * 商品更新防并发KEY
     */
    public static final String GOODS_UPDATE_LOCK = "goods_update_lock_";

    /**
     * 商品条码分割符
     */
    public static final String GOODS_BARCODE_SPLIT_KEY = ",";


    
    
}

package com.dj.boot.common.constant;

/**
 * Created with IntelliJ IDEA.
 * User: chent
 * Date: 2019-8-23
 */
public class ExcelConstant {

    /**
     * 导出字段
     */
    public static final String TEMPLATE = "例子";
    public static final String[] TEMPLATE_FIELDNAMES = {"序号", "编码", "名称"};
    public static final String[] TEMPLATE_FIELDPRENAMES = {"id", "no", "name"};


    /**
     * 导入最大数量
     */
    public final static int IMPORTMAXLENGTH = 500;

    /**
     * 导入商品分类
     */
    public final static int FIRST_CATEGORY_NAME = 0;
    public final static int SECOND_CATEGORY_NAME = FIRST_CATEGORY_NAME + 1;
    public final static int THIRD_CATEGORY_NAME = SECOND_CATEGORY_NAME + 1;
    public final static int FOURTH_CATEGORY_NAME = THIRD_CATEGORY_NAME + 1;
    public final static int FIFTH_CATEGORY_NAME = FOURTH_CATEGORY_NAME + 1;
}

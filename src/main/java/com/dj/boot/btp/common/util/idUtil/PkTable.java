package com.dj.boot.btp.common.util.idUtil;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 业务类型枚举
 *
 * @author ext.wangjia
 * @version : 1.0
 * @date : 2019/3/14
 */
public enum PkTable {


    /**
     * 技术框架
     */
    Btp_Flow("flow", "流程", "", "", PkMixEnum.NO.getCode()),
    Btp_FlowNode("flow_node", "流程节点", "", "", PkMixEnum.SHORT.getCode()),

    //合同
    CONTRACT_TEMPLATE("template", "模板", "COT", "%08d", PkMixEnum.LONG.getCode()),
    ;


    /**
     * id生成的key
     */
    String table;
    /**
     * 业务名称
     **/
    String name;
    /**
     * 业务单号前缀
     **/
    String prefix;

    /**
     * 业务单号格式化
     **/
    String format;
    /**
     * 是否混淆单号 开启混淆生成的单号最低11位
     */
    int mix;


    PkTable(String table, String name, String prefix, String format, Integer mix) {
        this.table = table;
        this.name = name;
        this.prefix = prefix;
        this.format = format;
        this.mix = mix;
    }

    public static final Map<String, PkTable> PkTableEnum_MAP = new HashMap();

    static {
        Iterator i$ = EnumSet.allOf(PkTable.class).iterator();

        while (i$.hasNext()) {
            PkTable e = (PkTable) i$.next();
            PkTableEnum_MAP.put(e.getTable(), e);
        }
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFormat() {
        return format;
    }

    public int getMix() {
        return mix;
    }

    public PkTable getPkTable(String table) {
        return PkTableEnum_MAP.get(table);
    }
}

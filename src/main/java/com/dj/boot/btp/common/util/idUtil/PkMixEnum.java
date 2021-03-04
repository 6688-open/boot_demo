package com.dj.boot.btp.common.util.idUtil;

/**
 * 是否进行编号混淆:0不混淆 1生成长混淆码 2生成断混淆码
 *
 * @author ext.wangjia
 * @Date 2019-09-25 16:16
 */
public enum PkMixEnum {

    NO(0), LONG(1), SHORT(2);

    private int code;

    PkMixEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

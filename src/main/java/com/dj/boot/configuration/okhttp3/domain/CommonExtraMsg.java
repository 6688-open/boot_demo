package com.dj.boot.configuration.okhttp3.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: wj
 * Date: 2017/12/2
 * Time: 18:38
 */
public class CommonExtraMsg implements Serializable {

    /**
     * erp库房号
     */
    private  String erpWarehouseNo;

    /**
     * 业务编号
     */
    private  String bizNo;

    /**
     *合作伙伴id
     */
    private  String partnerNo;

    /**
     * 库房编号
     */
    private  String warehouseNo;

    /**
     * 用户pin
     */
    private  String pin;

    /**
     * 对接appKey
     */
    private String appKey;

    /**
     * 系统对接token
     */
    private String appScrect;

    /**
     *
     * 对接session
     */
    private String session;

    /**
     * 操作类型
     */
    private String operateType;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getErpWarehouseNo() {
        return erpWarehouseNo;
    }

    public void setErpWarehouseNo(String erpWarehouseNo) {
        this.erpWarehouseNo = erpWarehouseNo;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppScrect() {
        return appScrect;
    }

    public void setAppScrect(String appScrect) {
        this.appScrect = appScrect;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}


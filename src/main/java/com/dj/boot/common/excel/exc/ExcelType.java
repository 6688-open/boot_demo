package com.dj.boot.common.excel.exc;

public enum ExcelType {

    XLSX(1, ".xlsx"),
    XLS(2, ".xls");

    private Integer code;

    private String type;


    ExcelType() {
    }

    ExcelType(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

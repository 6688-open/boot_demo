package com.dj.boot.common.excel.easyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User extends BaseRowModel {
    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "昵称", index = 1)
    private String nickName;

    @ExcelProperty(value = "密码", index = 2)
    private String password;

    //不支持LocalDate
    @ExcelProperty(value = "生日", index = 3)
    private String birthday;
}

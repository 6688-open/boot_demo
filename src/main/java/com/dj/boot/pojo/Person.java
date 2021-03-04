package com.dj.boot.pojo;

import com.dj.boot.pojo.validate.GroupInterface;
import com.dj.boot.pojo.validate.NotLessThanZero;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Component
@ConfigurationProperties(prefix="person")
@Data
@Accessors(chain = true)
@Valid
public class Person {

    private String userName;
    private String password;
    private Integer age;
    @NotNull(message = "费用项不能为空" , groups = {GroupInterface.class})
    @Positive(message = "商品数量不能小于等于0", groups = {GroupInterface.class})
    @Digits(integer = 3, fraction = 2, groups = {GroupInterface.class}, message = "费用项数量不能超过{integer}位,小数点后不能超过{fraction}位")
    @NotLessThanZero(message = "费用项金额必须大于0" , groups = {GroupInterface.class})
    private BigDecimal userNum;



}

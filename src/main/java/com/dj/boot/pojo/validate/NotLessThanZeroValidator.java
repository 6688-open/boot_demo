package com.dj.boot.pojo.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.pojo.validate
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-04-11-24
 */
public class NotLessThanZeroValidator  implements ConstraintValidator<NotLessThanZero, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }
        if (value instanceof Number) {
            if (((Number) value).intValue() > 0) {
                return true;
            }
        }
        return false;
    }
}

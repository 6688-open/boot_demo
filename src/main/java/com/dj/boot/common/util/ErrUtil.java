package com.dj.boot.common.util;

import com.dj.boot.btp.exception.BizException;
import com.dj.boot.common.base.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.util
 * @Author: wangJia
 * @Date: 2020-11-12-16-35
 */
public class ErrUtil {

    private ErrUtil() {
    }

    public static void ifNull(Object target, String message) {
        if (target == null) {
            throw new BizException(Response.ERROR_BUSINESS, message);
        }
    }

    public static void ifBlank(String target, String message) {
        if (StringUtils.isBlank(target)) {
            throw new BizException(Response.ERROR_BUSINESS, message);
        }
    }

    public static void ifSizeEmpty(final Object o, String message) {
        ifNull(o, message);
        if (CollectionUtils.sizeIsEmpty(o)) {
            throw new BizException(Response.ERROR_BUSINESS, message);
        }
    }

//    public static void ifInValid(String regex, String target, String message) {
//        if (!ValidatorUtil.validateCharSequence(regex, target))
//            throw new IllegalArgumentException(message + "[" + target + "]不合法");
//    }
}

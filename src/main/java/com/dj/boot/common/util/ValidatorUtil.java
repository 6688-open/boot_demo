package com.dj.boot.common.util;

import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * use by wj
 */
public class ValidatorUtil {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public ValidatorUtil() {
    }

    public static ValidatorUtil.Result validate(Object object, Class<?>... groups) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> validateResult = validator.validate(object, groups);
        if (validateResult.isEmpty()) {
            return new Result(Boolean.TRUE);
        } else {
            List<String> errMsgList = new ArrayList();
            Iterator iterator = validateResult.iterator();

            while(iterator.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation)iterator.next();
                errMsgList.add(violation.getMessage());
            }

            return new Result(Boolean.FALSE, errMsgList);
        }
    }

    public static ValidatorUtil.Result validateCollection(Collection<?> collection, Class<?>... groups) {
        if (CollectionUtils.isNotEmpty(collection)) {
            Iterator iterator = collection.iterator();

            while(iterator.hasNext()) {
                Object next = iterator.next();
                ValidatorUtil.Result validate = validate(next, groups);
                if (!validate.isPass()) {
                    return validate;
                }
            }
        }

        return new Result(Boolean.TRUE);
    }

    public static class Result {
        private final boolean isPass;
        private List<String> errMsgList;

        public Result(boolean isPass) {
            this.isPass = isPass;
        }

        public Result(boolean isPass, List<String> errMsgList) {
            this.isPass = isPass;
            this.errMsgList = errMsgList;
        }

        public boolean isPass() {
            return this.isPass;
        }

        public List<String> getErrMsgList() {
            return this.errMsgList;
        }

        public void setErrMsgList(List<String> errMsgList) {
            this.errMsgList = errMsgList;
        }
    }
}

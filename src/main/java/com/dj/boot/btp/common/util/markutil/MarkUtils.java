package com.dj.boot.btp.common.util.markutil;

import com.dj.boot.btp.common.util.markutil.MarkEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 标记位工具类.
 * 默认生成一个长度为200位,全是0的字符串
 * 默认的0不能标记任何特殊业务
 *
 * @author wj
 * @Date 2020-04-23 18:13
 */
public class MarkUtils {

    /**
     * 实际值
     */
    private final char[] value;
    /**
     * 长度
     */
    private final int count;

    private final int defaultLength = 200;

    public MarkUtils() {
        this.count = defaultLength;
        this.value = String.format("%0" + defaultLength + "d", 0).toCharArray();
    }

    public MarkUtils(String mark) {
        if (StringUtils.isNotBlank(mark)) {
            if (mark.length() != defaultLength) {
                // throw new SoBizException(SoIssuedErrorEnum.R_20_2);
                if (mark.length() > defaultLength) {
                    mark = mark.substring(0, defaultLength);
                } else {
                    mark = mark + String.format("%0" + (defaultLength - mark.length()) + "d", 0);
                }
            }
            this.count = mark.length();
            this.value = mark.toCharArray();
        } else {
            this.count = defaultLength;
            this.value = String.format("%0" + defaultLength + "d", 0).toCharArray();
        }
    }


    /**
     * 获取标记位值
     *
     * @param mark
     * @return
     */
    public char charAt(MarkEnum mark) {
        if ((mark.bit() <= 0) || (mark.bit() > count))
            throw new StringIndexOutOfBoundsException(mark.bit());
        return value[mark.bit() - 1];
    }

    /**
     * 设置标记位值
     *
     * @param mark
     */
    public void inChar(MarkEnum mark) {
        if ((mark.bit() <= 0) || (mark.bit() > count))
            throw new StringIndexOutOfBoundsException(mark.bit());
        value[mark.bit() - 1] = mark.value();
    }

    /**
     * 判断标记位 所有标记必须满足才返回true
     *
     * @param marks
     * @return
     */
    public boolean isMark(MarkEnum... marks) {
        if (marks == null || marks.length < 1) {
            return false;
        }
        for (MarkEnum mark : marks) {
            try {
                if (charAt(mark) != mark.value()) {
                    return false;
                }
            } catch (StringIndexOutOfBoundsException e) {
                return false;
            }

        }
        return true;
    }

    /**
     * 判断标记位 只有一个标记满足就返回true
     *
     * @param marks
     * @return
     */
    public boolean isOrMark(MarkEnum... marks) {
        if (marks == null || marks.length < 1) {
            return false;
        }
        for (MarkEnum mark : marks) {
            try {
                if (charAt(mark) == mark.value()) {
                    return true;
                }
            } catch (StringIndexOutOfBoundsException ignore) {
            }

        }
        return false;
    }

    /**
     * 获取所有标记特殊业务的描述
     *
     * @param mark
     * @return
     */
    public String getDesc(MarkEnum mark) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            char c = value[i];
            if (c != '0') {
                MarkEnum markEnum = mark.getMarkEnum(i + 1, c);
                if (markEnum != null) {
                    sb.append(markEnum.desc()).append(";");
                }
            }
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

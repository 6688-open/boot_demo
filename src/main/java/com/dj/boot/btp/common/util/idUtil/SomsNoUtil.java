package com.dj.boot.btp.common.util.idUtil;



import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * 公共方法:单号生成器
 * Date: 13-5-10Time: 下午1:51
 * version:1.0
 * @author ext.wangjia
 */
public class SomsNoUtil {

    private static long coefficient = 11L;
    private static long longSpace = 1032214646L;
    private static long shortSpace = 1626L;

    /**
     * 生成单号
     *
     * @return
     */
    public static String generateNo(final PkTable bizType, final Long id) {
        if (PkMixEnum.LONG.getCode() == bizType.getMix()) {
            return bizType.getPrefix() + String.format(bizType.getFormat(), (id * coefficient) ^ longSpace);
        }
        if (PkMixEnum.SHORT.getCode() == bizType.getMix()) {
            return bizType.getPrefix() + String.format(bizType.getFormat(), (id * coefficient) ^ shortSpace);
        } else {
            return bizType.getPrefix() + String.format(bizType.getFormat(), id);
        }

    }

    /**
     * 编号转化为ID
     * @param no
     * @param bizType
     * @return
     */
    public static long reverseNo2Id(String no, final PkTable bizType) {
        if (null == no || "".equals(no)) {
            return -1L;
        }
        if (no.startsWith(bizType.getPrefix())) {
            try {
                if (PkMixEnum.LONG.getCode() == bizType.getMix()) {
                    Long mixId = Long.parseLong(no.substring(bizType.getPrefix().length())) ^ longSpace;
                    if (mixId % coefficient == 0) {
                        return mixId / coefficient;
                    } else {
                        return -1;
                    }
                }
                if (PkMixEnum.SHORT.getCode() == bizType.getMix()) {
                    Long mixId = Long.parseLong(no.substring(bizType.getPrefix().length())) ^ shortSpace;
                    if (mixId % coefficient == 0) {
                        return mixId / coefficient;
                    } else {
                        return -1;
                    }
                } else {
                    return Long.parseLong(no.substring(bizType.getPrefix().length()));
                }
            } catch (Exception e) {
                return -1L;
            }
        } else {
            try {
                return Long.parseLong(no);
            } catch (Exception e) {
                return -1L;
            }
        }
    }

    /**
     * 批量操作 单号 ---> ID
     *
     * @param noCollection
     * @param bizType
     * @return
     */
    public static Set<Long> batchReverseNo2Id(Collection<String> noCollection, final PkTable bizType) {
        Set<Long> idSet = new HashSet<Long>();
        if (noCollection != null && !noCollection.isEmpty()) {
            for (String no : noCollection) {
                long id = reverseNo2Id(no, bizType);
                if (id != -1L) {
                    idSet.add(id);
                }
            }
        }
        return idSet;

    }

    /**
     * 编号转化为ID
     * 换另一个传参使用
     *
     * @param no       编号
     * @param noPrefix 编号前缀
     * @return 返回ID
     */
    @Deprecated
    public static long reverseNo2Id(String no, String noPrefix) {
        if (null == no || "".equals(no)) {
            return -1L;
        }
        if (no.startsWith(noPrefix)) {
            try {
                return Long.valueOf(no.substring(noPrefix.length()));
            } catch (Exception e) {
                return -1L;
            }
        } else {
            try {
                return Long.valueOf(no);
            } catch (Exception e) {
                return -1L;
            }
        }
    }


    public static void main(String[] args) {
        String generateNo = SomsNoUtil.generateNo(PkTable.CONTRACT_TEMPLATE, 120202020020202L);
        System.out.println(generateNo);
        long ID = SomsNoUtil.reverseNo2Id(generateNo, PkTable.CONTRACT_TEMPLATE);
        System.out.println(ID);
        System.out.println(120202020020202L);
    }
}
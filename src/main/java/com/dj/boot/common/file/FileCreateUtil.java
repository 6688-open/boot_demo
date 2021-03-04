package com.dj.boot.common.file;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @Date 2019-12-17 18:45
 */
@Slf4j
public class FileCreateUtil {

    private static final String tmpDir;

    static {
        tmpDir = "/export/tmp/";
    }

    /**
     * 创建临时文件
     *
     * @param
     */
    public static File fileExcelCreate(String namePre) {
        String uuid = UUID.randomUUID().toString();
        //根据生成相应的bean对象
        //拼接文件名称
        String fileName = new StringBuilder(namePre).append("-").append(uuid).append(".xlsx").toString();
        File file = new File(tmpDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        //根据目录和文件名生成文件
        File fileTemp = new File(file, fileName);
        if (!fileTemp.exists()) {
            try {
                fileTemp.createNewFile();
                log.info("【文件工具类】创建文件成功!" + fileName);
            } catch (IOException e) {
                log.error("【文件工具类】创建文件失败：!" + file, e);
                throw new RuntimeException("【文件工具类】创建文件失败!" + fileName, e);
            }
        }
        return fileTemp;
    }


    /**
     * 删除临时文件
     *
     * @param
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            try {
                file.delete();
                log.error("【文件工具类】刪除临时文成功!" + file.toString());
            } catch (Exception e) {
                log.error("【文件工具类】刪除临时文件失败：!" + file.toString(), e);
                throw new RuntimeException("【文件工具类】刪除临时文件失败：!" + file.toString(), e);
            }

        }
    }

}

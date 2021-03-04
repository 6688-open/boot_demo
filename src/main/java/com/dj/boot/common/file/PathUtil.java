package com.dj.boot.common.file;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @Date 2019-12-17 20:55
 */
public class PathUtil {

    public static String generateJssPath(String type, String user, String fileName) throws Exception {
        if (StringUtils.isBlank(fileName)) {
            throw new Exception("文件名称为空");
        } else if (!fileName.contains(".")) {
            throw new Exception("[" + fileName + "]未解析到文件名");
        } else {
            Calendar now = Calendar.getInstance();
            StringBuilder sb = new StringBuilder(type);
            sb.append("/").append(now.get(Calendar.YEAR));
            sb.append("/").append(now.get(Calendar.MONTH) + 1);
            sb.append("/").append(now.get(Calendar.DAY_OF_MONTH));
            sb.append("/").append(user).append("-").append(UUID.randomUUID().toString().replace("-", ""));
            sb.append(fileName.substring(fileName.lastIndexOf("."), fileName.length()));
            return sb.toString();
        }
    }


    public static void main(String[] args) throws Exception {
        File file = new File("D:/user.xlsx");
        String dir = "attachFile/";
        String jssFileName = PathUtil.generateJssPath(dir + "业务逻辑", "wj", file.getName());
        System.out.println(jssFileName);

    }
}

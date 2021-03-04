package com.dj.boot.common.excel.clps;

import com.dj.boot.common.util.collection.CollectionUtils;
import com.dj.boot.common.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.*;
/**
 *  CLPS导入导出工具类
 */
public class ExportExcel {

    private static final Logger log = LogManager.getLogger(ExportExcel.class);
    private static String PARAM_TITLE = "文件标题不能为空";
    private static String PARAM_FIELDNAMES = "列表列名不能为空";
    private static String MAX_EXPORT_SIZE_FIELDNAMES = "导出数据大于10000条,请增加查询条件!";
    private static final Integer MAX_EXPORT_SIZE = 10000;

    /**
     * 设置响应
     * @param response
     * @param fileName
     */
    public static void setResponseParam(HttpServletResponse response, String fileName) {
        try {
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + java.net.URLEncoder.encode(fileName , "utf-8")+ ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
    }

    /**
     * 参数校验
     * @param fileName
     * @param fieldNames
     * @param list
     * @throws Exception
     */
    public static <T> void validateParam(String fileName, String[] fieldNames, List<T> list) throws Exception {
        //文件名非空判断
        if (StringUtils.isBlank(fileName)) {
            throw new Exception(PARAM_TITLE);
        }
        //列表列名非空
        if (null == fieldNames || fieldNames.length <= 0) {
            throw new Exception(PARAM_FIELDNAMES);
        }
        if (list.size() > MAX_EXPORT_SIZE) {
            throw new RuntimeException(MAX_EXPORT_SIZE_FIELDNAMES);
        }
    }
    /**
     * 导出excel
     * @param response
     * @param fileName
     * @param fieldNames
     * @param fieldPreNames
     * @param list
     * @param <T>
     * @throws Exception
     */
    public static <T> void exportExcel(HttpServletResponse response, String fileName, String[] fieldNames, String[] fieldPreNames, List<T> list) throws Exception {
        XSSFWorkbook xssfWorkbook = translateExcelData(response, fileName, fieldNames, fieldPreNames, list);
        xssfWorkbook.write(response.getOutputStream());
    }
    /**
     * 解析数据  translateExcelData
     * @param response
     * @param fileName
     * @param fieldNames
     * @param fieldPreNames
     * @param list
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> XSSFWorkbook translateExcelData(HttpServletResponse response, String fileName, String[] fieldNames, String[] fieldPreNames, List<T> list) throws Exception {
        //参数校验
        validateParam(fileName, fieldNames, list);
        //设置响应
        setResponseParam(response, fileName);
        //创建XSSFWorkbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(fileName);
        sheet.setDefaultColumnWidth(20);
        //创建表头
        createColumHeader(workbook, sheet, fieldNames);
        //列表数据为空 导出空文件
        if (CollectionUtils.isNullOrEmpty(list)) {
            return workbook;
        }
        try {
            Set<String> fieldSet = new HashSet<>();
            for (String field : fieldPreNames) {
                fieldSet.add(field.toLowerCase());
            }
            Method[] methods = getMethods(list.get(0).getClass());
            Map<String, Method> methodMap = new HashMap<>();
            Method[] row = methods;
            int rowCount = methods.length;
            for(int i = 0; i < rowCount; ++i) {
                Method method = row[i];
                String filedName = method.getName().replace("get", "").toLowerCase();
                if (fieldSet.contains(filedName)) {
                    methodMap.put(filedName, method);
                }
            }
            rowCount = 1;
            for(Iterator it = list.iterator(); it.hasNext(); ++rowCount) {
                T t = (T) it.next();
                XSSFCellStyle style = workbook.createCellStyle();
                XSSFRow row1 = sheet.createRow(rowCount);
                createCell(row1, style, methodMap, fieldPreNames, t);
            }
        } catch (Exception e) {
            log.error("导出excel创建单元格是出现异常", e);
        }
        return workbook;
    }

    private static void createColumHeader(XSSFWorkbook workbook, XSSFSheet sheet, String[] fieldNames) {
        XSSFRow row = sheet.createRow(0);
        row.setHeight((short)400);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.forInt((short)2));
        cellStyle.setVerticalAlignment(VerticalAlignment.forInt((short)1));
        cellStyle.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setBold(true);//是否加粗
        font.setFontName("宋体");
        font.setFontHeight((short)280);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.valueOf((short)1));
        cellStyle.setBottomBorderColor((short)8);
        cellStyle.setBorderLeft(BorderStyle.valueOf((short)1));
        cellStyle.setLeftBorderColor((short)8);
        cellStyle.setBorderRight(BorderStyle.valueOf((short)1));
        cellStyle.setRightBorderColor((short)8);
        cellStyle.setBorderTop(BorderStyle.valueOf((short)1));
        cellStyle.setTopBorderColor((short)8);
        XSSFCell cell = null;

        for(int i = 0; i < fieldNames.length; ++i) {
            cell = row.createCell(i);
            cell.setCellType(CellType.forInt(1));
            cell.setCellStyle(cellStyle);
            cell.setCellValue(new XSSFRichTextString(fieldNames[i]));
        }
    }

    private static <T> void createCell(XSSFRow row, XSSFCellStyle style, Map<String, Method> methodMap, String[] fieldPreNames, T t) {
        for(int i = 0; i < fieldPreNames.length; ++i) {
            try {
                Method method = (Method)methodMap.get(fieldPreNames[i].toLowerCase());
                XSSFCell cell = row.createCell(i);
                Object fieldValue = method.invoke(t);
                cell.setCellType(CellType.forInt(1));
                style.setBorderBottom(BorderStyle.valueOf((short)1));
                style.setBorderTop(BorderStyle.valueOf((short)1));
                style.setBorderLeft(BorderStyle.valueOf((short)1));
                style.setBorderRight(BorderStyle.valueOf((short)1));
                if (fieldValue == null) {
                    cell.setCellValue("");
                } else if (fieldValue instanceof Date) {
                    cell.setCellValue(DateUtil.format((Date)fieldValue, "yyyy-MM-dd HH:mm:ss"));
                } else {
                    cell.setCellValue(fieldValue.toString());
                }

                cell.setCellStyle(style);
            } catch (Exception e) {
                log.error("导出excel创建单元格是出现异常", e);
            }
        }
    }


    private static Method[] getMethods(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        if(clazz.getSuperclass() != null){
            Method[] superMethods = getSuperMethods(clazz);
            if(superMethods != null){
                Method[] allMethods = new Method[methods.length + superMethods.length];
                System.arraycopy(methods, 0, allMethods, 0, methods.length);
                System.arraycopy(superMethods, 0, allMethods, methods.length, superMethods.length);
                return allMethods;
            }
        }
        return methods;
    }

    private static Method[] getSuperMethods(Class clazz){
        if(clazz.getSuperclass() != Object.class){
            Method[] methods = clazz.getSuperclass().getDeclaredMethods();
            Method[] superMethods = getSuperMethods(clazz.getSuperclass());
            if(superMethods != null){
                Method[] allMethods = new Method[methods.length + superMethods.length];
                System.arraycopy(methods, 0, allMethods, 0, methods.length);
                System.arraycopy(superMethods, 0, allMethods, methods.length, superMethods.length);
                return allMethods;
            }else{
                return methods;
            }
        }else{
            return null;
        }
    }
}

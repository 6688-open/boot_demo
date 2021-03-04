package com.dj.boot.common.excel.exc;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  导入导出工具类
 * @param <T>
 */
public class ExcelUtil<T> {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public Class<T> clazz;

    public ExcelUtil(Class<T> clazz)
    {
        this.clazz = clazz;
    }
    /**
     * 用于将科学计数法式的数字转换为常规数字显示,BigDecimal表示。<br>
     * 如：6.913084212242E12  转换成：6940282300075
     * @param obj as Object
     * @return
     */
    public static String decimal2string(Object obj){
        BigDecimal big = null;
        if(obj==null) return "";
        if(obj instanceof Number){
            big = BigDecimal.valueOf(((Number) obj).doubleValue());
        }else {
            return obj.toString();
        }
        return big.toPlainString();
    }
    public List<T> importExcel(String sheetName, InputStream input) throws Exception  {
        List<T> list = new ArrayList<T>();

        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = null;
        if(StringUtils.isEmpty(sheetName)){
            sheet = workbook.getSheetAt(0);
        }else{
            sheet = workbook.getSheet(sheetName);
        }
        int rows = sheet.getPhysicalNumberOfRows();

        if (rows > 0)  {
            // 有数据时才处理 得到类的所有field.
            Field[] allFields = clazz.getDeclaredFields();
            // 定义一个map用于存放列的序号和field.
            List<Field> fieldsMap = new ArrayList<>();
            for (Field field : allFields) {
                // 将有注解的field存放到map中.
                if (field.isAnnotationPresent(Excel.class)) {
                    // 设置类的私有字段属性可访问.
                    field.setAccessible(true);
                    fieldsMap.add(field);
                }
            }
            for (int i = 1; i < rows; i++) {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i);
                //获取一行所有的单元格的数量
                int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();
                T entity = null;
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    } else {
                        cell = row.getCell(j);
                    }
                    String c = null;
                    if(cell.getCellType()==CellType.NUMERIC) {
                        c = decimal2string(cell.getNumericCellValue());
                    } else c = cell.getStringCellValue();
                    if (StringUtils.isEmpty(c)) {
                        continue;
                    }
                    c= replaceBlank(c);
                    // 如果不存在实例则新建.
                    entity = entity == null ? clazz.newInstance() : entity;
                    // 从map中得到对应列的field.
                    Field field = fieldsMap.get(j);
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
//                    Class<?> fieldType = String.class;
                    if (String.class == fieldType) {
                        field.set(entity, String.valueOf(c));
                    }
                    else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                        field.set(entity, Integer.parseInt(c));
                    }
                    else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                        field.set(entity, Long.valueOf(c));
                    }
                    else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                        field.set(entity, Float.valueOf(c));
                    }
                    else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                        field.set(entity, Short.valueOf(c));
                    }
                    else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                        field.set(entity, Double.valueOf(c));
                    }
                    else if (Character.TYPE == fieldType) {
                        if ((c != null) && (c.length() > 0)) {
                            field.set(entity, c.charAt(0));
                        }
                    }
                    else if (Date.class == fieldType) {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            cell.setCellValue(sdf.format(cell.getNumericCellValue()));
                            c = sdf.format(cell.getNumericCellValue());
                        } else {
                            c = cell.getStringCellValue();
                        }
                    }
                    else if (BigDecimal.class == fieldType) {
                        c = cell.getStringCellValue();
                    }
                }
                if (entity != null) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    /**
     * 导出excel到网络（直接将http）
     *
     * @param list
     * @param sheetName
     * @param response
     * @return
     */
    public void exportExcelToWeb(HttpServletResponse response, List<T> list, String sheetName, ExcelType excelType) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(sheetName + parseExcelType(excelType), "utf-8"));
            this.exportExcel(list,sheetName,response.getOutputStream(), excelType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void download(List<T> list, String sheetName, HttpServletResponse response, ExcelType excelType) throws UnsupportedEncodingException {
        // 告诉浏览器用什么软件可以打开此文件
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fileName =df.format(new Date())+"导出数据";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" +fileName + parseExcelType(excelType));
            this.exportExcel(list,sheetName,response.getOutputStream(), excelType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载模板
     * 在resource 相对路径获取指定模板
     */
    public static void downloadTemplate (InputStream inputStream, HttpServletResponse response, String fileName) throws IOException {
        //获取模板文件
        //File sourceFile = ResourceUtils.getFile("classpath:template/xlsx/Product.xlsx");
        // 转换为文件流
        //BufferedInputStream fs = new BufferedInputStream(new FileInputStream(sourceFile));
        //指定默认下载名称
        //String fileName = "Product.xlsx";
        //配置response的头
        try {
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            //循环从文件中读出数据后写出，完成下载
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public String parseExcelType (ExcelType excelType) {
        if (Objects.isNull(excelType)) {
            return ExcelType.XLS.getType();
        }
        return excelType.getType();
    }

    /**
     * 导出excel到指定路径
     *
     * @param list
     * @param sheetName
     * @param path
     * @return
     */
    public ReturnResult exportExcelToPath(List<T> list, String sheetName,String path, ExcelType excelType) {
        ReturnResult returnResult = null;
        try {
            OutputStream out = new FileOutputStream(path);
            returnResult = this.exportExcel(list,sheetName,out, excelType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnResult;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * 通过制定out，实现导出到磁盘/网络
     *
     * @param sheetName 工作表的名称
     */
    public ReturnResult exportExcel(List<T> list, String sheetName,OutputStream out, ExcelType excelType) {
        // 得到所有定义字段
        Field[] allFields = clazz.getDeclaredFields();
        List<Field> fields = new ArrayList<Field>();
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(Excel.class)) {
                fields.add(field);
            }
        }
        try {
            if (".xls".equals(excelType.getType())) {
                //2003
                HSSFWorkbook workbook = hSSFWorkbook(list, sheetName, fields);
                workbook.write(out);
            } else {
                //2007
                XSSFWorkbook workbook = xSSFWorkbook(list, sheetName, fields);
                workbook.write(out);
            }

            //String filename = encodingFilename(sheetName, excelType);
            //OutputStream out = new FileOutputStream(getfile() + filename);
            out.close();
            ReturnResult returnResult = new ReturnResult();
            returnResult.setMsg("filename");
            returnResult.setCode(1);
            return returnResult;
        }
        catch (Exception e) {
            log.error("关闭flush失败{}", e.getMessage());
            ReturnResult returnResult = new ReturnResult();
            returnResult.setMsg("导出Excel失败，请联系网站管理员！");
            returnResult.setCode(-1);
            return returnResult;
        }
    }


    public HSSFWorkbook hSSFWorkbook(List<T> list, String sheetName, List<Field> fields) {
        // 产生工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // excel2003中每个sheet中最多有65536行
        int sheetSize = 65536;
        // 取出一共有多少个sheet.
        double sheetNo = Math.ceil(list.size() / sheetSize);
        for (int index = 0; index <= sheetNo; index++) {
            // 产生工作表对象
            HSSFSheet sheet = workbook.createSheet();
            if (sheetNo == 0) {
                workbook.setSheetName(index, sheetName);
            } else {
                // 设置工作表的名称.
                workbook.setSheetName(index, sheetName + index);
            }
            HSSFRow row;
            HSSFCell cell; // 产生单元格

            // 产生一行
            row = sheet.createRow(0);
            // 写入各个字段的列头名称
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                Excel attr = field.getAnnotation(Excel.class);
                // 创建列
                cell = row.createCell(i);
                // 设置列中写入内容为String类型
                cell.setCellType(CellType.STRING);
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                if (attr.name().contains("注：")) {
                    HSSFFont font = workbook.createFont();
                    font.setColor(HSSFFont.COLOR_RED);
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(IndexedColors.BLUE.index);
                    sheet.setColumnWidth(i, 6000);
                } else {
                    HSSFFont font = workbook.createFont();
                    // 粗体显示
                    font.setBold(true);
                    // 选择需要用到的字体格式
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);

                    // 设置列宽
                    sheet.setColumnWidth(i, 3766);
                }
                //设置背景
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);

                // 写入列名
                cell.setCellValue(attr.name());

                // 如果设置了提示信息则鼠标放上去提示.
                if (!StringUtils.isEmpty(attr.prompt())) {
                    // 这里默认设了2-101列提示.
                    sheet.addValidationData(setHSSFPrompt("", attr.prompt(), 1, 100, i, i));
                }
                // 如果设置了combo属性则本列只能选择不能输入
                if (attr.combo().length > 0) {
                    // 这里默认设了2-101列只能选择不能输入.
                    sheet.addValidationData(setHSSFValidation(attr.combo(), 1, 100, i, i));
                }
            }

            int startNo = index * sheetSize;
            int endNo = Math.min(startNo + sheetSize, list.size());
            // 写入各条记录,每条记录对应excel表中的一行
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setAlignment(HorizontalAlignment.CENTER);
            cs.setVerticalAlignment(VerticalAlignment.CENTER);
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 - startNo);
                // 得到导出对象.
                T vo = list.get(i);
                for (int j = 0; j < fields.size(); j++) {
                    // 获得field.
                    Field field = fields.get(j);
                    // 设置实体类私有属性可访问
                    field.setAccessible(true);
                    Excel attr = field.getAnnotation(Excel.class);
                    try {
                        // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport()) {
                            // 创建cell
                            cell = row.createCell(j);
                            cell.setCellStyle(cs);
                            try {
                                if (String.valueOf(field.get(vo)).length() > 10) {
                                    throw new Exception("长度超过10位就不用转数字了");
                                }
                                // 如果可以转成数字则导出为数字类型
                                BigDecimal bc = new BigDecimal(String.valueOf(field.get(vo)));
                                cell.setCellType(CellType.NUMERIC);
                                cell.setCellValue(bc.doubleValue());
                            } catch (Exception e) {
                                cell.setCellType(CellType.STRING);
                                if (vo == null) {
                                    // 如果数据存在就填入,不存在填入空格.
                                    cell.setCellValue("");
                                } else {
                                    // 如果数据存在就填入,不存在填入空格.
                                    cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));
                                }

                            }
                        }
                    }
                    catch (Exception e) {
                        log.error("导出Excel失败{}", e.getMessage());
                    }
                }
            }
        }
        return workbook;
    }

    public XSSFWorkbook xSSFWorkbook(List<T> list, String sheetName, List<Field> fields) {
        // 产生工作薄对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        // excel2003中每个sheet中最多有65536行
        int sheetSize = 65536;
        // 取出一共有多少个sheet.
        double sheetNo = Math.ceil(list.size() / sheetSize);
        for (int index = 0; index <= sheetNo; index++) {
            // 产生工作表对象
            XSSFSheet sheet = workbook.createSheet();
            if (sheetNo == 0) {
                workbook.setSheetName(index, sheetName);
            } else {
                // 设置工作表的名称.
                workbook.setSheetName(index, sheetName + index);
            }
            XSSFRow row;
            XSSFCell cell; // 产生单元格

            // 产生一行
            row = sheet.createRow(0);
            // 写入各个字段的列头名称
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                Excel attr = field.getAnnotation(Excel.class);
                // 创建列
                 cell = row.createCell(i);
                // 设置列中写入内容为String类型
                cell.setCellType(CellType.STRING);
                XSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                if (attr.name().contains("注：")) {
                    XSSFFont font = workbook.createFont();
                    font.setColor(HSSFFont.COLOR_RED);
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(IndexedColors.BLUE.index);
                    sheet.setColumnWidth(i, 6000);
                } else {
                    XSSFFont font = workbook.createFont();
                    // 粗体显示
                    font.setBold(true);
                    // 选择需要用到的字体格式
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);

                    // 设置列宽
                    sheet.setColumnWidth(i, 3766);
                }
                //设置背景
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);

                // 写入列名
                cell.setCellValue(attr.name());

                // 如果设置了提示信息则鼠标放上去提示.
                if (!StringUtils.isEmpty(attr.prompt())) {
                    // 这里默认设了2-101列提示.
                    sheet.addValidationData(setHSSFPrompt("", attr.prompt(), 1, 100, i, i));
                }
                // 如果设置了combo属性则本列只能选择不能输入
                if (attr.combo().length > 0) {
                    // 这里默认设了2-101列只能选择不能输入.
                    sheet.addValidationData(setHSSFValidation(attr.combo(), 1, 100, i, i));
                }
            }

            int startNo = index * sheetSize;
            int endNo = Math.min(startNo + sheetSize, list.size());
            // 写入各条记录,每条记录对应excel表中的一行
            XSSFCellStyle cs = workbook.createCellStyle();
            cs.setAlignment(HorizontalAlignment.CENTER);
            cs.setVerticalAlignment(VerticalAlignment.CENTER);
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 - startNo);
                // 得到导出对象.
                T vo = list.get(i);
                for (int j = 0; j < fields.size(); j++) {
                    // 获得field.
                    Field field = fields.get(j);
                    // 设置实体类私有属性可访问
                    field.setAccessible(true);
                    Excel attr = field.getAnnotation(Excel.class);
                    try {
                        // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport()) {
                            // 创建cell
                            cell = row.createCell(j);
                            cell.setCellStyle(cs);
                            try {
                                if (String.valueOf(field.get(vo)).length() > 10) {
                                    throw new Exception("长度超过10位就不用转数字了");
                                }
                                // 如果可以转成数字则导出为数字类型
                                BigDecimal bc = new BigDecimal(String.valueOf(field.get(vo)));
                                cell.setCellType(CellType.NUMERIC);
                                cell.setCellValue(bc.doubleValue());
                            } catch (Exception e) {
                                cell.setCellType(CellType.STRING);
                                if (vo == null) {
                                    // 如果数据存在就填入,不存在填入空格.
                                    cell.setCellValue("");
                                } else {
                                    // 如果数据存在就填入,不存在填入空格.
                                    cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));
                                }

                            }
                        }
                    }
                    catch (Exception e) {
                        log.error("导出Excel失败{}", e.getMessage());
                    }
                }
            }
        }
        return workbook;
    }



    /**
     * 设置单元格上提示
     * @param promptTitle 标题
     * @param promptContent 内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     */
    public static HSSFDataValidation setHSSFPrompt(String promptTitle, String promptContent, int firstRow,
                                     int endRow, int firstCol, int endCol)
    {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation dataValidationView = new HSSFDataValidation(regions, constraint);
        dataValidationView.createPromptBox(promptTitle, promptContent);
        return dataValidationView;
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param textList 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     */
    public static HSSFDataValidation setHSSFValidation(String[] textList, int firstRow, int endRow,
                                         int firstCol, int endCol)
    {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textList);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        return new HSSFDataValidation(regions, constraint);
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename, ExcelType excelType)
    {
        filename = UUID.randomUUID().toString() + "_" + filename + parseExcelType(excelType);
        return filename;
    }


    public  String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}




/*    HSSFWorkbook workbook = new HSSFWorkbook(); //创建工作簿
    HSSFSheet sheet = workbook.createSheet("Test"); //创建SHEET页
    HSSFRow row = sheet.createRow(0); //创建行，从0开始
    HSSFCell cell = row.createCell(0);//创建列，从0开始
cell.setCellValue("姓名");

//设置样式
        HSSFCellStyle style = workbook.createCellStyle();
//加边框
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setBorderTop(BorderStyle.THIN); //上边框
//居中
        style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
//设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName("华文行楷");//设置字体名称
        font.setFontHeightInPoints((short)28);//设置字号
        font.setItalic(false);//设置是否为斜体
        font.setBold(true);//设置是否加粗
        font.setColor(IndexedColors.RED.index);//设置字体颜色
        style.setFont(font);
//设置背景
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.YELLOW.index);
//设置宽度和高度
        row.setHeightInPoints(30);//设置行的高度
        sheet.setColumnWidth(0, 20 * 256);//设置列的宽度
//渲染单元格
        cell.setCellStyle(style);*/

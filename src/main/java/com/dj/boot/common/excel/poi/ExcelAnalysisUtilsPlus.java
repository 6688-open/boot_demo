package com.dj.boot.common.excel.poi;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * excel解析工具
 * Created by fangkun on 14-11-13.
 * modified by gouhaonan on 16-12-23.
 *  改为解析xlsx格式
 *  单sheet最多支持500条记录
 *  不同sheet分开存储
 */
public class ExcelAnalysisUtilsPlus {

    /**
     * 解析excel文件
     * @param excelInputStream excel文件输入流
     * @return
     */
    public static List<List<List<String>>> analysisExcel(InputStream excelInputStream)  throws IOException {
        //工作簿集合
        List<List<List<String>>> result = new LinkedList<List<List<String>>>();
        if (excelInputStream == null) {
            return result;
        }
        //生成工作簿对象
        XSSFWorkbook hssfWorkbook = new XSSFWorkbook(excelInputStream);
        // 循环工作簿所有Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            //工作表集合
            List<List<String>> sheetResult = new LinkedList<List<String>>();
            XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if(hssfSheet == null){
                continue;
            }
            //若当前工作表为空或记录超过2000条，跳过不处理
            if (hssfSheet.getLastRowNum() == 0 || hssfSheet.getLastRowNum() > 2001) {
                continue;
            }
            //避免某行数据，因为最后几个单元格全是null，导致List取值时，超出List的size异常
            XSSFRow hssfRowHead = hssfSheet.getRow(0);
            int colNum=hssfRowHead.getLastCellNum();
            // 循环行Row
            for(int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
                boolean isEmptyRow = true;
                XSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if(hssfRow == null){
                    continue;
                }
                List<String> rowData = new ArrayList<String>();
                //防止数字类型的变更为科学计数法
                DecimalFormat df = new DecimalFormat("0");
                // 循环列Cell
                for(int cellNum = 0; cellNum <= colNum; cellNum++){
                    XSSFCell hssfCell = hssfRow.getCell(cellNum);
                    if(hssfCell == null){
                        rowData.add(null);
                    }else {
                        String cellValue;
                        switch (hssfCell.getCellType()) {
                            case STRING:
                                cellValue = hssfCell.getStringCellValue();
                                break;
                            case NUMERIC:
                                short format = hssfCell.getCellStyle().getDataFormat();
                                SimpleDateFormat dateFormat = null;
                                if(format == 14 || format == 31 || format == 57 || format == 58){
                                    //日期
                                    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    double value = hssfCell.getNumericCellValue();
                                    cellValue = dateFormat.format(DateUtil.getJavaDate(value));
                                }else {
                                    cellValue = String.valueOf(hssfCell.getNumericCellValue());//不做处理
                                    cellValue = new BigDecimal(cellValue).stripTrailingZeros().toPlainString();
                                }
                                break;
                            case BOOLEAN:
                                cellValue = String.valueOf(hssfCell.getBooleanCellValue());
                                break;
                            case BLANK:
                                cellValue = null;
                                break;
                            default:
                                cellValue = null;
                                break;
                        }
                        //如果每一行中有一个单元格值不为空，则认为该行数据有效
                        if(!StringUtils.isEmpty(cellValue)){
                            isEmptyRow = false;
                        }
                        rowData.add(cellValue);
                    }
                }
                //如果是空行，则认为改行数据无效，不添加到解析结果中
                if(!isEmptyRow){
                    sheetResult.add(rowData);
                }
            }
            result.add(sheetResult);
        }
        return result;
    }
}

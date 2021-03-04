package com.dj.boot.service.pdf;

import com.dj.boot.common.util.collection.CollectionUtils;
import com.dj.boot.pojo.pdf.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.pdf
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-02-14-47
 */
@Service
public class PdfServiceImpl implements PdfService {

    public void test(){
        for (int i = 0; i < 100; i++) {
            System.out.println(111);
        }
    }


    @SneakyThrows
    @Override
    public void exportPdf(HttpServletResponse response) {
        AddProjectRequestVO addRequestVo = getAddProjectRequestVO();


        java.util.List<QuestionModuleVO> list = Lists.newArrayList();

        for (int j = 1; j <= 2; j++) {
            QuestionModuleVO  q = new QuestionModuleVO();
            java.util.List<ProjectQuestionVO> questionList = Lists.newArrayList();
            for (int i = 0; i < 3; i++) {
                questionList.add(getProjectQuestionVO());
            }
            q.setGroupName("测试分组----: " + j);
            q.setQuestions(questionList);

            list.add(q);
        }








        // 设置字体
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        com.itextpdf.text.Font FontChinese24 = new com.itextpdf.text.Font(bfChinese, 24, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font FontChinese16 = new com.itextpdf.text.Font(bfChinese, 16, com.itextpdf.text.Font.NORMAL);
        com.itextpdf.text.Font FontChinese12 = new com.itextpdf.text.Font(bfChinese, 10, com.itextpdf.text.Font.NORMAL);
        //创建一个 Document 设置大小 样式
        Document document = new Document(PageSize.A4, 70, 70, 20, 100);// 上下左右页边距
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/pdf");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=projectDetails.pdf");
        // Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        //设置此文档的加密选项
        writer.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        document.open();
        int high = 13;
        int border = 1;
        // 边距
        Paragraph paragraph = new Paragraph(40);
        // 1 2 3 中右左
        // 对齐方式
        paragraph.setAlignment(1);
        // 设置段落字体
        paragraph.setFont(FontChinese24);
        Chunk chunk = new Chunk("客户基本信息");
        paragraph.setSpacingAfter(5);
        paragraph.add(chunk);
        document.add(paragraph);
        // 边距
        Paragraph paragraph1 = new Paragraph(40);
        //paragraph1.setSpacingAfter(20);
        float[] childcolumnWidth2 = { 100, 100, 20, 100, 130 };
        PdfPTable table = new PdfPTable(childcolumnWidth2);
        table.setTotalWidth(450);

        // 宽度算正确+
        table.setLockedWidth(true);
        table.addCell(drawPdfPCell("Project Basic Info", FontChinese16, 3, 30, 1, border, 2, 3, 15));
        table.addCell(drawPdfPCell("", FontChinese16, 3, 30, 1, border, 1, 3, 15));
        table.addCell(drawPdfPCell("Customer Info", FontChinese16, 3, 30, 1, border, 2, 3, 15));

        table.addCell(drawPdfPCell("project Name:", FontChinese12, 3, high, border, 1, 1, 2, 10));
        table.addCell(drawPdfPCell(addRequestVo.getProjectName(), FontChinese12, 3, high, border, 1, 1, 2, 6));
        table.addCell(drawPdfPCell("", FontChinese16, 3, high, 1, border, 1, 2, 15));

        table.addCell(drawPdfPCell("Frist Name:", FontChinese12, 3, high, border, 1, 1, 1, 10));
        table.addCell(drawPdfPCell(addRequestVo.getFirstName(), FontChinese12, 3, high, border, 1, 1, 1, 6));
        table.addCell(drawPdfPCell("lastName:", FontChinese12, 3, high, border, 1, 1, 1, 11));
        table.addCell(drawPdfPCell(addRequestVo.getLastName(), FontChinese12, 3, high, border, 1, 1, 1, 7));
        table.addCell(drawPdfPCell("description:", FontChinese12, 3, high, border, 1, 1, 3, 9));
        table.addCell(drawPdfPCell(addRequestVo.getDescription(), FontChinese12, 3, high, border, 1, 1, 3, 5));
        table.addCell(drawPdfPCell("", FontChinese16, 3, high, 1, border, 1, 3, 15));
        table.addCell(drawPdfPCell("Address:", FontChinese12, 3, high, border, 1, 1, 1, 11));

        StringBuffer address = getAddressString(addRequestVo.getUpdateAddress());
        System.out.println(address);

        table.addCell(drawPdfPCell(address.toString(), FontChinese12, 3, high, border, 1, 1, 1, 7));
        table.addCell(drawPdfPCell("Phone Number", FontChinese12, 3, high, border, 1, 1, 1, 11));
        table.addCell(drawPdfPCell(addRequestVo.getPhoneNumber(), FontChinese12, 3, high, border, 1, 1, 1, 7));
        table.addCell(drawPdfPCell("Email Address:", FontChinese12, 3, high, border, 1, 1, 1, 9));
        table.addCell(drawPdfPCell(addRequestVo.getEmail(), FontChinese12, 3, high, border, 1, 1, 1, 5));

        paragraph1.add(table);
        document.add(paragraph1);


        Paragraph paragraphSex = new Paragraph(40);
        Chunk chunk1 = new Chunk(" ");
        paragraphSex.add(chunk1);
        document.add(paragraphSex);

        for (QuestionModuleVO questionModuleVO : list) {
            Phrase paragraphmodule1 = new Phrase();
            // 1 2 3 中右左
            // 设置段落字体
            paragraphmodule1.setFont(FontChinese16);
            Chunk module = new Chunk(questionModuleVO.getGroupName());
            paragraphmodule1.add(module);
            paragraphmodule1.add(Chunk.NEWLINE);
            java.util.List<ProjectQuestionVO> questionList = questionModuleVO.getQuestions();
            for (ProjectQuestionVO question : questionList) {
                // 边距
                Phrase paragraphQuestion = new Phrase(40);
                paragraphQuestion.setLeading(20);
                // 1 2 3 中右左
                // paragraphQuestion.setAlignment(3);// 对齐方式
                // 设置段落字体
                paragraphQuestion.setFont(FontChinese12);
                if (StringUtils.isNotBlank(question.getName())) {
                    Chunk questionChunk = new Chunk("  " + question.getName());
                    paragraphQuestion.add(questionChunk);
                }
                java.util.List<QuestionItemVO> questionItems = question.getQuestionItems();

                if (questionItems.size() > 1) {
                    float[] textTablecolumnWidth2 = { 80, 80, 80, 80,80,80 };
                    PdfPTable textTable = new PdfPTable(textTablecolumnWidth2);
                    textTable.setTotalWidth(480);
                    // 宽度算正确+
                    textTable.setLockedWidth(true);
                    textTable.setHorizontalAlignment(3);
                    for (QuestionItemVO questionItem : questionItems) {
                        textTable.addCell(
                                drawPdfPCell(questionItem.getItemName(), FontChinese12, 3, 20, 0, 1, 1, 1, 15));
                        textTable.addCell(
                                drawPdfPCell(questionItem.getAnswer(), FontChinese12, 3, 20, 0, 1, 1, 1, 15));
                    }
                    paragraphQuestion.add(textTable);
                }

//                if ("radio".equals(question.getType())) {
//                    boolean radioflag = false;
//                    for (QuestionItemVO questionItem : questionItems) {
//                        if (StringUtils.isNotBlank(questionItem.getAnswer())) {
//                            paragraphQuestion.setFont(FontChinese12);// 设置段落字体
//                            Chunk questionitem = new Chunk("    " + questionItem.getAnswer());
//                            paragraphQuestion.add(Chunk.NEWLINE);
//                            paragraphQuestion.add(questionitem);
//                            radioflag = true;
//                        }
//                    }
//                    if (!radioflag) {
//                        paragraphQuestion.setFont(FontChinese12);// 设置段落字体
//                        Chunk questionitem = new Chunk("    ");
//                        paragraphQuestion.add(Chunk.NEWLINE);
//                        paragraphQuestion.add(questionitem);
//                    }
//                } else if ("text".equals(question.getType())) {
//                    if (questionItems.size() > 1) {
//                        PdfPTable textTable = new PdfPTable(6);
//                        textTable.setTotalWidth(480);
//                        textTable.setLockedWidth(true);// 宽度算正确+
//                        textTable.setHorizontalAlignment(3);
//                        float[] textTablecolumnWidth2 = { 80, 80, 80, 80,80,80 };
//                        textTable.setTotalWidth(textTablecolumnWidth2);
//                        for (QuestionItemVO questionItem : questionItems) {
//                            textTable.addCell(
//                                    drawPdfPCell(questionItem.getItemName(), FontChinese12, 3, 20, 0, 1, 1, 1, 15));
//                            textTable.addCell(
//                                    drawPdfPCell(questionItem.getAnswer(), FontChinese12, 3, 20, 0, 1, 1, 1, 15));
//                        }
//                        paragraphQuestion.add(textTable);
//                    }
//                } else if ("textarea".equals(question.getType())) {
//                    PdfPTable textTable = new PdfPTable(1);
//                    textTable.setTotalWidth(450);
//                    // 宽度算正确+
//                    textTable.setLockedWidth(true);
//                    textTable.setHorizontalAlignment(3);
//                    float[] textTablecolumnWidth2 = { 450 };
//                    textTable.setTotalWidth(textTablecolumnWidth2);
//                    for (QuestionItemVO questionItem : questionItems) {
//                        PdfPCell textarea=null;
//                        if(StringUtils.isNotBlank(questionItem.getAnswer())) {
//                            textarea = drawPdfPCell(questionItem.getAnswer(), FontChinese12, 3, 20, 0, 3, 2, 2,-1);
//                        }else {
//                            textarea = drawPdfPCell(questionItem.getAnswer(), FontChinese12, 3, 70, 0, 3, 2, 2,-1);
//                        }
//                        textarea.setPadding(20);
//                        textTable.addCell(textarea);
//                    }
//                    paragraphQuestion.add(textTable);
//                } else if ("Mixed".equals(question.getType())) {
//                    java.util.List<QuestionItemVO> targetquestionItem = CollectionUtils.list(questionItems,
//                            i -> !StringUtils.isEmpty(i.getItemType()) && "checkbox".equals(i.getItemType()));
//                    java.util.List<QuestionItemVO> textareaQuestionItem = CollectionUtils.list(questionItems,
//                            i -> !StringUtils.isEmpty(i.getItemType()) && "textarea".equals(i.getItemType()));
//                    java.util.List<QuestionItemVO> textItemList = CollectionUtils.list(questionItems,
//                            i -> !StringUtils.isEmpty(i.getItemType()) && "text".equals(i.getItemType()));
//                    java.util.List<QuestionItemVO> radioItemList = CollectionUtils.list(questionItems,
//                            i -> !StringUtils.isEmpty(i.getItemType()) && "radio".equals(i.getItemType()));
//                    if(radioItemList.size()>0) {
//                        boolean radioflag = false;
//                        for (QuestionItemVO questionItem : questionItems) {
//                            if (StringUtils.isNotBlank(questionItem.getAnswer())) {
//                                // 设置段落字体
//                                paragraphQuestion.setFont(FontChinese12);
//                                String answer = "";
//                                answer = questionItem.getAnswer();
//                                Chunk questionitem = new Chunk("    " + answer);
//                                paragraphQuestion.add(Chunk.NEWLINE);
//                                paragraphQuestion.add(questionitem);
//                                radioflag = true;
//                            }
//                        }
//                        if (!radioflag) {
//                            // 设置段落字体
//                            paragraphQuestion.setFont(FontChinese12);
//                            Chunk questionitem = new Chunk("    ");
//                            paragraphQuestion.add(Chunk.NEWLINE);
//                            paragraphQuestion.add(questionitem);
//                        }
//                    }
//                    if (textItemList.size() > 1) {
//                        PdfPTable textTable = new PdfPTable(4);
//                        textTable.setTotalWidth(440);
//                        // 宽度算正确+
//                        textTable.setLockedWidth(true);
//                        textTable.setHorizontalAlignment(3);
//                        float[] textTablecolumnWidth2 = { 110, 110, 110, 110 };
//                        textTable.setTotalWidth(textTablecolumnWidth2);
//                        for (QuestionItemVO questionItem : textItemList) {
//                            textTable.addCell(
//                                    drawPdfPCell(questionItem.getItemName(), FontChinese12, 3, 20, 0, 1, 1, 1, 15));
//                            textTable.addCell(
//                                    drawPdfPCell(questionItem.getAnswer(), FontChinese12, 3, 20, 0, 1, 1, 1, 15));
//                        }
//                        paragraphQuestion.add(textTable);
//                    }
//                    PdfPTable textTable = new PdfPTable(1);
//                    textTable.setTotalWidth(450);
//                    // 宽度算正确+
//                    textTable.setLockedWidth(true);
//                    if (targetquestionItem.size() > 0) {
//                        boolean checkboxFlag = false;
//                        for (QuestionItemVO questionItem : targetquestionItem) {
//                            boolean flag = false;
//                            if ("true".equals(questionItem.getAnswer())) {
//                                flag = true;
//                            }
//                            if (flag) {
//                                PdfPCell checkBox = drawPdfPCell(questionItem.getItemName(), FontChinese12, 3, 20, 0, 0,
//                                        1, 1, 15);
//                                // 表格开始
//                                textTable.addCell(checkBox);
//                                checkboxFlag = true;
//                            }
//                        }
//                        if (!checkboxFlag) {
//                            PdfPCell checkBox = drawPdfPCell(" ", FontChinese12, 3, 20, 0, 0, 1, 1, 15);
//                            // 表格开始
//                            textTable.addCell(checkBox);
//                        }
//                        paragraphQuestion.add(textTable);
//                    }
//                    if (textareaQuestionItem.size() > 0) {
//                        PdfPTable textTable1 = new PdfPTable(1);
//                        textTable1.setTotalWidth(450);
//                        // 宽度算正确+
//                        textTable1.setLockedWidth(true);
//                        textTable1.setHorizontalAlignment(3);
//                        float[] textTablecolumnWidth2 = { 450 };
//                        textTable1.setTotalWidth(textTablecolumnWidth2);
//                        for (QuestionItemVO questionItem : textareaQuestionItem) {
//                            textTable1.addCell(drawPdfPCell(questionItem.getItemName(), FontChinese12, 3, 20, 0, 0, 1, 1, 15));
//                            PdfPCell pdfscell =null;
//                            if(StringUtils.isNotBlank(questionItem.getAnswer())) {
//                                pdfscell=drawPdfPCell(questionItem.getAnswer(), FontChinese12, 3, 20, 0, 3, 2, 2,-1);
//                            }else {
//                                pdfscell=drawPdfPCell(questionItem.getAnswer(), FontChinese12, 3, 70, 0, 3, 2, 2,-1);
//                            }
//                            pdfscell.setPadding(20);
//                            textTable1.addCell(pdfscell);
//                        }
//                        paragraphQuestion.add(textTable1);
//                    }
//                }
                paragraphmodule1.add(paragraphQuestion);
                paragraphmodule1.add(Chunk.NEWLINE);
            }
            document.add(paragraphmodule1);
        }

        document.close();
    }


    /**
     *
     * Method description : 画一行中的一列（格子）
     *
     * @param cellText  格子的文字
     * @param font  字体类型
     * @param side      字体大小
     * @param alignment 对齐方式
     * @return
     * @throws Exception
     *
     */
    private static PdfPCell drawPdfPCell(String cellText, Font font, int alignment, int minimumHeight, int border,
                                         int borderWidth, int colspan, int rowspan, int side) throws Exception {
        // 为null会报错 防止报错
        if (cellText == null) {
            cellText = " ";
        }
        // 表格开始
        Paragraph paragraph = new Paragraph(cellText);
        // 1 2 3 中右左
        // 对齐方式
        paragraph.setAlignment(alignment);
        // 设置段落字体
        paragraph.setFont(font);
        // 左缩进
        paragraph.setIndentationLeft(12);
        PdfPCell cell = new PdfPCell();
        cell.setUseAscender(true);
        cell.setBorderWidth(borderWidth);
        cell.setBorder(border);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setPadding(5);
        cell.setBorderWidthLeft(1f);
        cell.setBorderWidthRight(1f);
        cell.setBorderWidthBottom(1f);
        cell.setBorderWidthTop(1f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        if (-1 != side) {
            cell.disableBorderSide(side);
        }
        // 设置cell垂直居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        // 设置单元格最小高度，当前行最小高度
        cell.setMinimumHeight(minimumHeight);
        cell.addElement(paragraph);
        return cell;
    }











    private AddProjectRequestVO getAddProjectRequestVO() {
        AddProjectRequestVO addRequestVo = new AddProjectRequestVO();
        UpdateAddressVo update = new UpdateAddressVo();
        update.setAddress2("丰县欢口镇");
        update.setAddress3("欢口");
        update.setCity("徐州市");
        update.setCompanyName("京东数科");
        update.setPostalCode("201104");
        update.setStateName("江苏省");
        update.setStreet("欢口街");
        addRequestVo.setUpdateAddress(update);
        addRequestVo.setDescription("如何学习JAVA编程");
        addRequestVo.setEmail("18351867657@163.com");
        addRequestVo.setFirstName("wang");
        addRequestVo.setLastName("jia");
        addRequestVo.setPhoneNumber("18351867657");
        addRequestVo.setProjectName("京东物流应急项目");
        return addRequestVo;
    }




    private ProjectQuestionVO getProjectQuestionVO() {
        ProjectQuestionVO p = new ProjectQuestionVO();
        p.setName("如何获取承运商？？？？？？？？？？？？？？？");
        p.setType("text");
        List<QuestionItemVO> questionItemVOList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            QuestionItemVO item = new QuestionItemVO();
            item.setItemType("ItemType"+i);
            item.setAnswer("Answer"+i);
            item.setItemName("ItemName"+i);
            questionItemVOList.add(item);
        }
        p.setQuestionItems(questionItemVOList);
        return p;
    }



    private StringBuffer getAddressString(UpdateAddressVo updateaddress) {
        StringBuffer address = new StringBuffer();
        if (StringUtils.isNotBlank(updateaddress.getStreet())) {
            address.append(updateaddress.getStreet());
        }
        if (StringUtils.isNotBlank(updateaddress.getAddress2())) {
            address.append("，" + updateaddress.getAddress2());
        }
        if (StringUtils.isNotBlank(updateaddress.getAddress3())) {
            address.append("，" + updateaddress.getAddress3());
        }
        if (StringUtils.isNotBlank(updateaddress.getCity())) {
            address.append("，" + updateaddress.getCity());
        }
        if (StringUtils.isNotBlank(updateaddress.getStateName())) {
            address.append("，" + updateaddress.getStateName());
        }
        if (StringUtils.isNotBlank(updateaddress.getPostalCode())) {
            address.append("，" + updateaddress.getPostalCode());
        }
        if (StringUtils.isNotBlank(updateaddress.getCompanyName())) {
            address.append("," + updateaddress.getCompanyName());
        }
        return address;
    }



}

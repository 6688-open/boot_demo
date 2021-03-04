package com.dj.boot.controller.excel;

import com.dj.boot.common.base.Response;
import com.dj.boot.common.base.ResultModel;
import com.dj.boot.common.excel.clps.ExportExcel;
import com.dj.boot.common.excel.exc.ExcelType;
import com.dj.boot.common.excel.exc.ExcelUtil;
import com.dj.boot.common.excel.exc.UserDto;
import com.dj.boot.common.excel.poi.ExcelAnalysisUtilsPlus;
import com.dj.boot.common.excel.poi.ReceiptsPerformImportDto;
import com.dj.boot.common.excel.poi.ReceiptsPerformItemDto;
import com.dj.boot.common.threadpoolutil.ThreadPoolUtils;
import com.dj.boot.common.util.date.DateUtil;
import com.dj.boot.pojo.User;
import com.dj.boot.service.pdf.PdfService;
import com.dj.boot.service.user.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Api(value = "excel操作接口")
@RestController
@RequestMapping("/excel/")
@Slf4j
public class ExcelController {

    protected static final Logger logger = Logger.getLogger(ExcelController.class);

    @Resource
    private PdfService pdfService;

    @Resource
    private UserService userService;

    /**
     * 异常报备单导出
     */
    public static final String ERROR_DEVICE_ORDER_FILENAME = "异常报备单";
    public static final String[] ERROR_DEVICE_ORDER_FIELDNAMES = {"主键","性别","姓名","密码","创建时间","日期"};
    public static final String[] ERROR_DEVICE_ORDER_FIELDPRENAMES = {"id","sex","userName","password","created","createTimeStr"};

    @GetMapping("export")
    public void exportExcel(HttpServletResponse response){
        ExcelUtil<UserDto> excelUtil = new ExcelUtil<>(UserDto.class);
        List<UserDto> userDtos = new ArrayList<>();
        UserDto build1 = UserDto.builder().username("333").password("2222").id("222").build();
        UserDto build2 = UserDto.builder().username("333").password("2222").id("222").build();
        UserDto build3 = UserDto.builder().username("3333").password("2222").id("222").build();
        UserDto build4 = UserDto.builder().username("44444").password("2222").id("222").build();
        userDtos.add(build1);
        userDtos.add(build2);
        userDtos.add(build3);
        userDtos.add(build4);
        excelUtil.exportExcelToWeb(response, userDtos,"用户信息表", ExcelType.XLSX);
    }


    @PostMapping("import")
    public Response importExcel(@RequestParam("file") MultipartFile file){
        Response response = Response.success();
        if (file.isEmpty()) {
            response.setMsg("文件为空");
            response.setCode(400);
            return response;
        }
        try {
            long time1=System.currentTimeMillis();

            ExcelUtil<UserDto> excelUtil = new ExcelUtil<>(UserDto.class);
            List<UserDto> userDtos = excelUtil.importExcel(null, file.getInputStream());
            System.out.println(userDtos.size());


            List<User> userList = Lists.newArrayList();
            userDtos.forEach(userDto -> {
                User user = new User();
                user.setEmail("18351867657@163.com");
                user.setUserName(userDto.getUsername());
                user.setPassword(userDto.getPassword());
                user.setSex(1);
                user.setSalt("userDtos.forEach(userDto");
                user.setCreateTime(LocalDateTime.now());
                userList.add(user);
            });

//            userList.forEach(user -> {
//                userService.save(user);
//            });

            getNameByList(userList);

            long time2=System.currentTimeMillis();
            System.out.println("当前程序耗时："+(time2-time1)+"ms");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  response;
    }

    private static final Integer BATCH_NUM = 10;

    private void getNameByList(List<User> list) {
        System.out.println("启动所有核心线程");
        //启动所有核心线程
        ThreadPoolUtils.getThreadPoolExecutor().prestartAllCoreThreads();
        //开启多个线程执行多个任务

        int len = list.size();
        // 计算size
        int size = len / BATCH_NUM;
        // 计算余数
        int remainder = len % BATCH_NUM;
        if (remainder > 0) {
            size = size + 1;
        }
        CountDownLatch countDownLatch = new CountDownLatch(size);
        List<Future> futureList = new ArrayList<>();
        // 分批异步处理
        for (int i = 0; i < size; i++) {
            List<User> userList = list.stream().skip(i * BATCH_NUM).limit(BATCH_NUM).collect(Collectors.toList());

            Future<String> future = ThreadPoolUtils.getThreadPoolExecutor().submit(new GetUser(userList, countDownLatch));
            futureList.add(future);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            // 获取所有并发任务的运行结果
            for (Future f : futureList) {
                // 从Future对象上获取任务的返回值，并输出到控制台
                System.out.println(">>>" + f.get().toString());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }


    class GetUser implements Callable<String> {

        private List<User> userList;
        private CountDownLatch countDownLatch;

        public GetUser(List<User> userList, CountDownLatch countDownLatch) {
            this.userList = userList;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public String call() throws Exception {
            try {
                Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = f.format(new Date());
                System.out.println("当前线程名称-------"+Thread.currentThread().getName() + "时间 " + format + "数量:" + userList.size());

                userList.forEach(user -> {
                    userService.save(user);
                });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
            return "当前线程名称-------"+Thread.currentThread().getName()  + "数量:" + userList.size();
        }
    }

    /**
     * 用户下载 导入ex工作表模板
     * @return
     */
    @GetMapping("uploadTemp")
    public void uploadTemp(HttpServletResponse response) {
        try {
            //从resource 下获取
            //InputStream inputStream = this.getClass().getResourceAsStream("/template/xlsx/Product.xlsx");
            //InputStream inputStream = new FileInputStream("D:/user.xlsx");
            InputStream inputStream = this.getClass().getResourceAsStream("/template/xlsx/Product.xlsx");
            ExcelUtil.downloadTemplate(inputStream, response, "Product.xlsx");
        } catch (Exception e) {
        }
    }

    /**
     * 创建空的excel  下载
     * @param response
     * @throws IOException
     */
    private void createWorkBookDownloadTemplate(HttpServletResponse response) throws IOException {
        //
        //创建工作表
        Workbook workbook = new XSSFWorkbook();
        //创建工作表
        Sheet sheet = workbook.createSheet();
        //设置表头
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("商品名称名称");
        row.createCell(1).setCellValue("邮费");
        row.createCell(2).setCellValue("描述");
        row.createCell(3).setCellValue("商品类型名称");
        row.createCell(4).setCellValue("商品Sku属性名");
        row.createCell(5).setCellValue("商品Sku属性值(多个属性值用逗号分开)");
        row.createCell(6).setCellValue("商品Sku属性");
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("商品导入模板表.xlsx").getBytes(), "iso-8859-1"));
        workbook.write(response.getOutputStream());
    }





    /**
     * 导出PDF
     * @return
     */
    @GetMapping("exportPdf")
    public void exportPdf(HttpServletResponse response) {
        try {
            pdfService.exportPdf(response);
        } catch (Exception e) {

        }
    }




    /*
     * 导入入库单
     * */
    @PostMapping(value = "importReceiptsPerform")
    public ResultModel importGoodsCategory(@RequestParam MultipartFile importFile, HttpServletRequest request
    ) {
        if (importFile == null){
            return  new ResultModel().error("文件不能为空");
        }

        try {
            List<List<List<String>>>  excleList = ExcelAnalysisUtilsPlus.analysisExcel(importFile.getInputStream());
            if (excleList == null || excleList.isEmpty() || excleList.size() <1){
                return   new ResultModel().error("没有需要导入的数据");
            }
            List<List<String>> rows = excleList.get(0);
            if (rows == null || rows.isEmpty() || rows.size() < 0) {
                return new ResultModel().error("没有需要导入的数据");
            }

            List<ReceiptsPerformImportDto> receiptsPerformList = new ArrayList<>(rows.size());

            String errorMsg = null;
            for (int i = 0; i < rows.size(); i++) {
                List<String> row = rows.get(i);
                if (row == null) {
                    continue;
                }

                int rowNum = i + 2;

                ReceiptsPerformImportDto receiptsPerform = new ReceiptsPerformImportDto();

                if (row.size() <= 1 || StringUtils.isBlank(row.get(0))) {
                    errorMsg = "第" + rowNum + "行采购单号(防重码)为空";
                    break;
                }
                receiptsPerform.setReferenceReceiptsNo(row.get(0).trim());


                if (row.size() <= 2 || StringUtils.isBlank(row.get(1))) {
                    errorMsg = "第" + rowNum + "行仓库名称为空";
                    break;
                }
                receiptsPerform.setWarehouseName(row.get(1).trim());


                List<ReceiptsPerformItemDto> receiptsPerformItemDtoList = new ArrayList<>(rows.size());
                ReceiptsPerformItemDto receiptsPerformItemDto = new ReceiptsPerformItemDto();
                if (row.size() <= 3 || StringUtils.isBlank(row.get(2))) {
                    errorMsg = "第" + rowNum + "行物资名称为空";
                    break;
                }
                receiptsPerformItemDto.setGoodsName(row.get(2).trim());

                if (row.size() <= 4 || StringUtils.isBlank(row.get(3))) {
                    errorMsg = "第" + rowNum + "行机构名称为空";
                    break;
                }
                receiptsPerform.setOrgName(row.get(3).trim());

                if (row.size() <= 5 || Integer.parseInt(row.get(4)) <= 0) {
                    errorMsg = "第" + rowNum + "行采购类型为空";
                    break;
                }
                receiptsPerform.setOrderType(Integer.valueOf(row.get(4).trim()));

                if (row.size() <= 6 || StringUtils.isBlank(row.get(5))) {
                    errorMsg = "第" + rowNum + "商品数量不能为空";
                    break;
                }
                receiptsPerformItemDto.setGoodsApplyQty(new BigDecimal(row.get(5).trim()));


                if (row.size() <= 7 && StringUtils.isNotBlank(row.get(6))) {
                    errorMsg = "第" + rowNum + "计划完成日期为空";
                    break;
                }
                //Date planCompleteTime  = (DateUtil.parseDate(row.get(6).trim() + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
                receiptsPerform.setPlanCompleteTime(new Date());


                if (row.size() <= 8 || Integer.parseInt(row.get(7)) <= 0) {
                    errorMsg = "第" + rowNum + "行来源为空";
                    break;
                }
                receiptsPerform.setSource(Integer.valueOf(row.get(7).trim()));

                if (row.size() <= 9) {
                    errorMsg = "第" + rowNum + "行生产日期为空";
                    break;
                }
                receiptsPerformItemDto.setProductTime(StringUtils.trim(row.get(8)));
                //默认良品
                receiptsPerformItemDto.setGoodsLevel("100");

//                if (row.size() <=8 && StringUtils.isNotBlank(row.get(7))) {
//                    errorMsg = "第" + rowNum + "行来源为空";
//                    break;
//                }else if (StringUtils.equals(row.get(10),"需求")){
//                    receiptsPerform.setSource(PoSourceEnum.DEMAND.getCode());
//                }else{
//                    receiptsPerform.setSource(PoSourceEnum.PLAN.getCode());
//                }
                receiptsPerformItemDtoList.add(receiptsPerformItemDto);

                receiptsPerform.setOrderSource((byte) 3);
                receiptsPerform.setReceiptsPerformItemDtoList(receiptsPerformItemDtoList);
                receiptsPerformList.add(receiptsPerform);
            }
//            if (StringUtils.isNotEmpty(errorMsg)) {
//                return Response.error(Response.ERROR_PARAM, errorMsg);
//            }

//            List<ReceiptsPerformImportDto> setList = new ArrayList<>();
//            if (receiptsPerformList.size()>1){
//                setList = mergeList(receiptsPerformList);
//            }else{
//                setList = receiptsPerformList;
//            }

//            for (ReceiptsPerformImportDto dto : setList) {
//                dto.setId(null);
//                dto.setCreateUser(loginPin);
//                dto.setUpdateUser(loginPin);
//                dto.setTenantId(tenantNo);
//                try {
//                    Response response = receiptsPerformService.saveListReceiptsOrder(dto);
//                    if (!response.isSuccess()) {
//                        throw new RuntimeException(response.getMsg());
//                    }
//                } catch (Exception e) {
//                    log.error("导入入库单异常"+ JSON.toJSONString(dto),e);
//                }
//            }

            return new ResultModel().success("成功");
        } catch (Exception e) {
            System.out.println("导入入库单，系统出错，错误信息："+ e);
            e.printStackTrace();
        }
        return null;
    }












    /**
     * clps导出异常报备单
     *
     * @param response
     * @param condition 导出条件
     * @throws IOException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    @RequestMapping(value = "exportErrorDeviceOrderDetail")
    public void exportErrorDeviceOrderDetail(HttpServletRequest request, HttpServletResponse response, com.dj.boot.pojo.UserDto condition) throws IOException, IllegalAccessException, NoSuchFieldException {
        try {
            List<User> userList = userService.findUserListByCondition(condition);
            userList.forEach(user -> {
                user.setCreated(new Date());
                user.setCreateTimeStr(DateUtil.format(new Date()));
            });
            //导出excel开始
            ExportExcel.exportExcel(response, ERROR_DEVICE_ORDER_FILENAME, ERROR_DEVICE_ORDER_FIELDNAMES, ERROR_DEVICE_ORDER_FIELDPRENAMES, userList);
        } catch (Exception e) {
            logger.error("异常报备单导出异常:", e);
        }
    }

}

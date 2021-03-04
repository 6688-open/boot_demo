package com.dj.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.btp.exception.SomsBizException;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.base.ResultModel;
import com.dj.boot.common.enums.ReceiptsPerformTypeEnum;
import com.dj.boot.pojo.ComplexAssembly;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.Warehouse;
import com.dj.boot.pojo.purchase.InStoreAcceptanceRequest;
import com.dj.boot.pojo.purchase.ReceivingLineDto;
import com.dj.boot.pojo.purchase.insr.AcceptanceDto;
import com.dj.boot.pojo.purchase.insr.PurchaseItemDto;
import com.dj.boot.pojo.purchase.insr.PurchaseMainDto;
import com.dj.boot.service.test.TestUserService;
import com.dj.boot.service.test.impl.TestUserServiceImpl;
import com.dj.boot.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.redisson.misc.Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ext.wangjia
 */
@Api(value = "测试接口 （reids 发布消息）")
@RequestMapping("/test/")
@RestController
@Slf4j
public class TestController {

    @Value("${qiniu.url}")
    private String qiniuUrl;

    @Resource
    private ComplexAssembly complexAssembly;

    @Autowired
    private UserService userService;

    @Autowired
    private TestUserService testService;


    @ApiOperation(value = "测试切面注解", notes="测试切面注解")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "根据标id查询", paramType = "query",  required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "name", paramType = "query",  required = true , dataType = "String")
    })
    @PostMapping("test")
    public Response<String> test(String name) {
        System.out.println(33333);
        try {
            Long count = userService.getCount();
            System.out.println(count);
        } catch (SomsBizException e) {
            log.error("userService.getCount() somsBizException",e);
            return Response.error(Integer.valueOf(e.getCode()), e.getMessage());
        } catch (Exception e) {
            log.error("userService.getCount() Exception",e);
            return Response.error(Response.ERROR_BUSINESS, "系统异常");
        }
        List<User> list = userService.getList();
        System.out.println(list);
        System.out.println(222);

        return Response.success();
    }


    /**
     * 将方法维护到枚举中 初始化  动态获取方法然后通过反射invoke执行
     * @return
     */
    @PostMapping("testQueryByOpId")
    public Response testQueryByOpId() {
       User user = new User();
       user.setSex(103);
        Response response = testService.queryByOpId(user);
        return response;
    }






    @PostMapping("testJson")
    public String testJson(@RequestBody  Map<String, Object> map) throws JsonProcessingException {
        String s = JSONObject.toJSONString(map);


        List<User> list = Lists.newArrayList();
        User u = new User();
        u.setUserName("111");
        User u2 = new User();
        u2.setUserName("222");
        list.add(u);
        list.add(u2);
        String listStr = JSONObject.toJSONString(list);

        User user = new User();
        user.setUserName(s);
        user.setPassword(listStr);


        String userStr = JSONObject.toJSONString(user);
        System.out.println(userStr);


        String a = "[{\"receivingNo\":\"12332\",\"containerNo\":\"1111\",\"realReceivedUsers\":\"2222\",\"bookTips\":\"1111\",\"isAppearance\":1,\"qtyMatch\":1,\"modelMatch\":1,\"qualityTag\":1,\"other\":\"2222\",\"purchasingEligibility\":1,\"technicalFocusEligibility\":1,\"archivesEligibility\":1,\"isPass\":1,\"receivingLines\":[{\"lineNo\":\"222\",\"itemId\":\"111\",\"itemName\":\"22\",\"expectedQty\":1,\"productLevel\":\"11\",\"isvLotattrs\":\"11\",\"packageNo\":\"22\",\"packageName\":\"33\",\"unitNo\":\"4\",\"unitName\":\"44\",\"packingQty\":1,\"uomQty\":1}]}]";
        List<Map<String,Object>> rtwBatAttrModelMapList = JSONObject.parseObject(a,List.class);
        return "boot,ok";
    }


    /**
     * 获取单据类型
     * @return
     */
    @ApiOperation(value = "获取单据类型", notes="获取单据类型")
    @GetMapping("/getReceiptsPerformType")
    public ResultModel getReceiptsPerformType() {
        try {
            String parse = ReceiptsPerformTypeEnum.parse(1);
            return new ResultModel().success(ReceiptsPerformTypeEnum.OPTIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @ApiOperation(value = "获取单据类型", notes="获取单据类型")
    @GetMapping("/complexAssemblyTest")
    public ResultModel complexAssemblyTest() {
        try {
            List<String> list = complexAssembly.getList();
            Map<String, String> map = complexAssembly.getMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 去掉servie注解
     */
    TestUserService testUserService = new TestUserServiceImpl();
    /**
     * 接口只是定义了一个标准，很多类可以实现这个接口，eg：List接口
     * List list = new ArrayList(); // 实例化的是ArrayList这个类
     * List list = new Vector();     // 实例化的是Vector这个类
     * 其中：ArrayList 和 Vector 这两个类都实现了List接口
     * A  instanceof  B 的作用：
     * 1、 对象A是否为B类的对象：objA  instanceof  B.class
     * 2、A对象是否实现了B接口
     */
    @ApiOperation(value = "测试对象的注入和new", notes="测试对象的注入和new")
    @GetMapping("testResource")
    public String testResource() {
        //TestUserService testUserService = new TestUserServiceImpl();
        System.out.println(testUserService.getUser());
        return testUserService.getUser();
    }




    @PostMapping("getPurchaseMainDtoList")
    public  List<PurchaseMainDto> getPurchaseMainDtoList(@RequestBody InStoreAcceptanceRequest inStoreAcceptanceRequest1) throws Exception{

        String u = JSONObject.toJSONString(inStoreAcceptanceRequest1);
        Map<String,Object> map = JSONObject.parseObject(u, Map.class);

        InStoreAcceptanceRequest inStoreAcceptanceRequest = new InStoreAcceptanceRequest();
        List<ReceivingLineDto> receivingLineDtoList = Lists.newArrayList();
        inStoreAcceptanceRequest.setReceivingNo((String) map.get("receivingNo"));
        inStoreAcceptanceRequest.setContainerNo((String) map.get("containerNo"));
        inStoreAcceptanceRequest.setRealReceivedUsers((String) map.get("realReceivedUsers"));
        inStoreAcceptanceRequest.setBookTips((String) map.get("bookTips"));
        inStoreAcceptanceRequest.setIsAppearance(Integer.valueOf(String.valueOf(map.get("isAppearance"))));
        inStoreAcceptanceRequest.setQtyMatch(Integer.valueOf(String.valueOf( map.get("qtyMatch"))));
        inStoreAcceptanceRequest.setModelMatch(Integer.valueOf(String.valueOf( map.get("modelMatch"))));
        inStoreAcceptanceRequest.setQualityTag(Integer.valueOf(String.valueOf( map.get("qualityTag"))));
        inStoreAcceptanceRequest.setOther((String) map.get("other"));
        inStoreAcceptanceRequest.setPurchasingEligibility(Integer.valueOf(String.valueOf(map.get("purchasingEligibility"))));
        inStoreAcceptanceRequest.setTechnicalFocusEligibility(Integer.valueOf(String.valueOf( map.get("technicalFocusEligibility"))));
        inStoreAcceptanceRequest.setArchivesEligibility(Integer.valueOf(String.valueOf( map.get("archivesEligibility"))));
        inStoreAcceptanceRequest.setIsPass((Integer) map.get("isPass"));
        List<Map<String,Object>> lines = (List<Map<String, Object>>) map.get("receivingLines");
        for (Map<String, Object> line : lines) {
            ReceivingLineDto receivingLineDto = new ReceivingLineDto();
            receivingLineDto.setSellerPurchaseNo((String) line.get("sellerPurchaseNo"));
            receivingLineDto.setFactoryCode((String) line.get("factoryCode"));
            receivingLineDto.setLineNo((String) line.get("lineNo"));
            receivingLineDto.setItemId((String) line.get("itemId"));
            receivingLineDto.setExpectedQty(new BigDecimal(String.valueOf(line.get("expectedQty")) ));
            receivingLineDto.setIsvLotattrs((String) line.get("isvLotattrs"));
            receivingLineDto.setStockLocationCode(String.valueOf(line.get("stockLocationCode")));
            receivingLineDto.setUnitNo(String.valueOf(line.get("unitNo")));
            receivingLineDtoList.add(receivingLineDto);
        }
        inStoreAcceptanceRequest.setReceivingLines(receivingLineDtoList);

        if (null == inStoreAcceptanceRequest || CollectionUtils.isEmpty(inStoreAcceptanceRequest.getReceivingLines())) {
            return null;
        }
        List<PurchaseMainDto> purchaseMainDtoList = Lists.newArrayList();
        Set<String> set = new HashSet<>();
        try {
            List<ReceivingLineDto> receivingLines = inStoreAcceptanceRequest.getReceivingLines();

            for (ReceivingLineDto i : receivingLines) {

                if (!set.contains(i.getSellerPurchaseNo())) {
                    set.add(i.getSellerPurchaseNo());
                    PurchaseMainDto purchaseMainDto = new PurchaseMainDto();
                    purchaseMainDto.setSellerPurchaseNo(i.getSellerPurchaseNo());
                    purchaseMainDto.setBatchNo(inStoreAcceptanceRequest.getReceivingNo());
                    purchaseMainDto.setFactoryCode(i.getFactoryCode());

                    List<PurchaseItemDto> purchaseItemDtoList = Lists.newArrayList();
                    PurchaseItemDto purchaseItemDto = new PurchaseItemDto();
                    purchaseItemDto.setLineNo(i.getLineNo());
                    purchaseItemDto.setSpGoodsNo(i.getItemId());
                    purchaseItemDto.setUnitId(Long.valueOf(i.getUnitNo()));
                    purchaseItemDto.setApplyQty(i.getExpectedQty());
                    purchaseItemDtoList.add(purchaseItemDto);
                    purchaseMainDto.setPurchaseItemDtoList(purchaseItemDtoList);

                    List<AcceptanceDto> acceptanceDtoList = Lists.newArrayList();
                    AcceptanceDto acceptanceDto = new AcceptanceDto();
                    BeanUtils.copyProperties(inStoreAcceptanceRequest, acceptanceDto);

                    List<ReceivingLineDto> dtoList = receivingLines.stream().filter(receivingLineDto -> i.getSellerPurchaseNo().equals(receivingLineDto.getSellerPurchaseNo())).collect(Collectors.toList());
                    List<ReceivingLineDto> newDtoList = Lists.newArrayList();
                    dtoList.forEach(dto ->{
                        ReceivingLineDto receivingLineDto = new ReceivingLineDto();
                        BeanUtils.copyProperties(dto, receivingLineDto);
                        newDtoList.add(receivingLineDto);
                    });
                    acceptanceDto.setReceivingLines(newDtoList);
                    acceptanceDtoList.add(acceptanceDto);

                    purchaseMainDto.setAcceptanceDtos(acceptanceDtoList);

                    purchaseMainDtoList.add(purchaseMainDto);
                } else {
                    PurchaseMainDto mainDto = purchaseMainDtoList.stream().filter(purchaseMainDto -> i.getSellerPurchaseNo().equals(purchaseMainDto.getSellerPurchaseNo())).collect(Collectors.toList()).get(0);
                    PurchaseItemDto purchaseItemDto = new PurchaseItemDto();
                    purchaseItemDto.setLineNo(i.getLineNo());
                    purchaseItemDto.setSpGoodsNo(i.getItemId());
                    purchaseItemDto.setUnitId(Long.valueOf(i.getUnitNo()));
                    purchaseItemDto.setApplyQty(i.getExpectedQty());
                    mainDto.getPurchaseItemDtoList().add(purchaseItemDto);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return purchaseMainDtoList;
    }




    /**
     * 根据合作伙伴获取库房信息
     * 用对象 或者 String userName  都可以接到参数
     */
    @RequestMapping(value = "queryWarehouseByPartnerNo.do")
    public Object queryWarehouseByPartnerNo(User user) throws Exception {

        if (StringUtils.isBlank(user.getUserName())) {
            return null;
        }
        try {
            Warehouse warehouse1 = new Warehouse();
            warehouse1.setWarehouseNo("NO---1");
            warehouse1.setWarehouseName("仓库----1");
            Warehouse warehouse2 = new Warehouse();
            warehouse2.setWarehouseNo("NO---2");
            warehouse2.setWarehouseName("仓库---2");
            Warehouse warehouse3 = new Warehouse();
            warehouse3.setWarehouseNo("NO---3");
            warehouse3.setWarehouseName("仓库---3");
            List<Warehouse> warehouseList = Lists.newArrayList();
            warehouseList.add(warehouse1);
            warehouseList.add(warehouse2);
            warehouseList.add(warehouse3);
            return warehouseList;
        } catch (Exception e) {
            log.error("查询仓库异常", e);
            throw new RuntimeException("查询仓库异常");
        }
    }




}

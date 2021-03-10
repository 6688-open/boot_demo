package com.dj.boot.controller.tenant;


import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.base.Request;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.constant.ExcelConstant;
import com.dj.boot.common.enums.StartOrStop;
import com.dj.boot.common.excel.exc.ExcelUtil;
import com.dj.boot.common.util.collection.CollectionUtils;
import com.dj.boot.controller.tenant.vo.GoodsVo;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.goods.CategoryViewCondition;
import com.dj.boot.pojo.goods.GoodsCategoryDto;
import com.dj.boot.pojo.goods.GoodsCategoryFormDto;
import com.dj.boot.service.goods.GoodsCategoryService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author ext.wangjia
 */
@RestController
@RequestMapping("/goods/")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Resource
    private GoodsCategoryService goodsCategoryService;



    /**
     * 测试
     * @return 返回数据
     */
    @PostMapping("test")
    public Response<GoodsCategoryDto> test(){
        Response<GoodsCategoryDto>  response = Response.success();
        try {
            Request request = new Request();
            request.setData(348l);
            response = goodsCategoryService.getById(request);
            GoodsCategoryDto data = response.getData();

        } catch (Exception e) {
            logger.info(e.getMessage());
            return Response.error(Response.ERROR_BUSINESS, "系统异常");
        }
        return response;
    }

    /**
     * 测试
     * @return 返回数据
     */
    @PostMapping("importGoodsCategory")
    public Response<User> importGoodsCategory(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        Response  response = Response.success();
        try {
            ExcelUtil<GoodsVo> excelUtil = new ExcelUtil<>(GoodsVo.class);
            List<GoodsVo> goodsVosList = excelUtil.importExcel(null, file.getInputStream());
            //数据不规范 命名-   逻辑会对-进行判断
            goodsVosList.forEach(goodsVo -> {
                goodsVo.setGoodsDesc(goodsVo.getGoodsDesc().replace("-", " "));
            });
            //以编码分组
            Map<String, String> map = goodsVosList.stream().collect(Collectors.groupingBy(GoodsVo::getGoods, Collectors.collectingAndThen(Collectors.toList(), value -> value.get(0).getGoodsDesc())));

            List<GoodsVo> voList = goodsVosList.stream().filter(goodsVo -> goodsVo.getGoods().length() >= 6).collect(Collectors.toList());
            List<GoodsVo> voList2 = goodsVosList.stream().filter(goodsVo -> goodsVo.getGoods().length() == 4).collect(Collectors.toList());
            List<GoodsVo> voList1 = goodsVosList.stream().filter(goodsVo -> goodsVo.getGoods().length() == 2).collect(Collectors.toList());
            goodsVosList.removeAll(voList);
            goodsVosList.removeAll(voList2);
            goodsVosList.removeAll(voList1);

            if (CollectionUtils.isNotEmpty(goodsVosList)) {
                response.setCode(300);
                response.setMsg("导入数据有问题 请检查"+ JSONObject.toJSONString(goodsVosList));
                return response;
            }

            List<List<String>> rowList = new ArrayList<>();

            //以名称分组会重复  编码+名称  在去除后缀
            voList.forEach(goodsVo -> {
                List<String> list = new ArrayList<>();
                String goods = goodsVo.getGoods();
                list.add(map.get(goods.substring(0, 2))+"="+goods.substring(0, 2));
                list.add(map.get(goods.substring(0, 4))+"="+goods.substring(0, 4));
                list.add(goodsVo.getGoodsDesc()+"="+goods);
                rowList.add(list);
            });
            Map importRes = checkAndImportExcelData(rowList, request, "SLES003844", "67");
            response.setData(importRes);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return Response.error(Response.ERROR_BUSINESS, "系统异常");
        }
        return response;
    }




    private Map checkAndImportExcelData(List<List<String>> rowList,  HttpServletRequest request, String tenantId, String userPin) {
        int count = rowList.size();
        int failNum = 0;
        int successNum = 0;
        StringBuilder msg = new StringBuilder();
        // 登录用户
        String pin = "wj";

        List<GoodsCategoryFormDto> dtoList = new ArrayList<>();
        Map level1Map = new HashMap();
        Map level2Map = new HashMap();
        Map level3Map = new HashMap();
        Map level4Map = new HashMap();
        Map level5Map = new HashMap();
        List level1List = new ArrayList();
        List level2List = new ArrayList();
        List level3List = new ArrayList();
        List level4List = new ArrayList();
        List level5List = new ArrayList();
        for (int i = 0; i < rowList.size(); i++) {
            List<String> data = rowList.get(i);
            boolean flag = true;
            // 五级分类名称
            String firstCategoryNameParam = data.get(ExcelConstant.FIRST_CATEGORY_NAME);
            String secondCategoryNameParam = data.get(ExcelConstant.SECOND_CATEGORY_NAME);
            String thirdCategoryNameParam = data.get(ExcelConstant.THIRD_CATEGORY_NAME);
            String firstCategoryName = firstCategoryNameParam.split("=")[0];
            String secondCategoryName =secondCategoryNameParam.split("=")[0];
            String thirdCategoryName = thirdCategoryNameParam.split("=")[0];
            String fourthCategoryName = null;//data.get(ExcelConstant.FOURTH_CATEGORY_NAME);
            String fifthCategoryName = null; //data.get(ExcelConstant.FIFTH_CATEGORY_NAME);
            // 一级分类名称空校验
            if (StringUtils.isEmpty(firstCategoryName)) {
                failNum++;
                continue;
            }
            // 一级分类名重复校验
            CategoryViewCondition condition = new CategoryViewCondition();
            condition.setCategoryName(firstCategoryName);
            condition.setLevel((byte) 1);
            Request<CategoryViewCondition> conditionRequest = new Request<>();
            conditionRequest.setData(condition);
            Response<Integer> response = goodsCategoryService.getCount(conditionRequest);
            if (response.getData() > 0) {
                msg.append("一级类目[").append(firstCategoryName).append("]已存在,");
                failNum++;
                continue;
            }
            // Map
            if (StringUtils.isNotEmpty(firstCategoryName)) {// 一级类目
                String key = firstCategoryName;
                if (!level1Map.containsKey(key)) {// 防重
                    level1List.add(this.buildGoodsCategoryFormDto(1, key, firstCategoryNameParam, tenantId, userPin));
                    level1Map.put(key, key);
                }
            }
            if (StringUtils.isNotEmpty(secondCategoryName)) {// 二级类目
                String key = firstCategoryName + "-" + secondCategoryName;
                if (!level2Map.containsKey(key)) {// 防重
                    level2List.add(this.buildGoodsCategoryFormDto(2, key, secondCategoryNameParam, tenantId, userPin));
                    level2Map.put(key, key);
                }
            }
            if (StringUtils.isNotEmpty(thirdCategoryName)) {// 三级类目
                String key = firstCategoryName + "-" + secondCategoryName + "-" + thirdCategoryName;
                if (!level3Map.containsKey(key)) {// 防重
                    level3List.add(this.buildGoodsCategoryFormDto(3, key, thirdCategoryNameParam, tenantId, userPin));
                    level3Map.put(key, key);
                } else {
                    msg.append("导入文件中三级类目[").append(key).append("]有重复数据,");
                }
            }
            if (StringUtils.isNotEmpty(fourthCategoryName)) {// 四级类目
                String key = firstCategoryName + "-" + secondCategoryName + "-" + thirdCategoryName + "-" + fourthCategoryName;
                if (!level4Map.containsKey(key)) {// 防重
                    level4List.add(this.buildGoodsCategoryFormDto(4, key, fourthCategoryName, tenantId, userPin));
                    level4Map.put(key, key);
                }
            }
            if (StringUtils.isNotEmpty(fifthCategoryName)) {// 五级类目
                String key = firstCategoryName + "-" + secondCategoryName + "-" + thirdCategoryName + "-" + fourthCategoryName + "-" + fifthCategoryName;
                if (!level5Map.containsKey(key)) {// 防重
                    level5List.add(this.buildGoodsCategoryFormDto(5, key, fifthCategoryName, tenantId, userPin));
                    level5Map.put(key, key);
                }
            }
            successNum++;
        }
        // Reduce
        dtoList.addAll(level1List);
        dtoList.addAll(level2List);
        dtoList.addAll(level3List);
        dtoList.addAll(level4List);
        dtoList.addAll(level5List);
        Map res = new HashMap();
        if (dtoList.size() > 0) {
            Request<List<GoodsCategoryFormDto>> requestDto = new Request<>();
            requestDto.setData(dtoList);
            Response response = goodsCategoryService.addBatch(requestDto);
            if (response.getCode() == Response.SUCCESS_CODE) {
                successNum = count - failNum;
            } else {
                successNum = 0;
                failNum = count;
            }

        }
        // 全部成功
        StringBuilder resMsg = new StringBuilder();
        if (successNum > 0 && successNum == count) {
            resMsg.append("导入成功."+msg);
        } else if (successNum > 0 && successNum < count) {
            resMsg.append("部分成功.");
            resMsg.append(msg);
        } else if (successNum == 0) {
            resMsg.append("导入失败.");
            resMsg.append(msg);
        }
        res.put("count", count);
        res.put("successNum", successNum);
        res.put("failNum", failNum);
        res.put("msg", resMsg.toString());

        return res;
    }





    private GoodsCategoryFormDto buildGoodsCategoryFormDto(Integer level, String treeCode, String name, String tenantId, String userPin) {
        GoodsCategoryFormDto category = new GoodsCategoryFormDto();
        category.setPTreeCode(treeCode);
        category.setCategoryName(name);
        category.setLevel(level.byteValue());
        category.setCreateUser(userPin);
        category.setUpdateUser(userPin);
        category.setStatus(StartOrStop.Start.getCode());
        category.setTenantId(tenantId);
        return category;
    }


    public static void main(String[] args) {
        String str = "121212";
        String substring = str.substring(0, 2);
        String s = str.substring(0, 4);
        System.out.println(s);
        System.out.println(substring);
    }



}

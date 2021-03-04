package com.dj.boot.test.receipts_perform;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.excel.poi.BatchDto;
import com.dj.boot.common.excel.poi.ReceiptsPerformItemDto;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.test.receipts_perform
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-09-08-15-03
 */
public class ReceiptsPerformTest {


    public static void main(String[] args) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("test", "22");

        map.get("test");

        Integer test = Integer.valueOf((String) map.get("test"));

        BigDecimal bigDecimal = new BigDecimal(2);
        Integer a = 1;
        //batchDto.setBackQty(new BigDecimal((String) stringObjectMap.get("batchQty")));
        //batchDto.setBackQty(new BigDecimal(String.valueOf(stringObjectMap.get("batchQty")) ));
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(a));
        String attrModeList = "[{\"batchNo\":\"3_3_27_EMG4398059782386_20160617_1466134338546\",\"batchOrderLine\":\"120\",\"eclpSoNo\":\"ESLXXXXXXXXXXXX\",\"isvSoNo\":\"153213\",\"deptGoodsNo\":\"EMGXXXXXXXXXXXX\",\"sellerGoodsNo\":\"2146587922356\",\"batAttrList\":[{\"batchValue\":\"2018-12-05\",\"batchKey\":\"expirationDate\"},{\"batchValue\":\"hong\",\"batchKey\":\"clour\"}],\"batchQty\":\"20\",\"goodsLevel\":\"100\"},{\"batchNo\":\"3_3_27_EMG4398059782386_20160617_1466134338547\",\"batchOrderLine\":\"120\",\"eclpSoNo\":\"ESLXXXXXXXXXXXX\",\"isvSoNo\":\"153213\",\"deptGoodsNo\":\"EMGXXXXXXXXXXXX\",\"sellerGoodsNo\":\"2146587922356\",\"batAttrList\":[{\"batchValue\":\"2018-12-06\",\"batchKey\":\"expirationDate\"},{\"batchValue\":\"hong\",\"batchKey\":\"clour\"}],\"batchQty\":\"20\",\"goodsLevel\":\"100\"}]\n";
        attrModeList="[{\"batchNo\":\"3_3_27_EMG4398059782386_20160617_1466134338546\",\"batchOrderLine\":\"120\",\"eclpSoNo\":\"ESLXXXXXXXXXXXX\",\"isvSoNo\":\"153213\",\"deptGoodsNo\":\"EMGXXXXXXXXXXXX\",\"sellerGoodsNo\":\"2146587922356\",\"batAttrList\":null,\"batchQty\":\"20\",\"goodsLevel\":\"100\"}]\n";

        List<Map<String,Object>> rtwBatAttrModelMapList = JSONObject.parseObject(attrModeList,List.class);
        System.out.println(rtwBatAttrModelMapList);
        String itemList="[{\"realQty\":\"10\",\"goodsLevelNo\":\"100\",\"planRtwReasonDesc\":\"120\",\"detailIsvSoNo\":\"153213\",\"planRtwReasonNo\":\"120\",\"planQty\":\"10\",\"realPlanRtwReasonDesc\":\"120\",\"realRtwReasonNo\":\"120\",\"detailEclpSoNo\":\"ESLXXXXXXXXXXXX\",\"goodsNo\":\"EMGXXXXXXXXXXXX\",\"isvGoodsNo\":\"2146587922356\",\"detailOrderLine\":\"120\"}]\n";
        List<Map<String,Object>> rtwDetailsModelMapList = JSONObject.parseObject(itemList,List.class);
        System.out.println(rtwDetailsModelMapList);
        List<ReceiptsPerformItemDto> receiptsPerformItemDtos  =getBatAttrModelList(rtwDetailsModelMapList,rtwBatAttrModelMapList);
        System.out.println(JSONObject.toJSONString(receiptsPerformItemDtos));
    }



    /**
     * 构建带批次信息的入库明细
     * @param
     * @return
     */
    public static List<ReceiptsPerformItemDto> getBatAttrModelList(List<Map<String,Object>> rtwDetailsModelMapList, List<Map<String,Object>> rtwBatAttrModelMapList) {

        List<ReceiptsPerformItemDto> poBatAttrModelList = new ArrayList<>();
        try {
            Map<String, List<Map<String, Object>>> sameBatchRtwBatAttrListMap = null;
            if (!CollectionUtils.isEmpty(rtwBatAttrModelMapList)) {
                sameBatchRtwBatAttrListMap = new HashMap<>();
                Set<String> set = new HashSet<>();
                /*same batch*/
                for (Map<String, Object> rtwBatAttrModel : rtwBatAttrModelMapList) {
                    String batchOrderLine = (String) rtwBatAttrModel.get("batchOrderLine");
                    if (set.contains(batchOrderLine)) {
                        sameBatchRtwBatAttrListMap.get(batchOrderLine).add(rtwBatAttrModel);
                    } else {

                        List<Map<String, Object>> batchRtwBatAttrNewList = new ArrayList<>();
                        batchRtwBatAttrNewList.add(rtwBatAttrModel);
                        sameBatchRtwBatAttrListMap.put(batchOrderLine, batchRtwBatAttrNewList);
                        set.add(batchOrderLine);
                    }
                }
            }


            /*buil receiptsPerformItemDto*/
            for (Map<String, Object> rtwDetailsModelMap : rtwDetailsModelMapList) {
                ReceiptsPerformItemDto receiptsPerformItemDto = new ReceiptsPerformItemDto();
                receiptsPerformItemDto.setSpGoodsNo((String) rtwDetailsModelMap.get("isvGoodsNo"));
                receiptsPerformItemDto.setLineNo((String) rtwDetailsModelMap.get("detailOrderLine"));
                receiptsPerformItemDto.setGoodsLevel((String) rtwDetailsModelMap.get("goodsLevelNo"));
                receiptsPerformItemDto.setRealBackQty(new BigDecimal(String.valueOf(rtwDetailsModelMap.get("realQty"))));
                if (sameBatchRtwBatAttrListMap != null) {
                    List<Map<String, Object>> batchRtwBatAttrNewList = sameBatchRtwBatAttrListMap.get((String) rtwDetailsModelMap.get("detailOrderLine"));

                    List<BatchDto> batchDtoList = new ArrayList<>();

                    for (Map<String, Object> stringObjectMap : batchRtwBatAttrNewList) {

                        BatchDto batchDto = new BatchDto();
                        batchDto.setGoodsLevel((String) stringObjectMap.get("goodsLevel"));
                        batchDto.setBackQty(new BigDecimal(String.valueOf(stringObjectMap.get("batchQty")) ));
                        batchDto.setBatchNo((String) stringObjectMap.get("batchNo"));
                        List<Map<String, Object>> batAttrListMap = (List<Map<String, Object>>) stringObjectMap.get("batAttrList");

                        if (!CollectionUtils.isEmpty(batAttrListMap)) {
                            StringBuilder lotAttrStr = null;
                            for (Map<String, Object> batAttr : batAttrListMap) {
                                //subWarehouse=20120901;stockPlace=20230103;subWarehouse=20120901;stockPlace=20230103
                                String batchKey = (String) batAttr.get("batchKey");
                                String batchValue = (String) batAttr.get("batchValue");
                                if (batchKey.equals("productionDate")) {
                                    batchKey = "madedate";
                                } else if (batchKey.equals("expirationDate")) {
                                    batchKey = "expiredate";
                                } else if (batchKey.equals("packageBatchNo")) {
                                    batchKey = "batchNo";
                                }
                                String batchStr = batchKey + "=" + batchValue;
                                if (lotAttrStr == null) {
                                    lotAttrStr = new StringBuilder();
                                    lotAttrStr.append(batchStr);
                                } else {
                                    lotAttrStr.append(";" + batchStr);
                                }
                            }
                            batchDto.setLotAttrStr(lotAttrStr != null ? lotAttrStr.toString() : null);
                        }

                        batchDtoList.add(batchDto);

                    }
                    receiptsPerformItemDto.setBatchDtoList(batchDtoList);
                }
                poBatAttrModelList.add(receiptsPerformItemDto);
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            //return null;

        }

        return poBatAttrModelList;


    }



    /**
     * 构建带批次信息的入库明细
     * @param
     * @return
     */
    public static List<ReceiptsPerformItemDto> getReceiptsPerformItemDtoList(List<Map<String,Object>> poItemModelList,List<Map<String,Object>> poBatAttrModelList) {

        List<ReceiptsPerformItemDto> receiptsPerformItemDtos = new ArrayList<>();
        try {
            Map<String, List<Map<String, Object>>> sameBatchPoBatAttrListMap = null;
            if (!CollectionUtils.isEmpty(poBatAttrModelList)) {
                sameBatchPoBatAttrListMap = new HashMap<>();
                Set<String> set = new HashSet<>();
                /*same orderLineNo  batch*/
                for (Map<String, Object> poBatAttrModel : poBatAttrModelList) {
                    String batchOrderLine = (String) poBatAttrModel.get("orderLineNo");
                    if (set.contains(batchOrderLine)) {
                        sameBatchPoBatAttrListMap.get(batchOrderLine).add(poBatAttrModel);
                    } else {

                        List<Map<String, Object>> batchPowBatAttrNewList = new ArrayList<>();
                        batchPowBatAttrNewList.add(poBatAttrModel);
                        sameBatchPoBatAttrListMap.put(batchOrderLine, batchPowBatAttrNewList);
                        set.add(batchOrderLine);
                    }
                }
            }

            /*buil receiptsPerformItemDto*/
            for (Map<String, Object> poItemMode : poItemModelList) {
                ReceiptsPerformItemDto receiptsPerformItemDto = new ReceiptsPerformItemDto();
                receiptsPerformItemDto.setLineNo((String) poItemMode.get("orderLine"));
                receiptsPerformItemDto.setGoodsLevel((String) poItemMode.get("realGoodsLevel"));
                receiptsPerformItemDto.setRealBackQty(new BigDecimal(String.valueOf( poItemMode.get("realInstoreQty"))));
                if (sameBatchPoBatAttrListMap != null) {
                    List<Map<String, Object>> batchPoBatAttrNewList = sameBatchPoBatAttrListMap.get((String) poItemMode.get("orderLine"));

                    List<BatchDto> batchDtoList = new ArrayList<>();

                    for (Map<String, Object> stringObjectMap : batchPoBatAttrNewList) {

                        BatchDto batchDto = new BatchDto();
                        batchDto.setGoodsLevel((String) stringObjectMap.get("goodsLevel"));
                        batchDto.setBackQty(new BigDecimal(String.valueOf(stringObjectMap.get("batchQty")) ));
                        batchDto.setBatchNo((String) stringObjectMap.get("batchNo"));
                        List<Map<String, Object>> batAttrListMap = (List<Map<String, Object>>) stringObjectMap.get("batAttrList");

                        if (!CollectionUtils.isEmpty(batAttrListMap)) {
                            StringBuilder lotAttrStr = null;
                            for (Map<String, Object> batAttr : batAttrListMap) {
                                //subWarehouse=20120901;stockPlace=20230103;subWarehouse=20120901;stockPlace=20230103
                                String batchKey = (String) batAttr.get("batchKey");
                                String batchValue = (String) batAttr.get("batchValue");
                                if (Objects.equals(batchKey, "productionDate")) {
                                    batchKey = "madedate";
                                } else if (Objects.equals(batchKey, "expirationDate")) {
                                    batchKey = "expiredate";
                                } else if (Objects.equals(batchKey, "packageBatchNo")) {
                                    batchKey = "batchNo";
                                }
                                String batchStr = batchKey + "=" + batchValue;
                                if (lotAttrStr == null) {
                                    lotAttrStr = new StringBuilder();
                                    lotAttrStr.append(batchStr);
                                } else {
                                    lotAttrStr.append(";" + batchStr);
                                }
                            }
                            batchDto.setLotAttrStr(lotAttrStr != null ? lotAttrStr.toString() : null);
                        }

                        batchDtoList.add(batchDto);

                    }
                    receiptsPerformItemDto.setBatchDtoList(batchDtoList);
                }
                receiptsPerformItemDtos.add(receiptsPerformItemDto);
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return receiptsPerformItemDtos;
    }
}

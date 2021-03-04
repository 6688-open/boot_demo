package com.dj.boot.service.goods;

import com.dj.boot.btp.common.util.idUtil.PkTable;
import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Request;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.enums.StartOrStop;
import com.dj.boot.common.util.ValidatorUtil;
import com.dj.boot.mapper.goods.GoodsCategoryDao;
import com.dj.boot.pojo.goods.*;
import com.dj.boot.service.goods.manager.GoodsCategoryManager;
import com.dj.boot.service.pk.SequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 对外服务
 *
 * @author wj
 * @Date 2019-7-30 18:3:20
 */
@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsCategoryServiceImpl.class);



    @Autowired
    private SequenceService sequenceService;
    @Resource
    private GoodsCategoryDao goodsCategoryDao;
    @Resource
    private GoodsCategoryManager goodsCategoryManager;
    /**
     * 测试服务可用性的方法
     *
     * @param message
     * @return
     */
    @Override
    public String echo(String message) {
        logger.info("GoodsCategoryService -> test info {}", message);
        return message;
    }

    @Override
    public Response queryViewList(Request<CategoryViewCondition> param) {
        Response response = new Response();
        CategoryViewCondition condition = param.getData();
        if (condition == null ||
                (StringUtils.isEmpty(condition.getCategoryName()) && StringUtils.isEmpty(condition.getCategoryNo())
                )
        ) {//没有名称的查询条件时候

            List<GoodsCategoryDto> dtoList = goodsCategoryManager.queryViewList(condition);
            response.setData(buildTree(dtoList));

        } else {
            List<GoodsCategoryDto> dtoList = goodsCategoryManager.queryViewList(condition);
            if (!CollectionUtils.isEmpty(dtoList)) {
                response.setData(queryHaveChild(condition.getTenantId(), dtoList));
            }
        }
        return response;
    }

    @Override
    public Response queryChildList(Request<ParentCategoryCondition> param) {
        Response response = new Response();
        response.setData(goodsCategoryManager.queryChildList(param.getData()));
        return response;
    }

    private List<CategoryViewDto> queryHaveChild(String tenantId, List<GoodsCategoryDto> dtoList) {
        Map<String, Set<String>> ppTreeCodeMap = new HashMap<String, Set<String>>();
        Set<String> allNodeSet = new HashSet<>();//所有节点的key-set
        Map<String, GoodsCategoryDto> ppDtoMap = new HashMap<String, GoodsCategoryDto>();//顶级节点map，处理只有顶级的情况
        List<GoodsCategoryDto> realList = new ArrayList<GoodsCategoryDto>();
        for (GoodsCategoryDto dto : dtoList) {
            String myTreeCode = dto.getTreeCode();
            if (myTreeCode.contains("-")) {
                String[] array = myTreeCode.split("-");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < array.length; i++) {
                    if (i == 0) {
                        sb.append(array[0]);
                    } else {
                        sb.append("-").append(array[i]);
                    }
                    Set<String> set = ppTreeCodeMap.computeIfAbsent(array[0], k -> new HashSet<String>());
                    set.add(sb.toString());
                }
                allNodeSet.addAll(ppTreeCodeMap.get(array[0]));
            } else {
                ppDtoMap.put(myTreeCode, dto);
            }
        }
        //从解析出的所以节点Set中检查没有出现的顶级数据
        for (Map.Entry<String, GoodsCategoryDto> entry : ppDtoMap.entrySet()) {
            if (!allNodeSet.contains(entry.getKey())) {
                realList.add(entry.getValue());
            }
        }

        for (Map.Entry<String, Set<String>> entry : ppTreeCodeMap.entrySet()) {
            PPCategoryCondition categoryCondition = new PPCategoryCondition();
            categoryCondition.setTenantId(tenantId);
            categoryCondition.setPpTreeCode(entry.getKey());
            List<GoodsCategoryDto> allPChildren = goodsCategoryManager.queryPPList(categoryCondition);
            if (!CollectionUtils.isEmpty(allPChildren)) {
                for (GoodsCategoryDto dto : allPChildren) {
                    if (entry.getValue().contains(dto.getTreeCode())) {
                        realList.add(dto);
                    }
                }
            }
        }
        return buildTree(realList);
    }

    private List<CategoryViewDto> buildTree(List<GoodsCategoryDto> dtoList) {
        List<CategoryViewDto> ppList = new ArrayList<CategoryViewDto>();
        Map<String, CategoryViewDto> allTreeMap = new HashMap<String, CategoryViewDto>(dtoList.size());
        Map<String, String> childTreeMap = new HashMap<String, String>();
        for (GoodsCategoryDto dto : dtoList) {
            String myTreeCode = dto.getTreeCode();
            CategoryViewDto viewDto = new CategoryViewDto();
            BeanUtils.copyProperties(dto, viewDto);
            allTreeMap.put(dto.getTreeCode(), viewDto);
            if (dto.getLevel() > 1) {
                String pT = myTreeCode.substring(0, myTreeCode.lastIndexOf("-"));
                childTreeMap.put(myTreeCode, pT);
            } else {
                childTreeMap.put(myTreeCode, null);
                ppList.add(viewDto);
            }
        }
        //变量所有子节点，把他们add到父的Children中
        for (Map.Entry<String, String> entry : childTreeMap.entrySet()) {
            CategoryViewDto child = allTreeMap.get(entry.getKey());
            if (child != null) {
                CategoryViewDto parent = allTreeMap.get(entry.getValue());
                if (parent != null) {
                    List<CategoryViewDto> children = parent.getChildren();
                    if (children == null) {
                        children = new ArrayList<CategoryViewDto>();
                        parent.setChildren(children);
                    }
                    children.add(child);
                }
            }
        }
        return ppList;
    }

    @Override
    public Response add(Request<GoodsCategoryFormDto> param) {
        Response response = new Response();
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtils.copyProperties(param.getData(), goodsCategory);
        if (goodsCategoryManager.checkDuplicate(goodsCategory)) {
            response.setCode(BaseResponse.ERROR_BUSINESS);
            response.setMsg("分类外部编码或名称重复！");
        } else {
            int id = goodsCategoryManager.insertGoodsCategory(goodsCategory);
            GoodsCategoryDto goodsCategoryDto = goodsCategoryManager.getGoodsCategoryById((long)id);
           /* Response transportResponse = transportGoodsCategory(goodsCategoryDto);
            if(!transportResponse.isSuccess()){
                return transportResponse;
            }*/
        }
        return response;
    }

    @Override
    public Response update(Request<GoodsCategoryFormDto> param) {
        Response response = new Response();
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtils.copyProperties(param.getData(), goodsCategory);
        if (goodsCategoryManager.checkDuplicate(goodsCategory)) {
            response.setCode(BaseResponse.ERROR_BUSINESS);
            response.setMsg("分类外部编码或名称重复！");
        } else {
            goodsCategoryManager.updateGoodsCategory(goodsCategory);
        }
        return response;
    }

    @Override
    public Response updateStartOrStop(Request<GoodsCategoryFormDto> param) {
        Response response = new Response();
        GoodsCategoryFormDto dto = param.getData();
        //如果是停用进行校验末级分类是否被商品引用
//        if (dto.getStatus() == StartOrStop.Stop.getCode().byteValue()) {
//            PPCategoryCondition pCondition = new PPCategoryCondition();
//            pCondition.setTenantId(dto.getTenantId());
//            pCondition.setPpTreeCode(dto.getPTreeCode());
//            List<GoodsCategoryDto> childList = goodsCategoryManager.queryPPList(pCondition);
//            Set<String> categoryTreeCodeSet = new HashSet<String>();
//            Set<String> categoryNoSet = new HashSet<String>();
//            if (!CollectionUtils.isEmpty(childList)) {
//                for (int i = childList.size() - 1; i >= 0; i--) {
//                    String trCode = childList.get(i).getTreeCode();
//                    String id = childList.get(i).getId() + "";
//                    if (!checkStartWith(categoryTreeCodeSet, trCode)) {
//                        categoryTreeCodeSet.add(trCode);
//                        categoryNoSet.add(id);
//                    }
//                }
//                CategoryRefCondition refCondition = new CategoryRefCondition();
//                refCondition.setCategoryNoSet(categoryNoSet);
//                refCondition.setTenantId(dto.getTenantId());
//                Response<Boolean> checkResult = goodsService.checkCategoryIsRef(refCondition);
//                if (checkResult.isSuccess() && checkResult.getData()) {
//                    response.setCode(BaseResponse.ERROR_BUSINESS);
//                    response.setMsg("您要停用的分类已经被商品引用！不能停用！");
//                    return response;
//                }
//            }
//        }else if(dto.getStatus() == StartOrStop.Start.getCode().byteValue()){
//            GoodsCategoryDto goodsCategoryDto = goodsCategoryManager.getGoodsCategoryById(dto.getId());
//            Response transportResponse = transportGoodsCategory(goodsCategoryDto);
//            if(!transportResponse.isSuccess()){
//                return transportResponse;
//            }
//        }
        goodsCategoryManager.updateStartOrStop(param.getData());
        return new Response();
    }

    private boolean checkStartWith(Set<String> categoryNoSet, String categoryNo) {
        for (String no : categoryNoSet) {
            if (no.startsWith(categoryNo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Response<GoodsCategoryDto> getById(Request<Long> param) {
        Response<GoodsCategoryDto> response = new Response<GoodsCategoryDto>();
        GoodsCategoryDto dto = goodsCategoryManager.getGoodsCategoryById(param.getData());
        response.setData(dto);
        return response;
    }

    @Override
    public Response delete(Request<Long> param) {
        Response response = new Response();
        goodsCategoryManager.deleteGoodsCategory(param.getData());
        return response;
    }

    @Override
    public Response addBatch(Request<List<GoodsCategoryFormDto>> param) {
        List<GoodsCategoryFormDto> dtoList = param.getData();
        List<GoodsCategory> list = new ArrayList();
        for (GoodsCategoryFormDto dto : dtoList) {
            GoodsCategory goodsCategory = new GoodsCategory();
            BeanUtils.copyProperties(dto, goodsCategory);
            list.add(goodsCategory);
        }
        Response response = new Response();
        goodsCategoryManager.insertBatchGoodsCategory(list);
        return response;
    }

    @Override
    public Response getCount(Request<CategoryViewCondition> param) {
        Response response = new Response();
        int count = goodsCategoryManager.getCount(param.getData());
        response.setData(count);
        return response;
    }

//    private Response transportGoodsCategory(GoodsCategoryDto goodsCategoryDto){
//        goodsCategoryDto.setStatus(StartOrStop.Start.getCode());
//        GoodsCategoryDownLoadDto goodsCategoryDownLoadDto = new GoodsCategoryDownLoadDto();
//        goodsCategoryDownLoadDto.setGoodsCategory(goodsCategoryDto);
//        goodsCategoryDownLoadDto.setSync(false);
//        Response response = masterInfoDownLoadUtil.downLoadGoodsCategory(goodsCategoryDownLoadDto);
//        return response;
//    }

    /**
     * 商品分类添加
     */
    @Override
    public Response<String> addCateCascadeAddDto(CategoryAddDto categoryAddDto) {
        ValidatorUtil.Result result = ValidatorUtil.validate(categoryAddDto);
        if (!result.isPass()) {   //如果校验未通过
            StringBuilder errors = new StringBuilder("参数校验未通过(");
            errors.append(org.apache.commons.lang3.StringUtils.join(result.getErrMsgList(), ";"));
            errors.append(")");
            return Response.error(BaseResponse.ERROR_PARAM, errors.toString());
        }
        Response response = Response.success();
        //商品外部编码和名称传值判重
        List<CateAddParamDto> paramDtoList = categoryAddDto.getCateAddParamDtoList();
        Set<String> outCateNoList = new HashSet<>(paramDtoList.stream().map(param -> param.getOutCategoryNo().trim()).collect(Collectors.toList()));
        if (outCateNoList.size() != paramDtoList.size()) {
            return Response.error(BaseResponse.ERROR_PARAM, "传入外部编码存在重复");
        }
        Set<String> cateNameList = new HashSet<>(paramDtoList.stream().map(param -> param.getCategoryName()).collect(Collectors.toList()));
        if (cateNameList.size() != paramDtoList.size()) {
            return Response.error(BaseResponse.ERROR_PARAM, "传入名称存在重复");
        }
        //分类按照从末级开始逐级匹配 匹配上则停止
        Collections.sort(paramDtoList);
        Integer levelCount = paramDtoList.size();
        List<GoodsCategory> addGoodsCateList = new ArrayList<>();
        Integer currLevel =  levelCount;
        for (int i = 0; i < levelCount; i++) {
            CateAddParamDto paramDto = paramDtoList.get(i);
            CategoryViewCondition condition = new CategoryViewCondition();
            condition.setTenantId(categoryAddDto.getTenantId());
            condition.setOutCategoryNo(paramDto.getOutCategoryNo());
            List<GoodsCategoryDto> dbCateList = goodsCategoryManager.queryViewList(condition);
            if (CollectionUtils.isEmpty(dbCateList)) {
                long id = sequenceService.insertAndGetSequence("dj_user");
                GoodsCategory goodsCategory = new GoodsCategory();
                goodsCategory.setTenantId(categoryAddDto.getTenantId());
                goodsCategory.setOutCategoryNo(paramDto.getOutCategoryNo());
                goodsCategory.setCategoryName(paramDto.getCategoryName());
                goodsCategory.setLevel(currLevel.byteValue());
                goodsCategory.setCategoryNo(String.valueOf(id));
                goodsCategory.setId(id);
                goodsCategory.setStatus(StartOrStop.Start.getCode());
                goodsCategory.setCreateUser(categoryAddDto.getCreateUser());
                goodsCategory.setUpdateUser(categoryAddDto.getCreateUser());
                addGoodsCateList.add(goodsCategory);
                if (currLevel == paramDtoList.size()) {
                    response.setData(goodsCategory.getCategoryNo());
                }
            } else {
                GoodsCategoryDto dbCate = dbCateList.get(0);
                if (currLevel == paramDtoList.size()) {
                    response.setData(dbCate.getCategoryNo());
                    return response;
                } else {
                    GoodsCategory category = addGoodsCateList.get(addGoodsCateList.size() - 1);
                    category.setPid(dbCate.getId());
                    category.setTreeCode(dbCate.getTreeCode() + "-" + category.getId());
                    break;
                }
            }
            currLevel--;
        }
        if (!CollectionUtils.isEmpty(addGoodsCateList)) {
            Collections.reverse(addGoodsCateList);
            //如果分类全为新增 则第一级父级为0
            if (levelCount == addGoodsCateList.size()) {
                addGoodsCateList.get(0).setPid(0L);
                addGoodsCateList.get(0).setTreeCode(addGoodsCateList.get(0).getId().toString());
            }
            for (int i = 1; i < addGoodsCateList.size(); i++) {
                GoodsCategory category = addGoodsCateList.get(i);
                category.setPid(addGoodsCateList.get(i - 1).getId());
                category.setTreeCode(addGoodsCateList.get(i - 1).getTreeCode() + "-" + category.getId());
            }
            goodsCategoryDao.batchInsertGoodsCategory(addGoodsCateList);
            addGoodsCateList.forEach(category->{
                GoodsCategoryDto goodsCategoryDto = goodsCategoryManager.getGoodsCategoryById(category.getId());
                //暂时对结果不处理
                /*Response transportResponse = transportGoodsCategory(goodsCategoryDto);
                if(!transportResponse.isSuccess()){
                    logger.error("{}商品分类下发失败:{}",goodsCategoryDto.getCategoryNo(),transportResponse.getMsg());
                }*/
            });
        }
        return response;
    }
}

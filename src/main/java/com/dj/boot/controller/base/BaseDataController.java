package com.dj.boot.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.base.ResultModel;
import com.dj.boot.controller.UserController;
import com.dj.boot.pojo.base.BaseData;
import com.dj.boot.service.base.BaseDataService;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author ext.wangjia
 */
@RestController
@RequestMapping("/baseData/")
public class BaseDataController {


    private static final Logger logger = LoggerFactory.getLogger(BaseDataController.class);

    @Autowired
    private BaseDataService baseDataService;

    @PostMapping("updateName")
    public Response updateName(String name, Integer id){
        Map<String, Object> map = new HashMap<>();
        Response response = Response.success();
        try {
            UpdateWrapper<BaseData> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("name", name);
            boolean i = baseDataService.update(updateWrapper);
            if (!i) {
                response.setCode(BaseResponse.ERROR_PARAM);
                response.setMsg("修改失败");
                return response;
            }
        } catch (Exception e) {
            logger.error("出现异常", e);
            response.setMsg("出现异常");
        }
        return response;
    }








    //异步展示
    @PostMapping("ztreeShow")
    public List<BaseData> ztreeShow() {
        try {
            List<BaseData> list = baseDataService.list();

            list.forEach(baseData ->{
                QueryWrapper<BaseData> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("parent_id", baseData.getId());
                List<BaseData> list1 = baseDataService.list(queryWrapper1);
                if(null != list1 && list1.size()>0) {
                    baseData.setParent(true);
                }
            });
            return list;
        } catch (Exception e) {
            logger.error("服务器异常{}", e.getMessage());
            return null;//提示
        }

    }




    //异步展示
    @PostMapping("ztreeDifferShow")
    public List<BaseData> ztreeDifferShow(Integer id) {
        try {
            QueryWrapper<BaseData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", id = id == null ? 0 :id);
            List<BaseData> list = baseDataService.list(queryWrapper);

            list.forEach(baseData ->{
                QueryWrapper<BaseData> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("parent_id", baseData.getId());
                List<BaseData> list1 = baseDataService.list(queryWrapper1);
                if(null != list1 && list1.size()>0) {
                    baseData.setParent(true);
                }
            });
            return list;
        } catch (Exception e) {
            logger.error("服务器异常{}", e.getMessage());
            return null;//提示
        }

    }




    //异步删除   后台递归
    @PostMapping("delete")
    public Response delete(Integer id) {
        Response<Object> response = Response.success();
        try {
            List<Integer> ids = new ArrayList<>();//声明集合
            ids.add(id);
            QueryWrapper<BaseData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", id);
            List<BaseData> list = baseDataService.list(queryWrapper);
            getChild(list, ids);
            boolean b = baseDataService.removeByIds(ids);
            if(!b) {
                response.setCode(BaseResponse.ERROR_PARAM);
                response.setMsg("删除失败");
                return response;
            }
        } catch (Exception e) {
            logger.error("服务器异常{}", e.getMessage());
            response.setMsg("服务器异常");
            return response;//提示
        }
        return response;
    }


    private void getChild(List<BaseData> list, List<Integer> ids) {
        list.forEach(baseData ->{
            ids.add(baseData.getId());
            QueryWrapper<BaseData> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("parent_id", baseData.getId());
            List<BaseData> list1 = baseDataService.list(queryWrapper1);
            if(null != list1 && list1.size()>0) {
                getChild(list1, ids);
            }
        });
    }





    //异步删除   前台递归
//    @PostMapping("update")
//		public ResultModel<Object> update(Integer[] ids) {
//			try {
//				List<Integer> list = new ArrayList<>();//声明集合
//				//foreach遍历
//				for (Integer in : ids) {
//					list.add(in);
//				}
//				UpdateWrapper<BaseData> uWrapper = new UpdateWrapper<>();//修改条件封装
//				uWrapper.in("id", list);//条件差
//				//uWrapper.set("is_delete", SystemConstant.NUMBER_ONE);//改
//				//boolean update = baseDataService.update(uWrapper);//执行语句
//				boolean remove = baseDataService.remove(uWrapper);
//				if(remove == true)//判断是否正确
//					return new ResultModel<Object>().success("删除成功");//页面提示
//				return new ResultModel<Object>().error("删除失败");//页面提示
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new  ResultModel<>().error("服务器异常" + e.getMessage());//提示
//			}
//		}









}

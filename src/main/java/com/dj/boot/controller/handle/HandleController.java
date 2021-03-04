package com.dj.boot.controller.handle;

import com.dj.boot.common.base.Request;
import com.dj.boot.common.enums.HandleTypeEnums;
import com.dj.boot.common.excel.exc.ExcelUtil;
import com.dj.boot.handeltest.aware.MyBeanFactoryAware;
import com.dj.boot.service.handle.QueryService;
import com.dj.boot.service.user.UserService;
import io.swagger.annotations.Api;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@Api(value = "handle操作接口")
@RestController
@RequestMapping("/handle/")
public class HandleController {

    @Resource
    private QueryService queryService;

    /**
     * 枚举维护bean 实例名称 通过operateType 获取不同的实现类
     * @return
     */
    @GetMapping("handleTest")
    public void handleTest(String operateType) {
        Request<T> request = new Request<>();
        //request.setOperateType(HandleTypeEnums.USER_PAGE_QUERY.getType());
        request.setOperateType(operateType);
        queryService.queryStock(request);

    }



    /**
     * 根据传的type 找到实现类的注解 获取指定的实现类
     * 通过实现类上的注解   获取不同的实现类
     * @return
     */
    @GetMapping("handlerType")
    public List<String> handlerType(String handlerType) {
        List<String> list = queryService.handlerType(handlerType);
        return list;
    }




    @GetMapping("test")
    public Long test() {
        UserService userService = (UserService) MyBeanFactoryAware.getBean("userServiceImpl");
        Long count = userService.getCount();
        return count;
    }

}

package com.dj.boot.controller.enumtest;

import com.dj.boot.common.base.ResultModel;
import com.dj.boot.common.enums.Option;
import com.dj.boot.common.enums.base.SelectTypeEnum;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "enum 根据类型 返回指定下拉框 操作接口")
@RestController
@RequestMapping("/enum/")
public class EnumController {

    /**
     * 如果为空 则去 查询  然后缓存下来  下次直接获取
     * 不为空 从缓存获取
     */
    private static Map<String, List<Option>> selectTypeMap;

    static {
        selectTypeMap = new HashMap<>();
    }

    @PostMapping("queryEnum")
    public ResultModel queryEnum(@Valid  @RequestBody EnumQueryParam enumQueryParam){
        List<Option> optionList = doGetEnumOption(enumQueryParam.getEnumType());
        return new ResultModel<>().success(200, "success", optionList);
    }


    private List<Option> doGetEnumOption(String dictType) {
        SelectTypeEnum type = SelectTypeEnum.getByCode(dictType);
        List<Option> optionList = null;
        if (type != null) {
            if (selectTypeMap.get(dictType) == null) {
                optionList = type.getOptionList(type.getOptionEnum());
                if (org.springframework.util.CollectionUtils.isEmpty(optionList)) {
                    selectTypeMap.put(dictType, optionList);
                }
            } else {
                optionList = selectTypeMap.get(dictType);
            }
        }
        return optionList;
    }

}

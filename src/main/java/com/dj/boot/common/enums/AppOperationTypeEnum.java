package com.dj.boot.common.enums;

import com.dj.boot.common.base.Response;
import com.dj.boot.pojo.User;
import com.dj.boot.service.test.impl.TestUserServiceImpl;
import com.dj.boot.service.user.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * app接口queryByOpId方法通过OPERATION_TYPE_METHOD_MAP获取执行方法
 * @author wj
 * @date 20181219
 */
public enum AppOperationTypeEnum {

    /**
     * app接口queryByOpId方法opId枚举
     */
    STORE_AND_SO_MONITOR(103, "库存及订单主要数据", "queryMainOpenData"),
    ;

    /**
     * 日志对象
     */
    private static final Logger log = LogManager.getLogger(AppOperationTypeEnum.class);
    /**
     * 方法类型（opId值）
     */
    private int type;
    /**
     * 中文名称
     */
    private String name;
    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 操作类型-枚举 映射
     */
    private static final Map<Integer, AppOperationTypeEnum> OPERATION_TYPE_ENUM_MAP = new HashMap<Integer, AppOperationTypeEnum>();
    /**
     * 操作类型-方法 映射
     */
    private static final Map<Integer, Method> OPERATION_TYPE_METHOD_MAP = new HashMap<Integer, Method>();

    static{
        for(AppOperationTypeEnum e : EnumSet.allOf(AppOperationTypeEnum.class)){
            OPERATION_TYPE_ENUM_MAP.put(e.getType(), e);
            try {
                Method method = TestUserServiceImpl.class.getDeclaredMethod(e.getMethodName(), User.class, Response.class);
                method.setAccessible(true);
                OPERATION_TYPE_METHOD_MAP.put(e.getType(), method);
            } catch (NoSuchMethodException e1) {
                log.error("[AppOperationTypeEnum] 初始化方法map时发生异常", e1);
            }
        }
    }

    /**
     * 构造函数
     * @param type opId
     * @param name 中文名称
     * @param methodName 方法名称
     */
    AppOperationTypeEnum(int type, String name, String methodName) {
        this.type = type;
        this.name = name;
        this.methodName = methodName;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getMethodName(){
        return methodName;
    }

    /**
     * 通过opId值获取方法
     * @param type opId
     * @return 对应的方法
     */
    public static Method getMethodByType(int type){
        return OPERATION_TYPE_METHOD_MAP.get(type);
    }

    /**
     * 通过opId值获取枚举
     * @param type opId
     * @return 对应的方法
     */
    public static AppOperationTypeEnum getEnumByType(int type){
        return OPERATION_TYPE_ENUM_MAP.get(type);
    }

}

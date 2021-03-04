package com.dj.boot.aspect;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.annotation.ConcurrentLock;
import com.dj.boot.annotation.Login;
import com.dj.boot.aspect.holder.LoginContext;
import com.dj.boot.aspect.holder.LoginUserContextHodler;
import com.dj.boot.aspect.holder.PermissionContext;
import com.dj.boot.aspect.holder.UserAndPermissionContext;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.util.json.JsonUtils;
import com.dj.boot.common.util.ProceedingJoinPointUtils;
import com.dj.boot.controller.TestController;
import com.dj.boot.pojo.User;
import com.dj.boot.pojo.UserDto;
import com.dj.boot.service.user.UserService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName TestAspect
 * @Description TODO
 * @Author wj
 * @Date 2019/11/20 13:51
 * @Version 1.0
 **/
@Aspect
@Order(3)
@Component
public class LoginAspect {

    private final ExpressionParser parser = new SpelExpressionParser();

    @Autowired
    private UserService userService;

    private Set<String> whiteParamList; // 白名单参数，key为此列表的不记录日志

    private final static Logger log = LogManager.getLogger(LoginAspect.class);

    @Around(value = "com.dj.boot.aspect.Pointcuts.pointcutForActions()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        //testConcurrentLock(joinPoint);

        //获取方法上的参数
        //paramTest(joinPoint);
        //切面拦截  获取用户的权限信息  用户信息
        setUserAndPermission();
        System.out.println("aspect   test   start................................................");
        //判断方法上如果没有注解 @Login  放行
        if (!ProceedingJoinPointUtils.isAnnotation(joinPoint, Login.class)) {
            return joinPoint.proceed();
        }




        //获取 TestController 方法上的注解   。。。。。。  类上 属性上
        //得到所有方法
        Method[] methods = TestController.class.getDeclaredMethods();
        if(methods != null){
            for(Method method : methods){
                //遍历某个方法上的get 某个指定的注解
                Login annotation = method.getAnnotation(Login.class);
                if(annotation == null) {
                    continue;
                }
                //注解的上的值  注解值  进而进行逻辑处理
                int i = annotation.value();
                System.out.println(i);
            }
        }

        String name = joinPoint.getSignature().getName();
        //获取方法上的参数
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println("参数：" + arg);
        }


        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        HttpServletRequest request = servletRequestAttributes.getRequest();
        String paramString = getParamString(request.getParameterMap());
        log.error("paramString:{}", paramString);


        User user = userService.getById(1);

        if (user != null && user.getId() == 1) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=utf-8");
            try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
                Response result = new Response();
                result.setMsg("id 1  的数据存在");
                result.setData("200");
                String body = JsonUtils.serialize(result);
                byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
                response.setContentLength(bytes.length);
                servletOutputStream.write(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        log.info("控制层响应数据拦截:{}", JSONObject.toJSONString(joinPoint.proceed()));
        return joinPoint.proceed();
    }

    private void testConcurrentLock(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        //获取执行方法
        Method method = signature.getMethod();
        // 获取注解信息
        ConcurrentLock annotation = method.getAnnotation(ConcurrentLock.class);

        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            System.out.println(parameterNames[i]);
            System.out.println(args[i]);
            context.setVariable(parameterNames[i], args[i]);
        }
        String redisKey = "";
        if (StringUtils.isNotBlank(annotation.prefix())) {
            redisKey += annotation.prefix() + annotation.connector();
        }
        String bizNo = parser.parseExpression(annotation.bizNo()).getValue(context, String.class);

        redisKey += bizNo;
        if (StringUtils.isNotBlank(annotation.suffix())) {
            redisKey += annotation.connector() + annotation.suffix();
        }
    }

    private void setUserAndPermission() {

        UserAndPermissionContext userAndPermissionContext = new UserAndPermissionContext();

        //用户赋值
        LoginContext loginContext = new LoginContext();
        loginContext.setUserName("wangjia");
        loginContext.setPassword("123456");

        //权限赋值
        PermissionContext permissionContext = new PermissionContext();
        permissionContext.setOrgNo("DEP202003213530");

        userAndPermissionContext.setLoginContext(loginContext);
        userAndPermissionContext.setPermissionContext(permissionContext);

        LoginUserContextHodler.set(userAndPermissionContext);

    }


    public static Object paramTest(ProceedingJoinPoint pjp){
        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //参数名称的集合
        String[] parameterNames = methodSignature.getParameterNames();

        //取出对应的pin 账号  参数是简单参数
        int pinIndex = ArrayUtils.indexOf(parameterNames, "pin");
          String pin = (String) args[pinIndex];

        //  参数是多个对象
        UserDto userDto = new UserDto();
        Object a = args[0];
        BeanUtils.copyProperties(a, userDto);

        System.out.println(userDto.getPin());
        return null;
    }




    /**
     * 获取请求参数
     *
     * @param map
     * @return
     */
    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder("{");
        int i = 0;
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            if (whiteParamList.contains(e.getKey())) {
                continue;
            }
            if (i != 0) {
                sb.append(",");
            }
            sb.append("\"").append(e.getKey()).append("\":\"");
            String[] value = e.getValue();
            if (value.length == 1) {
                sb.append(value[0]).append("\"");
            } else {
                sb.append(Arrays.toString(value)).append("\"");
            }
        }
        sb.append("}");
        return sb.toString();
    }




    //获取 TestController 方法上的注解   。。。。。。  类上 属性上
    public static void main(String[] args) {
        //得到所有方法
        Method[] methods = TestController.class.getDeclaredMethods();
        if(methods != null){
            for(Method method : methods){
                //遍历某个方法上的get 某个指定的注解
                Login annotation = method.getAnnotation(Login.class);
                if (method.isAnnotationPresent(Login.class)) {
                    System.out.println(method+ "方法上有test注解");
                }
                if(annotation == null) {
                    continue;
                }
                //注解的上的值  注解值  进而进行逻辑处理
                int i = annotation.value();
                System.out.println(i);
            }
        }
    }




//    //Aop JoinPoint方法详解
//    重要方法
//    /*获取参数的值数组*/
//    Object[] args = point.getArgs();                                    //  [1] 参数的值
//    /*获取目标对象(被加强的对象)*/
//    Object target = point.getTarget();
//    /*获取signature 该注解作用在方法上，强转为 MethodSignature*/
//    MethodSignature signature = (MethodSignature) point.getSignature();
//    /*方法名*/
//    String signatureName = signature.getName();                         //  findById
//    /*参数名称数组(与args参数值意义对应)*/
//    String[] parameterNames = signature.getParameterNames();            //  [i] 参数名称
//    /*获取执行的方法对应Method对象*/
//    Method method = signature.getMethod();                              //  public void com.draymond.aop2.service.UserService.findById(int)
//    /*获取返回值类型*/
//    Class returnType = signature.getReturnType();                       //  void
//    /*获取方法上的注解*/
//    WebAnnotation webAnnotation = method.getDeclaredAnnotation(WebAnnotation.class);
//
//
//    //常用的其他对象 request / response
//
// 　　// 获取request/response(ThreadLocal模式)
//    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
//    HttpServletRequest request = servletRequestAttributes.getRequest();
//    HttpServletResponse response = servletRequestAttributes.getResponse();
}

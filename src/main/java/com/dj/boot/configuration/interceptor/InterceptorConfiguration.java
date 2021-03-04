package com.dj.boot.configuration.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InterceptorConfiguration implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(InterceptorConfiguration.class);

    private static final String TOKEN_KEY = "token";

    /**
     * token验证
     * @param token
     * @return
     */
    private boolean checkToken(String token) {
        if (StringUtils.hasText(token)) {
            // token有效性校验
            if (redisService.checkKeyIsExist(token)) {
                // 验证通过
                redisService.expireKey(token, 3600);
                return true;
            }
        }
        return false;
    }
    @Autowired
    private RedisService redisService;
    /**
     * 进入controller层之前拦截请求
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        log.info("---------------------开始进入请求地址拦截----------------------------");
        // 判断请求类型
        // 是ajax请求 从Harder中获取Token信息
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            // 从请求的头中获取TOKEN
            String token = request.getHeader(TOKEN_KEY);
            if (checkToken(token)) {
                //设置 key的过期时间
                redisService.expireKey(token, -1);
                return true;
            }
            // 设置未登录状态码
            response.setStatus(401);
            JSONObject result = new JSONObject();
            result.put("code", 401);
            result.put("msg", "未登录");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().append(result.toJSONString());
            return false;
        } else {
            //普通请求
            // String token = request.getParameter(TOKEN_KEY);

            // 从请求的头中获取TOKEN
            String token = request.getHeader(TOKEN_KEY);
            if (checkToken(token)) {
                //设置 key的过期时间
                redisService.expireKey(token, -1);
                return true;
            }
            //response.sendRedirect(request.getContextPath() + "/storeProduct/toShow");//转发到首页
            // 设置未登录状态码
            response.setStatus(401);
            JSONObject result = new JSONObject();
            result.put("code", 401);
            result.put("msg", "未登录");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().append(result.toJSONString());
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("---------------视图渲染之后的操作-------------------------0");
    }
}

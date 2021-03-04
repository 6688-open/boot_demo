//package com.dj.boot.aspect;
//
//import com.dj.boot.aspect.util.RequestConfig;
//import com.dj.boot.aspect.util.ResponseUtils;
//import com.dj.boot.common.redis.RedisService;
//import com.dj.boot.pojo.User;
//import com.dj.boot.service.user.UserService;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Aspect
//@Order(4)
//@Component
//public class AntiCsrfAspect {
//	@Autowired
//	private RedisService redisService;
//	@Autowired
//	private UserService userService;
//
//	private Logger logger = LoggerFactory.getLogger(AntiCsrfAspect.class);
//
//	@Around(value = "com.dj.boot.aspect.Pointcuts.pointcutForActions()")
//	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//
//		logger.info("------------------CSRF--跨站请求伪造---------------防止CSRF攻击 starting......-----------------------------------------------");
//
//		//用户登录将用户信息存到redis中  token
//		User u = new User();
//		u.setUserName("1");
//		u.setToken("1111111111111111111");
//		redisService.set("USER",u);
//
//		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = servletRequestAttributes.getRequest();
//		//获取客户端向服务器传送数据的方法
//		if (!RequestConfig.REQUEST_METHODS_NEED_ANTI_CSRF_TOKEN.contains(request.getMethod())) {
//			return joinPoint.proceed();
//		} else {
//			HttpServletResponse response = servletRequestAttributes.getResponse();
//			User user = redisService.get("USER");
//			if (user == null) {
//				//未登录
//				ResponseUtils.unauthorizedResponse(response);
//				return null;
//			} else {
//				//判断页面hearder token 与用户redis存的token的是否一致
//				String token = request.getHeader("CSRFToken");
//				if (!StringUtils.isAllBlank(new CharSequence[]{token}) && token.equals(user.getToken())) {
//					return joinPoint.proceed();
//				} else {
//					ResponseUtils.badRequestResponse(response, "Need valid token!");
//					return null;
//				}
//			}
//		}
//	}
//}

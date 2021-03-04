package com.dj.boot.aspect;

import com.dj.boot.annotation.ConcurrentLock;
import com.dj.boot.aspect.util.ResponseUtils;
import com.dj.boot.btp.exception.ConcurrentLockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.aspect
 * @Author: wangJia
 * @Date: 2020-08-17-10-56
 */
@Aspect
@Slf4j
@Order(2)
@Component
public class ConcurrentLockAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final ExpressionParser parser = new SpelExpressionParser();


    /**
     * //定义增强，pointcut连接点使用@annotation（xxx）进行定义
     * 指定注解进行 拦截某个方法
     * @param pjp
     * @param annotation
     * @return
     * @throws Throwable
     */
    @Around("@annotation(annotation)")
    public Object doAround(ProceedingJoinPoint pjp, ConcurrentLock annotation) throws Throwable {
        String value = annotation.value();

        String userId = UUID.randomUUID().toString();
        //String lockKey = "productKey_001";
        String redisKey = "";
        //Lock lock = null;
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            String[] parameterNames = signature.getParameterNames();
            Object[] args = pjp.getArgs();

            StandardEvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }

            if (StringUtils.isNotBlank(annotation.prefix())) {
                redisKey += annotation.prefix() + annotation.connector();
            }
            String bizNo = parser.parseExpression(annotation.bizNo()).getValue(context, String.class);

            redisKey += bizNo;
            if (StringUtils.isNotBlank(annotation.suffix())) {
                redisKey += annotation.connector() + annotation.suffix();
            }
            if (log.isDebugEnabled()) {
                log.debug("防并发redis锁的key为：[{}]", redisKey);
            }
            if (StringUtils.isBlank(value)) {
                value = String.valueOf(System.nanoTime());
            }

            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = servletRequestAttributes.getResponse();
            Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(redisKey, userId, 10, TimeUnit.SECONDS);
            if (Objects.isNull(ifAbsent) || !ifAbsent) {
                ResponseUtils.badRequestResponse(response, "业务正在处理中,请稍后再次操作");
                return null;
                //throw new ConcurrentLockException(400, "业务正在处理中,请稍后再次操作");
            }
                //扣减库存
        } catch (Exception c) {
            throw c;
        }

        try {
            return pjp.proceed();
        } finally {
            //释放锁
            if (Objects.equals(userId, redisTemplate.opsForValue().get(redisKey))) {
                redisTemplate.delete(redisKey);
            }
        }

    }
}

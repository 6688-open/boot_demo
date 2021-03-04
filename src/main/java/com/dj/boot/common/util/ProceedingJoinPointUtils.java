package com.dj.boot.common.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 
 * ProceedingJoinPointUtils
 * 
 * @author: Lenovo
 * @date: 2018-11-11 14:01:31
 * @Copyright: 2018 www.lenovo.com Inc. All rights reserved.
 */
public class ProceedingJoinPointUtils {
	public static Method getMethod(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException, SecurityException {
		Signature signature = proceedingJoinPoint.getSignature();

		if (!(signature instanceof MethodSignature)) {
			throw new IllegalArgumentException("Illegal signature!");
		}

		MethodSignature methodSignature = (MethodSignature) signature;
		return proceedingJoinPoint.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
	}

	public static boolean isAnnotation(JoinPoint joinPoint, Class<? extends Annotation> annotationClass) throws NoSuchMethodException, SecurityException {
		Class<?> clazz = joinPoint.getTarget().getClass();

		if (clazz.isAnnotationPresent(annotationClass)) {
			return true;
		}

		Signature signature = joinPoint.getSignature();

		if (!(signature instanceof MethodSignature)) {
			throw new IllegalArgumentException("Illegal signature!");
		}

		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = clazz.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
		return method.isAnnotationPresent(annotationClass);
	}
}
package com.dj.boot.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 
 * Pointcuts
 * 
 * @author: wj
 * @date: 2018-12-18 15:32:15
 */
public class Pointcuts {
	/**
	 * Pointcut for handle exception.
	 */
	@Pointcut("pointcutForActions() || pointcutForAspects()")
	public void pointcutForException() {

	}

	/**
	 * Pointcut for all actions.
	 */
	@Pointcut("execution(public * com.dj.boot.controller..*.*(..))")
	public void pointcutForActions() {

	}

	/**
	 * Pointcut for aspect.
	 */
	@Pointcut("execution(public * com.dj.boot.aspect..*(..))")
	public void pointcutForAspects() {

	}
}
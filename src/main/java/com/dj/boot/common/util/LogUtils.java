package com.dj.boot.common.util;

import com.dj.boot.common.util.base.SingletonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * LogUtils
 * 
 * @author: Lenovo
 * @date: 2018-12-26 14:01:16
 * @Copyright: 2018 www.lenovo.com Inc. All rights reserved.
 */
public class LogUtils {
	private volatile static ConcurrentMap<String, Logger> container = new ConcurrentHashMap<>();
	public static Logger getLogger() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement stackTraceElement = stackTraceElements[3];
		String className = stackTraceElement.getClassName();

		if (container.get(className) != null) {
			return container.get(className);
		}

		synchronized (SingletonFactory.class) {
			if (container.get(className) == null) {
				container.put(className, LoggerFactory.getLogger(className));
			}
		}

		return container.get(className);
	}

	public static void trace(String message) {
		Logger logger = getLogger();
		logger.trace(message);
	}

	public static void debug(String message) {
		Logger logger = getLogger();
		logger.debug(message);
	}

	public static void info(String message) {
		Logger logger = getLogger();
		logger.info(message);
	}

	public static void info(String format, Object arg) {
		Logger logger = getLogger();
		logger.info(format, arg);
	}

	public static void warn(String message) {
		Logger logger = getLogger();
		logger.warn(message);
	}

	public static void error(String message) {
		Logger logger = getLogger();
		logger.error(message);
	}
	public static void error(String message, Throwable t) {
		Logger logger = getLogger();
		logger.error(message,t);
	}
}
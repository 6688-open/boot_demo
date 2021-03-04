package com.dj.boot.common.util.lang;

import java.lang.reflect.Constructor;

/**
 * 
 * EnumUtils
 * 
 * @author: Aldwin Su
 * @version: 2019-11-22 20:10:07
 */
public class EnumUtils {
	/**
	 * Get max parameter count from constructors in provided Class object.
	 * @param clazz Class object of Enum.
	 * @return int
	 */
	public static int maxConstructorParameterCount(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();

		if (constructors == null) {
			return 0;
		}

		int parameterCount = constructors[0].getParameterCount();

		for (int i = 1; i < constructors.length; i++) {
			if (parameterCount < constructors[i].getParameterCount()) {
				parameterCount = constructors[i].getParameterCount();
			}
		}

		return parameterCount;
	}
}
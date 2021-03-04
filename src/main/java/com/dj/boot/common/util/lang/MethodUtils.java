package com.dj.boot.common.util.lang;

import com.dj.boot.common.util.StringUtils;
import com.dj.boot.common.util.collection.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * MethodUtils
 * 
 * @author: Aldwin Su
 */
public class MethodUtils {
	/**
	 * Method whether contains annotations?
	 * @param method: Method object.
	 * @param annotations: Annotations want to check.
	 * @return boolean
	 */
	public static boolean contain(Method method, List<Annotation> annotations) {
		if (method == null || CollectionUtils.isNullOrEmpty(annotations)) {
			return false;
		}

		List<Annotation> fieldAnnotations = Arrays.asList(method.getAnnotations());
		return CollectionUtils.exist(annotations, i -> fieldAnnotations.contains(i));
	}

	/**
	 * Find Method object from Method objects by method name.
	 * @param name Method name.
	 * @param methods Methods object.
	 * @return Method
	 */
	public static Method find(String name, List<Method> methods) {
		for (Method method : methods) {
			if (name.equals(method.getName())) {
				return method;
			}
		}

		return null;
	}

	/**
	 * Find Method object from Method objects by method name.
	 * @param name Method name.
	 * @param methods Methods array.
	 * @return Method
	 */
	public static Method find(String name, Method[] methods) {
		for (Method method : methods) {
			if (name.equals(method.getName())) {
				return method;
			}
		}

		return null;
	}

	/**
	 * Return name of Getter method.
	 * @param field Field object.
	 * @return String
	 */
	public static String getterName(Field field) {
		Class<?> fieldType = field.getType();

		if (fieldType == Boolean.class || "boolean".equals(fieldType.getName())) {
			return String.format("is%s", StringUtils.upperFirstCase(field.getName()));
		}

		return String.format("get%s", StringUtils.upperFirstCase(field.getName()));
	}

	/**
	 * Return name of Setter method.
	 * @param field Field object.
	 * @return String
	 */
	public static String setterName(String field) {
		return String.format("set%s", StringUtils.upperFirstCase(field));
	}

	/**
	 * Does field has Getter method or not?
	 * @param field Field object.
	 * @param methods Names of methods.
	 * @return boolean
	 */
	public static boolean hasGetter(Field field, List<String> methods) {
		String name = getterName(field);
		return methods.contains(name);
	}

	/**
	 * Does field has Getter method or not?
	 * @param field Field object.
	 * @param methods Names of methods.
	 * @return boolean
	 */
	public static boolean hasSetter(String field, List<String> methods) {
		String name = setterName(field);
		return methods.contains(name);
	}

	/**
	 * Return Setter Method object.
	 * @param field Field object.
	 * @param methods Method objects.
	 * @return Method
	 */
	public static Method setter(String field, List<Method> methods) {
		String name = setterName(field);
		return find(name, methods);
	}

	/**
	 * Return Getter Method object.
	 * @param field Field object.
	 * @param methods Method objects.
	 * @return Method
	 */
	public static Method getter(Field field, List<Method> methods) {
		String name = getterName(field);
		return find(name, methods);
	}

	/**
	 * Whether method is static method or not?
	 * @param method Method object.
	 * @return boolean
	 */
	public static boolean isStatic(Method method) {
		return Modifier.isStatic(method.getModifiers());
	}

	/**
	 * Invoke provided method.
	 * @param method Method object.
	 * @param args Parameters for invoking method.
	 * @return Object
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static Object invoke(Method method, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		if (isStatic(method)) {
			return method.invoke(null, args);
		}

		Class<?> clazz = method.getDeclaringClass();
		return method.invoke(clazz.newInstance(), args);
	}

	/**
	 * Invoke provided method and return result with inherent type.
	 * @param method Method object.
	 * @param args Parameters for invoking method.
	 * @return {@code T}
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public static <T> T call(Method method, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Object value = invoke(method, args);

		if (value == null) {
			return null;
		}

		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) method.getReturnType();
		return clazz.cast(value);
	}

	/**
	 * Invoke provided method without any exceptions.
	 * @param method Method object.
	 * @param args Parameters for invoking method.
	 * @return Object
	 */
	public static Object quietInvoke(Method method, Object... args) {
		try {
			return invoke(method, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Invoke provided method without any exceptions.
	 * @param method Method object.
	 * @param args Parameters for invoking method.
	 * @return {@code T}
	 */
	public static <T> T quietCall(Method method, Object... args) {
		Object value = quietInvoke(method, args);

		if (value == null) {
			return null;
		}

		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) method.getReturnType();
		return clazz.cast(value);
	}
}
package com.dj.boot.common.util.lang;

import com.dj.boot.common.util.collection.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 
 * ClassUtils
 * 
 * @author: Aldwin Su
 */
public class ClassUtils {
	/**
	 * Create a new instance.
	 * @param clazz: Class object
	 * @return {@code  T}
	 * @throws ReflectiveOperationException
	 */
	public static <T> T newInstance(Class<T> clazz) throws ReflectiveOperationException {
		return clazz.getDeclaredConstructor().newInstance();
	}

	/**
	 * Create a new instance.
	 * @param clazz: Class object
	 * @return {@code  T}
	 */
	public static <T> T instance(Class<T> clazz) {
		try {
			return newInstance(clazz);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get super classes include itself.
	 * @param clazz Class object
	 * @return {@code List<Class<?>>}
	 */
	public static <T> List<Class<?>> getSuperClasses(Class<T> clazz) {
		List<Class<?>> list = new LinkedList<>();

		if (clazz == null) {
			return list;
		}

		Class<?> temp = clazz;

		while (temp != null && !"java.lang.Object".equals(temp.getCanonicalName())) {
			list.add(temp);
			temp = temp.getSuperclass();
		}

		return list;
	}

	/**
	 * Get all of declared fields include provided class and its super class.
	 * @param clazz Class object.
	 * @return {@code List<Field>}
	 */
	public static List<Field> getAllDeclaredFields(Class<?> clazz) {
		List<Field> list = new LinkedList<>();
		List<Class<?>> classes = getSuperClasses(clazz);

		if (classes.size() == 0) {
			return list;
		}

		for (Class<?> cl : classes) {
			list.addAll(Arrays.asList(cl.getDeclaredFields()));
		}

		return list;
	}

	/**
	 * Get all of declared methods include provided class and its super class.
	 * @param clazz Class object.
	 * @return {@code List<Method>}
	 */
	public static List<Method> getAllDeclaredMethods(Class<?> clazz) {
		List<Method> list = new LinkedList<>();
		List<Class<?>> classes = getSuperClasses(clazz);

		if (classes.size() == 0) {
			return list;
		}

		for (Class<?> cl : classes) {
			list.addAll(Arrays.asList(cl.getDeclaredMethods()));
		}

		return list;
	}

	/**
	 * Get EntityFields and include specified fields.
	 * @param includeFields: Fields want to include.
	 * @param clazz: Class object
	 * @return {@code List<EntityField>}
	 */
	public static List<EntityField> getIncludedEntityFields(List<String> includeFields, Class<?> clazz) {
		List<EntityField> list = getEntityFields(clazz);
		return EntityFieldUtils.includeEntityFields(list, includeFields);
	}

	/**
	 * Get EntityFields and exclude specified fields.
	 * @param clazz: Class object
	 * @param excludeFields: Fields want to exclude.
	 * @return {@code List<EntityField>}
	 */
	public static List<EntityField> getNonExcludedEntityFields(List<String> excludeFields, Class<?> clazz) {
		List<EntityField> list = getEntityFields(clazz);
		return EntityFieldUtils.excludeEntityFields(list, excludeFields);
	}

	/**
	 * Get sorted EntityField list and exclude specified fields.
	 * @param excludeFields EntityField list.
	 * @param clazz Class object.
	 * @return {@code List<EntityField>}
	 */
	public static List<EntityField> getSortedAndNonExcludedEntityFields(List<String> excludeFields, Class<?> clazz) {
		List<EntityField> list = getEntityFields(clazz);
		list = EntityFieldUtils.excludeEntityFields(list, excludeFields);
		list.sort(Comparator.comparingInt(EntityField::getOrder));
		return list;
	}

	/**
	 * Get EntityFields and exclude specified annotations.
	 * @param clazz: Class object
	 * @param excludeAnnotations: Annotations want to be excluded.
	 * @return {@code List<EntityField>}
	 */
	public static List<EntityField> getNonExcludedEntityFields(Annotation[] excludeAnnotations, Class<?> clazz) {
		List<EntityField> list = getEntityFields(clazz);
		List<Annotation> annotations = Arrays.asList(excludeAnnotations);
		return CollectionUtils.list(list, i -> i.containAnnotations(annotations));
	}

	/**
	 * Get EntityFields.
	 * @param clazz Class object
	 * @return {@code List<EntityField>}
	 */
	public static List<EntityField> getEntityFields(Class<?> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("Illegal parameter!");
		}

		List<Field> declaredFields = getAllDeclaredFields(clazz);
		List<Method> declaredMethods = getAllDeclaredMethods(clazz);
		List<EntityField> list = new ArrayList<>(declaredFields.size());

		for (Field declaredField : declaredFields) {
			Method getter = MethodUtils.getter(declaredField, declaredMethods);
			Method setter = MethodUtils.setter(declaredField.getName(), declaredMethods);

			if (setter == null || getter == null) {
				continue;
			}

			EntityField entityField = new EntityField();
			entityField.setField(declaredField);
			entityField.setSetter(setter);
			entityField.setGetter(getter);
			entityField.setOrder(FieldUtils.getOrder(declaredField));
			list.add(entityField);
		}

		return list;
	}

	/**
	 * Get sorted EntityField list.
	 * @param clazz Class object.
	 * @return {@code List<EntityField>}
	 */
	public static List<EntityField> getSortedEntityFields(Class<?> clazz) {
		List<EntityField> list = getEntityFields(clazz);
		list.sort(Comparator.comparingInt(EntityField::getOrder));
		return list;
	}

	/**
	 * Get Method object by provided name and clazz.
	 * @param name: Method name.
	 * @param clazz: Class object.
	 * @return Method
	 */
	public static Method getMethod(String name, Class<?> clazz) {
		List<Method> methods = getAllDeclaredMethods(clazz);
		return MethodUtils.find(name, methods);
	}

	/**
	 * Return Generic Class object by provided generic type.
	 * @param genericType Type object for generic type.
	 * @return {@code Class<?>}
	 */
	public static Class<?> getGenericClass(Type genericType) {
		if (genericType != null && genericType instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) genericType;
			return (Class<?>) parameterizedType.getActualTypeArguments()[0];
		}

		return null;
	}

	/**
	 * Check whether object is instance of clazz?
	 * @param object: Object need check.
	 * @param clazz: Target Class object.
	 * @return boolean
	 */
	public static boolean isInstanceOf(Object object, Class<?> clazz) {
		return clazz.isInstance(object) || clazz.isAssignableFrom(object.getClass());
	}

	/**
	 * Whether clazz is Jave class
	 * @param clazz: Class object need check
	 * @return boolean
	 */
	public static boolean isJaveClass(Class<?> clazz) {
		return clazz != null && clazz.getClassLoader() == null;
	}
}
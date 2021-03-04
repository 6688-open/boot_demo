package com.dj.boot.common.util.lang;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericTypeUtils {
	/**
	 * Convert Type to ParameterizedType.
	 * @param type: Type object.
	 * @return ParameterizedType
	 */
	public static ParameterizedType typeToParameterizedType(Type type) {
		if (type instanceof ParameterizedType) {
			return (ParameterizedType) type;
		}

		return null;
	}

	/**
	 * Convert type to Class.
	 * @param type: Type object.
	 * @return {@code Class<?>}
	 * @throws ClassNotFoundException When type not found.
	 */
	public static Class<?> typeToClass(Type type) throws ClassNotFoundException {
		if (type == null || !(type instanceof Class)) {
			return null;
		}

		return Class.forName(type.getTypeName());
	}

	/**
	 * Get generic type.
	 * @param parameterizedType: ParameterizedType object.
	 * @return Type
	 */
	public static Type getGenericType(ParameterizedType parameterizedType) {
		if (parameterizedType == null) {
			return null;
		}

		Type[] types = parameterizedType.getActualTypeArguments();

		if (types == null || types.length == 0) {
			return null;
		}

		return types[0];
	}

	/**
	 * Get actual generic type by field's parameterized type.
	 * @param field: Field object.
	 * @return Type
	 */
	public static Type getGenericType(Field field) {
		Type type = field.getGenericType();
		ParameterizedType parameterizedType = typeToParameterizedType(type);
		return getGenericType(parameterizedType);
	}

	/**
	 * Get actual generic Class object by field's parameterized type.
	 * @param field: Field object.
	 * @return {@code Class<?>}
	 * @throws ClassNotFoundException When type not found.
	 */
	public static Class<?> getGenericClass(Field field) throws ClassNotFoundException {
		Type genericType = getGenericType(field);
		return typeToClass(genericType);
	}

	/**
	 * Get actual generic Class object by type.
	 * @param type: Type object.
	 * @return {@code Class<?>}
	 * @throws ClassNotFoundException When type not found.
	 */
	public static Class<?> getGenericClass(Type type) throws ClassNotFoundException {
		ParameterizedType parameterizedType = typeToParameterizedType(type);
		Type genericType = getGenericType(parameterizedType);
		return typeToClass(genericType);
	}

	/**
	 * Get Class object of List.
	 * @param list: List object.
	 * @return {@code Class<?>}
	 */
	public static Class<?> getGenericClass(List<?> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0).getClass();
	}
}
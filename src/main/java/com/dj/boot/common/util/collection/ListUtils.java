package com.dj.boot.common.util.collection;

import com.dj.boot.common.util.lang.BeanUtils;
import com.dj.boot.common.util.lang.ClassUtils;
import com.dj.boot.common.util.lang.EntityField;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ListUtils {
	/**
	 * Sort string list by alphabet ascendingly.
	 * @param list: String list.
	 */
	public static void sortByAlphabet(List<String> list) {
		if (CollectionUtils.isNullOrEmpty(list)) {
			return;
		}

		list.sort((i1, i2) -> {
			if (i1 == null && i2 == null) {
				return 0;
			}

			if (i1 == null && i2 != null) {
				return -1;
			}

			if (i1 != null && i2 == null) {
				return 1;
			}

			return i1.compareToIgnoreCase(i2);
		});
	}

	/**
	 * List to arrays
	 * @param list: Bean list
	 * @param clazz: Class object of bean.
	 * @return String[][]
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> String[][] toArrays(List<T> list, Class<T> clazz) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<EntityField> entityFields = ClassUtils.getEntityFields(clazz);
		return toArrays(list, entityFields);
	}

	/**
	 * List to arrays
	 * @param list: Bean list.
	 * @param entityFields: EntityFields of {@code T}
	 * @return String[][]
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> String[][] toArrays(List<T> list, List<EntityField> entityFields) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (CollectionUtils.isNullOrEmpty(list) || CollectionUtils.isNullOrEmpty(entityFields)) {
			return new String[0][];
		}

		String[][] arrays = new String[list.size()][];

		for (int i = 0; i < arrays.length; i++) {
			T entity = list.get(i);
			String[] array = BeanUtils.toArray(entity, entityFields);

			if (array != null) {
				arrays[i] = array;
			}
		}

		return arrays;
	}

	/**
	 * Arrays to entities list
	 * @param arrays: Arrays
	 * @param clazz: Class object of bean.
	 * @return {@code List<T>}
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> List<T> toEntities(String[][] arrays, Class<T> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<EntityField> entityFields = ClassUtils.getEntityFields(clazz);
		return toEntities(arrays, clazz, entityFields);
	}

	/**
	 * Arrays to entities list
	 * @param arrays: Arrays
	 * @param clazz: Class object of bean.
	 * @param entityFields: EntityFields of {@code T}
	 * @return {@code List<T>}
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> List<T> toEntities(String[][] arrays, Class<T> clazz, List<EntityField> entityFields) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (ArraysUtils.isNullOrEmpty(arrays) || CollectionUtils.isNullOrEmpty(entityFields)) {
			return new ArrayList<>();
		}

		List<T> list = new ArrayList<>(arrays.length);

		for (int i = 0; i < arrays.length; i++) {
			T entity = BeanUtils.toEntity(arrays[i], entityFields, clazz);

			if (entity != null) {
				list.add(entity);
			}
		}

		return list;
	}
}
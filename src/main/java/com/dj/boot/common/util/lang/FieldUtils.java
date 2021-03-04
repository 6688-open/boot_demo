package com.dj.boot.common.util.lang;

import com.dj.boot.annotation.Order;
import com.dj.boot.common.util.collection.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class FieldUtils {
	/**
	 * Field whether contains annotations?
	 * @param field: Field object
	 * @param annotations: Annotations want to check.
	 * @return boolean
	 */
	public static boolean contain(Field field, List<Annotation> annotations) {
		if (field == null || CollectionUtils.isNullOrEmpty(annotations)) {
			return false;
		}

		List<Annotation> fieldAnnotations = Arrays.asList(field.getAnnotations());
		return CollectionUtils.exist(annotations, i -> fieldAnnotations.contains(i));
	}

	/**
	 * Return Order value by provided Field object. 0 if no Order setting.
	 * @param field Field object.
	 * @return int
	 */
	public static int getOrder(Field field) {
		Order order = field.getAnnotation(Order.class);

		if (order == null) {
			return 0;
		}

		return order.value();
	}
}
package com.dj.boot.common.util.lang;

import com.dj.boot.common.util.collection.CollectionUtils;

import java.util.List;

public class EntityFieldUtils {
	public static List<EntityField> includeEntityFields(List<EntityField> entityFields, List<String> includeFields) {
		if (CollectionUtils.isNullOrEmpty(entityFields) || CollectionUtils.isNullOrEmpty(includeFields)) {
			return entityFields;
		}

		return CollectionUtils.list(entityFields, i -> i.getField() != null && includeFields.contains(i.getField().getName()));
	}

	public static List<EntityField> excludeEntityFields(List<EntityField> entityFields, List<String> excludeFields) {
		if (CollectionUtils.isNullOrEmpty(entityFields) || CollectionUtils.isNullOrEmpty(excludeFields)) {
			return entityFields;
		}

		return CollectionUtils.list(entityFields, i -> i.getField() != null && !excludeFields.contains(i.getField().getName()));
	}
}
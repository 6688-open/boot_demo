package com.dj.boot.common.util.lang;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 
 * EntityField
 * 
 * @author: Aldwin Su
 * @version: 2019-06-05 15:24:00
 */
public class EntityField {
	private Field field;
	private Method setter;
	private Method getter;
	private int order;

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Method getSetter() {
		return setter;
	}

	public void setSetter(Method setter) {
		this.setter = setter;
	}

	public Method getGetter() {
		return getter;
	}

	public void setGetter(Method getter) {
		this.getter = getter;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Object getValue(Object model) throws ReflectiveOperationException {
		return getter.invoke(model);
	}

	public boolean containAnnotations(List<Annotation> annotations) {
		if (FieldUtils.contain(field, annotations)) {
			return true;
		}

		if (MethodUtils.contain(setter, annotations)) {
			return true;
		}

		if (MethodUtils.contain(getter, annotations)) {
			return true;
		}

		return false;
	}

	public Class<?> fieldType() {
		return getField() != null ? getField().getType() : null;
	}
}
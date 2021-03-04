package com.dj.boot.common.util.collection;

import com.dj.boot.annotation.PropertiesKey;
import com.dj.boot.common.util.StringUtils;
import com.dj.boot.common.util.lang.BeanUtils;
import com.dj.boot.common.util.lang.ClassUtils;
import com.dj.boot.common.util.lang.EntityField;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PropertiesUtils {
	/**
	 * Get properties.
	 * @param propertiesFile: Properties file name.
	 * @return Properties
	 */
	public static Properties getProperties(String propertiesFile) {
		InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesFile);
		return getProperties(inputStream);
	}

	/**
	 * Get properties
	 * @param inputStream: InputStream of properties file
	 * @return Properties
	 */
	public static Properties getProperties(InputStream inputStream) {
		try (InputStream inStream = inputStream) {
			Properties properties = new Properties();
			properties.load(inStream);
			return properties;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Check one properties object whether contains prefix.
	 * @param properties: Properties object
	 * @param prefix: Prefix of property key
	 * @return boolean
	 */
	public static boolean containsPrefix(Properties properties, String prefix) {
		List<Object> keys = Collections.list(properties.keys());

		for (Object key : keys) {
			if (key.toString().indexOf(prefix) == 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Check one properties file whether contains prefix.
	 * @param resourceName: Properties file
	 * @param prefix: Prefix of property key
	 * @return boolean
	 */
	public static boolean containsPrefix(String resourceName, String prefix) {
		Properties properties = getProperties(resourceName);
		return containsPrefix(properties, prefix);
	}

	/**
	 * Check one properties object whether contains key.
	 * @param properties: Properties object
	 * @param key: Key name for you query
	 * @return boolean
	 */
	public static boolean containsKey(Properties properties, String key) {
		return properties.containsKey(key);
	}

	/**
	 * Check one properties file whether contains key.
	 * @param resourceName: Properties file
	 * @param key: Key name for you query
	 * @return boolean
	 */
	public static boolean containsKey(String resourceName, String key) {
		Properties properties = getProperties(resourceName);
		return containsKey(properties, key);
	}

	/**
	 * Get property value.
	 * @param properties: Properties object
	 * @param key: Key name for you query
	 * @return String
	 */
	public static String getProperty(Properties properties, String key) {
		if (properties == null) {
			return null;
		}

		return properties.getProperty(key);
	}

	/**
	 * Get property value.
	 * @param resourceName: Properties file
	 * @param key: Key name for you query
	 * @return String
	 */
	public static String getProperty(String resourceName, String key) {
		Properties properties = getProperties(resourceName);
		return getProperty(properties, key);
	}

	/**
	 * Get property int value.
	 * @param properties: Properties object
	 * @param key: Key name for you query
	 * @return int
	 */
	public static int getIntProperty(Properties properties, String key) {
		String value = getProperty(properties, key);

		if (value == null) {
			return 0;
		}

		return Integer.parseInt(value);
	}

	/**
	 * Get property int value.
	 * @param resourceName: Properties file
	 * @param key: Key name for you query
	 * @return int
	 */
	public static int getIntProperty(String resourceName, String key) {
		String value = getProperty(resourceName, key);

		if (value == null) {
			return 0;
		}

		return Integer.parseInt(value);
	}

	/**
	 * Convert properties to Map
	 * @param resourceName: Properties file
	 * @return {@code Map<String,String>}
	 */
	public static Map<String, String> toMap(String resourceName) {
		Properties properties = getProperties(resourceName);
		return toMap(properties);
	}

	/**
	 * Convert properties to Map
	 * @param properties: Properties object
	 * @return {@code Map<String,String>}
	 */
	public static Map<String, String> toMap(Properties properties) {
		Map<String, String> map = new LinkedHashMap<>();

		if (properties == null) {
			return map;
		}

		for (String name : properties.stringPropertyNames()) {
			map.put(name, properties.getProperty(name));
		}

		return map;
	}

	/**
	 * Convert Properties to model.
	 * @param propertiesFile: Properties file.
	 * @param prefix: Prefix of property key.
	 * @param clazz: Model Class object.
	 * @return {@code T}
	 * @throws NumberFormatException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> T toModel(String propertiesFile, String prefix, Class<T> clazz) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Properties properties = getProperties(propertiesFile);
		return toModel(properties, prefix, clazz);
	}

	/**
	 * Convert Properties to model.
	 * @param propertiesFile: Properties file.
	 * @param clazz: Model Class object.
	 * @return {@code T}
	 * @throws NumberFormatException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> T toModel(String propertiesFile, Class<T> clazz) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Properties properties = getProperties(propertiesFile);
		return toModel(properties, clazz);
	}

	/**
	 * Convert Properties to model.
	 * @param properties: Properties object.
	 * @param prefix: Prefix of property key.
	 * @param clazz: Model Class object.
	 * @return {@code T}
	 * @throws NumberFormatException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> T toModel(Properties properties, String prefix, Class<T> clazz) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		T model = ClassUtils.instance(clazz);

		boolean hasProperty = false;
		List<EntityField> entityFields = ClassUtils.getEntityFields(clazz);

		for (EntityField entityField : entityFields) {
			String value = getPropertyValue(properties, prefix, entityField.getField());

			if (value != null) {
				BeanUtils.setField(entityField.getSetter(), model, value, entityField.getField());
				hasProperty = true;
			}
		}

		if (!hasProperty) {
			return null;
		}

		return model;
	}

	/**
	 * Convert Properties to model.
	 * @param properties: Properties object.
	 * @param clazz: Model Class object.
	 * @return {@code T}
	 * @throws NumberFormatException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> T toModel(Properties properties, Class<T> clazz) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		T model = ClassUtils.instance(clazz);

		boolean hasProperty = false;
		List<EntityField> entityFields = ClassUtils.getEntityFields(clazz);

		for (EntityField entityField : entityFields) {
			String value = getPropertyValue(properties, entityField.getField());

			if (value != null) {
				BeanUtils.setField(entityField.getSetter(), model, value, entityField.getField());
				hasProperty = true;
			}
		}

		if (!hasProperty) {
			return null;
		}

		return model;
	}

	/**
	 * Get property key.
	 * @param prefix: Prefix of property key.
	 * @param field: Field object in model.
	 * @return String
	 */
	public static String getPropertyKey(String prefix, Field field) {
		String propertyKey = getPropertyKey(field);

		if (StringUtils.isNotEmpty(propertyKey)) {
			return propertyKey;
		}

		return getPropertyKey(prefix, field.getName());
	}

	/**
	 * Get property key.
	 * @param prefix: Prefix of property key.
	 * @param fieldName: Field name.
	 * @return String
	 */
	public static String getPropertyKey(String prefix, String fieldName) {
		if (StringUtils.isNullOrEmpty(prefix)) {
			return fieldName;
		}

		return String.format("%s.%s", prefix, fieldName);
	}

	/**
	 * Get property key.
	 * @param field: Field object in model.
	 * @return String
	 */
	public static String getPropertyKey(Field field) {
		PropertiesKey propertiesKey = field.getAnnotation(PropertiesKey.class);

		if (propertiesKey == null) {
			return null;
		}

		return propertiesKey.value();
	}

	/**
	 * Get property value.
	 * @param properties: Properties object.
	 * @param prefix: Prefix of property key.
	 * @param field: Field object in model.
	 * @return String
	 */
	public static String getPropertyValue(Properties properties, String prefix, Field field) {
		String propertyKey = getPropertyKey(prefix, field);

		if (StringUtils.isNullOrEmpty(propertyKey)) {
			return null;
		}

		return PropertiesUtils.getProperty(properties, propertyKey);
	}

	/**
	 * Get property value.
	 * @param properties: Properties object.
	 * @param field: Field object in model.
	 * @return String
	 */
	public static String getPropertyValue(Properties properties, Field field) {
		String propertyKey = getPropertyKey(field);

		if (StringUtils.isNullOrEmpty(propertyKey)) {
			propertyKey = field.getName();
		}

		return PropertiesUtils.getProperty(properties, propertyKey);
	}
}
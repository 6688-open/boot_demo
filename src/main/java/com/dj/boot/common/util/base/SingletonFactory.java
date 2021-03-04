package com.dj.boot.common.util.base;

import com.dj.boot.common.util.lang.ClassUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SingletonFactory {
	/**
	 * Container for singleton object.
	 */
	private static volatile ConcurrentMap<String, Object> container = new ConcurrentHashMap<>();

	/**
	 * Get or create a singleton object.
	 * @param clazz: Class of singleton object.
	 * @return {@code T}
	 */
	public static <T> T get(Class<T> clazz) {
		String key = clazz.getName();
		return get(key, clazz);
	}

	/**
	 * Get a singleton object.
	 * @param key: Key of singleton object to save.
	 * @param clazz: Class of singleton object.
	 * @return {@code T}
	 */
	public static <T> T get(String key, Class<T> clazz) {
		if (container.get(key) != null) {
			Object value = container.get(key);

			if (value != null && clazz.isAssignableFrom(value.getClass())) {
				return clazz.cast(value);
			}

			container.remove(key);
		}

		return null;
	}

	/**
	 * Create a singleton object.
	 * @param clazz: Class of singleton object.
	 * @return {@code T}
	 */
	public static <T> T create(Class<T> clazz) {
		String key = clazz.getName();
		return create(key, clazz);
	}

	/**
	 * Create a singleton object.
	 * @param key: Key of instance.
	 * @param clazz: Class of singleton object.
	 * @return {@code T}
	 */
	public static <T> T create(String key, Class<T> clazz) {
		if (container.get(key) != null) {
			Object value = container.get(key);

			if (value != null && value.getClass().isAssignableFrom(clazz)) {
				return clazz.cast(value);
			}

			container.remove(key);
		}

		synchronized (SingletonFactory.class) {
			if (container.get(key) == null) {
				T value = ClassUtils.instance(clazz);
				container.put(key, value);
			}
		}

		return clazz.cast(container.get(key));
	}

	/**
	 * Save an instance in container.
	 * @param key: Key of instance.
	 * @param instance: Singleton instance.
	 * @return {@code T}
	 */
	public static <T> T save(String key, T instance) {
		synchronized (SingletonFactory.class) {
			container.put(key, instance);
		}

		return instance;
	}
}
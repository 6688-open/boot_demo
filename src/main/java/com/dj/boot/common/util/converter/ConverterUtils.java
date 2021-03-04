package com.dj.boot.common.util.converter;

import com.dj.boot.annotation.Convertor;
import com.dj.boot.common.util.StringUtils;
import com.dj.boot.common.util.base.SingletonFactory;
import com.dj.boot.common.util.lang.MethodUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * 
 * ConvertorUtils
 * 
 * @author: Aldwin Su
 */
public class ConverterUtils {
	/**
	 * Cache key format for converter.
	 */
	private final static String CACHEKEYFORMAT_CONVERTER = "Converter-%s-%s";

	/**
	 * Cache key format for converter method.
	 */
	private final static String CACHEKEYFORMAT_CONVERTER_METHOD = "Converter-Method-%s-%s";

	/**
	 * Get cache key for Converter object.
	 * @param sName Name of source Class object.
	 * @param tName Name of target Class object.
	 * @return String
	 */
	public static String getCacheKeyForConverter(String sName, String tName) {
		return String.format(CACHEKEYFORMAT_CONVERTER, sName, tName);
	}

	/**
	 * Get cache key for converter method object.
	 * @param sName Name of source Class object.
	 * @param tName Name of target Class object.
	 * @return String
	 */
	public static String getCacheKeyForConverterMethod(String sName, String tName) {
		return String.format(CACHEKEYFORMAT_CONVERTER_METHOD, sName, tName);
	}

	/**
	 * Get converter from memory by Class object.
	 * @param sClazz: Class object for source type.
	 * @param tClazz: Class object for target type.
	 * @return {@code Converter<S,T>}
	 */
	@SuppressWarnings("unchecked")
	public static <S, T> Converter<S, T> getConverter(Class<S> sClazz, Class<T> tClazz) {
		String key = getCacheKeyForConverter(sClazz.getName(), tClazz.getName());
		return SingletonFactory.get(key, Converter.class);
	}

	/**
	 * Get method annotated with Convertor by provided source Class object and target Class object.
	 * @param sClazz Source Class object.
	 * @param tClazz Target Class object.
	 * @return Method
	 */
	public static <S, T> Method getConvertorMethod(Class<S> sClazz, Class<T> tClazz) {
		String key = getCacheKeyForConverterMethod(sClazz.getName(), tClazz.getName());
		Method method = SingletonFactory.get(key, Method.class);

		if (method != null) {
			return method;
		}

		List<Method> methods = new LinkedList<>();
		methods.addAll(Arrays.asList(sClazz.getDeclaredMethods()));
		methods.addAll(Arrays.asList(tClazz.getDeclaredMethods()));

		for (Method convertorMethod : methods) {
			if (convertorMethod.isAnnotationPresent(Convertor.class)) {
				Class<?>[] parameterTypes = convertorMethod.getParameterTypes();
				Class<?> returnType = convertorMethod.getReturnType();

				if (parameterTypes != null && parameterTypes.length == 1 && parameterTypes[0].equals(sClazz) && returnType != null && returnType.equals(tClazz)) {
					method = convertorMethod;
					break;
				}
			}
		}

		if (method != null) {
			SingletonFactory.save(key, method);
		}

		return method;
	}

	/**
	 * Register a converter in System.
	 * @param converter: Converter object.
	 */
	public static <S, T> void register(Converter<S, T> converter) {
		Type[] types = converter.getClass().getGenericInterfaces();
		ParameterizedType parameterizedType = (ParameterizedType) types[0];
		Type[] actualTypes = parameterizedType.getActualTypeArguments();

		String key = getCacheKeyForConverter(actualTypes[0].getTypeName(), actualTypes[1].getTypeName());
		SingletonFactory.save(key, converter);
	}

	/**
	 * int to byte
	 * @param value: int value
	 * @return byte
	 */
	public static byte intToByte(int value) {
		return (byte) value;
	}

	/**
	 * int to bytes
	 * @param value: int value.
	 * @return byte[]
	 */
	public static byte[] intToBytes(int value) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (value & 0xFF);
		bytes[1] = (byte) ((value >> 8) & 0xFF);
		bytes[2] = (byte) ((value >> 16) & 0xFF);
		bytes[3] = (byte) ((value >> 24) & 0xFF);
		return bytes;
	}

	/**
	 * byte to int
	 * @param value: byte value
	 * @return int
	 */
	public static int byteToInt(byte value) {
		return value & 0xFF;
	}

	/**
	 * bytes to long
	 * @param bytes: bytes array
	 * @param littleEndian: whether need little endian or not?
	 * @return long
	 */
	public static long bytesToLong(byte[] bytes, boolean littleEndian) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes, 0, 8);

		if (littleEndian) {
			buffer.order(ByteOrder.LITTLE_ENDIAN);
		}

		return buffer.getLong();
	}

	/**
	 * bytes to long
	 * @param bytes: bytes array
	 * @return long
	 */
	public static long bytesToLong(byte[] bytes) {
		return bytesToLong(bytes, false);
	}

	/**
	 * bytes to hex
	 * @param bytes: bytes array
	 * @return String
	 */
	public static String bytesToHex(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		StringBuffer stringBuffer = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			stringBuffer.append(Character.forDigit((bytes[i] & 240) >> 4, 16));
			stringBuffer.append(Character.forDigit(bytes[i] & 15, 16));
		}

		return stringBuffer.toString();
	}

	/**
	 * array to string
	 * @param array: Object array
	 * @param componentType: array component type
	 * @return String
	 */
	public static String arrayToString(Object[] array, Class<?> componentType) {
		if (componentType == String.class) {
			return String.join(",", (String[]) array);
		}

		StringBuilder stringBuilder = new StringBuilder();

		for (Object object : array) {
			stringBuilder.append(object.toString());
			stringBuilder.append(",");
		}

		return stringBuilder.substring(0, stringBuilder.length() - 2);
	}

	/**
	 * int array to integer list
	 * @param array: int array
	 * @return ArrayList
	 */
	public static ArrayList<Integer> intArrayToIntegerList(int[] array) {
		if (array == null || array.length == 0) {
			return null;
		}

		ArrayList<Integer> list = new ArrayList<>(array.length);

		for (int i : array) {
			list.add(i);
		}

		return list;
	}

	/**
	 * list to int array
	 * @param <T>: Generic type
	 * @param list: Integer list
	 * @return int[]
	 * @throws InstantiationException
	 * @throws IllegalAccessException int[]
	 */
	public static <T> int[] listToIntArray(ArrayList<T> list) throws InstantiationException, IllegalAccessException {
		if (list == null || list.size() == 0 || !(list.get(0) instanceof Integer)) {
			return new int[0];
		}

		Integer[] integers = list.toArray(new Integer[0]);
		int[] array = new int[list.size()];
		int index = 0;

		for (index = 0; index < list.size(); index++) {
			array[index] = integers[index];
		}

		return array;
	}

	/**
	 * string to type
	 * @param <T>: Generic type
	 * @param value: string value
	 * @param clazz: target class
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T stringToType(String value, Class<T> clazz) {
		if (StringUtils.isNullOrEmpty(value)) {
			return null;
		}

		if (clazz == Byte.class || clazz == byte.class) {
			return (T) Byte.valueOf(value);
		}

		if (clazz == Short.class || clazz == short.class) {
			return (T) Short.valueOf(value);
		}

		if (clazz == Integer.class || clazz == int.class) {
			return (T) Integer.valueOf(value);
		}

		if (clazz == Long.class || clazz == long.class) {
			return (T) Long.valueOf(value);
		}

		if (clazz == Float.class || clazz == float.class) {
			return (T) Float.valueOf(value);
		}

		if (clazz == Double.class || clazz == double.class) {
			return (T) Double.valueOf(value);
		}

		if (clazz == Character.class || clazz == char.class) {
			return (T) Character.valueOf(value.toCharArray()[0]);
		}

		if (clazz == Boolean.class || clazz == boolean.class) {
			return (T) Boolean.valueOf(value);
		}

		if (clazz == String.class) {
			return (T) value;
		}

		if (clazz == Date.class) {
			long time = Long.parseLong(value);
			return (T) new Date(time);
		}

		Converter<String, T> converter = getConverter(String.class, clazz);

		if (converter != null) {
			return converter.convert(value);
		}

		Method convertorMethod = getConvertorMethod(String.class, clazz);

		if (convertorMethod != null) {
			return MethodUtils.<T>quietCall(convertorMethod, value);
		}

		return null;
	}

	/**
	 * string to list
	 * @param <T>: Generic type
	 * @param value: String value
	 * @param separator: separator for String value
	 * @param clazz: List generic class
	 * @return List
	 */
	public static <T> List<T> stringToList(String value, String separator, Class<T> clazz) {
		String[] array = value.split(separator);
		List<T> list = new ArrayList<>(array.length);

		for (String item : array) {
			list.add(ConverterUtils.stringToType(item, clazz));
		}

		return list;
	}

	/**
	 * string to int
	 * @param value: String value
	 * @return int
	 */
	public static int stringToInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * object to string
	 * @param value: Object value
	 * @return String
	 */
	public static String objectToString(Object value) {
		if (value == null) {
			return null;
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			return arrayToString((Object[]) value, clazz.getComponentType());
		}

		if (clazz == Date.class) {
			long time = ((Date) value).getTime();
			return String.valueOf(time);
		}

		return value.toString();
	}

	/**
	 * Convert Object value to long type.
	 * @param value Object value with unknow type.
	 * @return long
	 */
	public static long objectTolong(Object value) {
		String className = value.getClass().getName();

		switch (className) {
		case "java.lang.Short":
			return ((Short) value).longValue();

		case "java.lang.Integer":
			return ((Integer) value).longValue();

		case "java.lang.Float":
			return ((Float) value).longValue();

		case "java.lang.Double":
			return ((Double) value).longValue();

		case "java.lang.Long":
			return (Long) value;

		case "java.lang.String":
			return Long.parseLong((String) value);

		default:
			return 0;
		}
	}

	/**
	 * Convert Object value to int type.
	 * @param value Object value with unknow type.
	 * @return int
	 */
	public static int objectToint(Object value) {
		String className = value.getClass().getName();

		switch (className) {
		case "java.lang.Short":
			return ((Short) value).intValue();

		case "java.lang.Integer":
			return (Integer) value;

		case "java.lang.Float":
			return ((Float) value).intValue();

		case "java.lang.Double":
			return ((Double) value).intValue();

		case "java.lang.Long":
			return ((Long) value).intValue();

		case "java.lang.String":
			return Integer.parseInt((String) value);

		default:
			return 0;
		}
	}

	/**
	 * Convert S value to T value.
	 * @param value: S type value.
	 * @param converter: Convert.
	 * @return {@code T}
	 */
	public static <T, S> T convert(S value, Converter<S, T> converter) {
		return converter.convert(value);
	}

	/**
	 * Convert LocalDate To Date
	 * @param localDate: LocalDate object.
	 * @param zoneId: ZoneId object, default is ZoneId.systemDefault().
	 * @return Date
	 */
	public static Date localDateToDate(LocalDate localDate, ZoneId zoneId) {
		if (zoneId == null) {
			zoneId = ZoneId.systemDefault();
		}

		ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * Convert LocalDateTime to Date.
	 * @param localDateTime: LocalDateTime object.
	 * @param zoneId: ZoneId object, default is ZoneId.systemDefault().
	 * @return Date
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime, ZoneId zoneId) {
		if (zoneId == null) {
			zoneId = ZoneId.systemDefault();
		}

		ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
		return Date.from(zonedDateTime.toInstant());
	}
}
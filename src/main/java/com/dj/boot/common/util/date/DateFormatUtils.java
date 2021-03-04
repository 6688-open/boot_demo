package com.dj.boot.common.util.date;


import com.dj.boot.common.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DateFormatUtils {
	/**
	 * Container for SimpleDateFormat
	 */
	private static volatile ConcurrentMap<String, ThreadLocal<SimpleDateFormat>> container = new ConcurrentHashMap<>();

	/**
	 * Standard DateFormat
	 */
	public final static String STANDARDDATEFORMAT = "yyyy-MM-dd";

	/**
	 * Standard DateTime format
	 */
	public final static String STANDARDDATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Get or create a ThreadLocal SimpleDateFormat
	 * @param pattern: Pattern for SimpleDateFormat
	 * @param locale: Locale for SimpleDateFormat
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getSimpleDateFormat(String pattern, Locale locale) {
		if (pattern == null || locale == null) {
			return null;
		}

		String key = String.format("%s-%s", pattern, locale.toString());
		ThreadLocal<SimpleDateFormat> threadLocal = container.get(key);

		if (threadLocal != null && threadLocal.get() != null) {
			return threadLocal.get();
		}

		synchronized (DateFormatUtils.class) {
			if (container.get(key) == null || container.get(key).get() == null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
				threadLocal = new ThreadLocal<SimpleDateFormat>();
				threadLocal.set(simpleDateFormat);
				container.put(key, threadLocal);
			}
		}

		return threadLocal.get();
	}

	/**
	 * Get or create a ThreadLocal SimpleDateFormat
	 * @param pattern: Pattern for SimpleDateFormat
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getSimpleDateFormat(String pattern) {
		return getSimpleDateFormat(pattern, Locale.getDefault(Locale.Category.FORMAT));
	}

	public static String format(Date date, String pattern, Locale locale) {
		SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern, locale);

		if (simpleDateFormat == null) {
			return null;
		}

		return simpleDateFormat.format(date);
	}

	public static String format(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern);

		if (simpleDateFormat == null) {
			return null;
		}

		return simpleDateFormat.format(date);
	}

	public static Date parse(String value, String pattern, Locale locale) throws ParseException {
		if (StringUtils.isNullOrEmpty(value)) {
			return null;
		}

		SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern, locale);

		if (simpleDateFormat == null) {
			return null;
		}

		return simpleDateFormat.parse(value);
	}

	public static Date parse(String value, String pattern) throws ParseException {
		if (StringUtils.isNullOrEmpty(value)) {
			return null;
		}

		SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern);

		if (simpleDateFormat == null) {
			return null;
		}

		return simpleDateFormat.parse(value);
	}

	public static Date toDate(String value, String pattern, Locale locale) {
		try {
			return parse(value, pattern, locale);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date toDate(String value, String pattern) {
		try {
			return parse(value, pattern);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
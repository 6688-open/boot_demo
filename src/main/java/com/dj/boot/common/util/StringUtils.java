package com.dj.boot.common.util;

public class StringUtils {

	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}
	public static boolean isNullOrEmpty(String value) {
		return value == null || value.length() == 0 || value.trim().length() == 0;
	}

	public static boolean isNotEmpty(String value) {
		return !isNullOrEmpty(value);
	}

	public static String concat(String... str) {
		if (str.length == 0) {
			return null;
		}

		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < str.length; i++) {
			stringBuffer.append(str[i]);
		}

		return stringBuffer.toString();
	}

	public static String upperFirstCase(String value) {
		char[] array = value.toCharArray();

		if (array[0] >= 'a' && array[0] <= 'z') {
			array[0] = (char) (array[0] - 32);
		}

		return String.valueOf(array);
	}

	public static String trimEnd(String value, String tail) {
		if (value == null || tail == null) {
			return value;
		}

		int index = value.indexOf(tail);

		if (index == value.length() - tail.length()) {
			return value.substring(0, index);
		}

		return value;
	}
}
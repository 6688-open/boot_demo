package com.dj.boot.common.util.lang;


import com.dj.boot.common.util.base.SystemUtils;

import java.util.ArrayList;
import java.util.List;

public class ExceptionUtils {
	public static String toString(Exception exception) {
		return toString(exception, SystemUtils.lineSeparator());
	}

	public static String toString(Exception exception, String separator) {
		StringBuilder result = new StringBuilder();
		List<String> list = toList(exception);

		for (int i = 0; i < list.size(); i++) {
			result.append(list.get(i));

			if (i < list.size() - 1) {
				result.append(separator);
			}
		}

		return result.toString();
	}

	public static List<String> toList(Exception exception) {
		List<String> list = new ArrayList<>();

		if (exception == null) {
			return list;
		}

		list.add(String.format("%s: %s", exception.getClass().getName(), exception.getMessage()));
		StackTraceElement[] stackTraceElements = exception.getStackTrace();

		if (stackTraceElements == null || stackTraceElements.length == 0) {
			return list;
		}

		for (StackTraceElement stackTraceElement : stackTraceElements) {
			list.add(String.format("	at %s", stackTraceElement.toString()));
		}

		return list;
	}
}
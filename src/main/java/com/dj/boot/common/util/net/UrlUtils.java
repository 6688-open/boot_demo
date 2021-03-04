package com.dj.boot.common.util.net;

public class UrlUtils {
	public static String concat(String... paths) {
		StringBuilder stringBuilder = new StringBuilder();

		for (String path : paths) {
			stringBuilder.append(path);
			stringBuilder.append("/");
		}

		return stringBuilder.substring(0, stringBuilder.length() - 1);
	}
}
package com.dj.boot.common.util;

import java.util.UUID;

public class UUIDUtil {

	public static String code() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String code(String prefix) {
		return prefix + UUID.randomUUID().toString().replaceAll("-", "");
	}
}

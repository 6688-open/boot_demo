package com.dj.boot.common.util.codec;

import java.io.UnsupportedEncodingException;

public class Utf8Utils {
	/**
	 * Encode value use UTF-8
	 * @param value: String value.
	 * @return byte[]
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] encode(String value) throws UnsupportedEncodingException {
		if (value == null) {
			return null;
		}

		return value.getBytes("UTF-8");
	}

	/**
	 * Decode value use UTF-8
	 * @param bytes: Bytes array
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(byte[] bytes) throws UnsupportedEncodingException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		return new String(bytes, "UTF-8");
	}
}
package com.dj.boot.common.util.codec;

import com.dj.boot.common.util.codec.uri.UriHeader;
import com.dj.boot.common.util.codec.uri.UriUtils;
import com.dj.boot.common.util.nio.RandomAccessFileUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 
 * Base64Utils
 * 
 * @author: Aldwin Su
 * @version: 2019-04-10 15:50:53
 */
public class Base64Utils {
	/**
	 * Encode bytes array to base64 bytes.
	 * @param bytes bytes array.
	 * @return byte[]
	 */
	public static byte[] encode(byte[] bytes) {
		return Base64.getEncoder().encode(bytes);
	}

	/**
	 * Encode base64 bytes to string.
	 * @param bytes Base64 bytes.
	 * @return String
	 * @throws UnsupportedEncodingException When can't support UTF-8 coder.
	 */
	public static String encodeToString(byte[] bytes) throws UnsupportedEncodingException {
		byte[] base64Bytes = encode(bytes);
		return Utf8Utils.decode(base64Bytes);
	}

	/**
	 * Decode base64 bytes to normal bytes.
	 * @param bytes Base64 bytes array.
	 * @return byte[]
	 */
	public static byte[] decode(byte[] bytes) {
		return Base64.getDecoder().decode(bytes);
	}

	/**
	 * Decode base64 string to bytes array.
	 * @param value Base64 string value.
	 * @return {@code byte[]}
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] decode(String value) throws UnsupportedEncodingException {
		byte[] base64Bytes = Utf8Utils.encode(value);
		return decode(base64Bytes);
	}

	/**
	 * Decode base64 string and save into file.
	 * @param file File path.
	 * @param value String value.
	 * @throws IOException From RandomAccessFileUtils.
	 */
	public static void decodeToFile(String file, String value) throws IOException {
		byte[] bytes = Base64.getDecoder().decode(value);
		RandomAccessFileUtils.save(file, bytes);
	}

	/**
	 * Decode ToImage
	 * @param file
	 * @param value
	 * @throws IOException void
	 */
	public static void decodeToImage(String file, String value) throws IOException {
		UriHeader uriHeader = UriUtils.uriHeader(value);

		if (uriHeader == null) {
			throw new IllegalArgumentException("Invalid base64 header format!");
		}

		String base64 = value.substring(uriHeader.getHeader().length());
		decodeToFile(file, base64);
	}
}
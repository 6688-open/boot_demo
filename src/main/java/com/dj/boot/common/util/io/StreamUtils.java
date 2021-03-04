package com.dj.boot.common.util.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 
 * StreamUtils
 * 
 * @author: Aldwin Su
 */
public class StreamUtils {
	/**
	 * Read bytes array from InputStream object.
	 * @param inputStream InputStream object.
	 * @return byte[]
	 */
	public static byte[] read(InputStream inputStream) {
		int index = 0;
		byte[] bytes = new byte[1024];

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			while ((index = inputStream.read(bytes, 0, bytes.length)) > 0) {
				byteArrayOutputStream.write(bytes, 0, index);
			}

			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
}
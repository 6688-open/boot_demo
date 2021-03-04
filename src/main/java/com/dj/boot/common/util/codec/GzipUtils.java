package com.dj.boot.common.util.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 
 * GzipUtils
 * 
 * @author: Aldwin Su
 * @version: 2019-07-07 15:18:59
 */
public class GzipUtils {
	/**
	 * Compressing {@code byte[]} data using Gzip algorithm.
	 * @param bytes {@code byte[]} need to be compressed.
	 * @return Compressed {@code byte[]} data.
	 * @throws IOException from ByteArrayOutputStream or GZIPOutputStream.
	 */
	public static byte[] compress(byte[] bytes) throws IOException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		byte[] compressedBytes;

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
				gzipOutputStream.write(bytes);
			} catch (IOException e) {
				throw e;
			}

			compressedBytes = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw e;
		}

		return compressedBytes;
	}

	/**
	 * Decompress {@code byte[]} data using Gzip algorithm.
	 * @param bytes {@code byte[]} need to be decompressed.
	 * @return Decompressed {@code byte[]} data.
	 * @throws IOException from ByteArrayOutputStream, ByteArrayInputStream or GZIPOutputStream.
	 */
	public static byte[] decompress(byte[] bytes) throws IOException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		byte[] decompressedBytes;

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
				try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
					int readCount = 0;
					byte[] buffer = new byte[1024];

					while ((readCount = gzipInputStream.read(buffer)) >= 0) {
						byteArrayOutputStream.write(buffer, 0, readCount);
					}
				} catch (IOException e) {
					throw e;
				}
			} catch (IOException e) {
				throw e;
			}

			decompressedBytes = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw e;
		}

		return decompressedBytes;
	}
}
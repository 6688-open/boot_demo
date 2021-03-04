package com.dj.boot.common.util.nio.channels;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * FileChannelUtils
 * 
 * @author: Aldwin Su
 */
public class FileChannelUtils {
	/**
	 * Write bytes toFile
	 * @param bytes bytes array.
	 * @param file Target file path.
	 * @throws IOException
	 */
	public static void write(byte[] bytes, String file) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(file); FileChannel fileChannel = fileOutputStream.getChannel()) {
			ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
			fileChannel.write(byteBuffer);
		} catch (IOException e) {
			throw e;
		}
	}
}
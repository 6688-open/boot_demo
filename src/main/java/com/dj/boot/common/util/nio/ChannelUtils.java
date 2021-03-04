package com.dj.boot.common.util.nio;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 * 
 * ChannelUtils
 * 
 * @author: Aldwin Su
 */
public class ChannelUtils {
	/**
	 * Read bytes from ReadableByteChannel object.
	 * @param channel ReadableByteChannel object.
	 * @param bufferSize Buffer size.
	 * @return byte[]
	 */
	public static byte[] read(ReadableByteChannel channel, int bufferSize) {
		int readBytesCount = 0;
		ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			while ((readBytesCount = channel.read(byteBuffer)) > 0) {
				byteBuffer.flip();

				if (byteBuffer.hasArray()) {
					byteArrayOutputStream.write(byteBuffer.array(), 0, readBytesCount);
				}

				byteBuffer.clear();
			}

			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
}
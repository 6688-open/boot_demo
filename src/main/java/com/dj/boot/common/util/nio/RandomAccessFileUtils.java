package com.dj.boot.common.util.nio;

import com.dj.boot.common.util.io.FileUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 
 * RandomAccessFileUtils
 * 
 * @author: Aldwin Su
 * @version: 2019-04-10 17:00:01
 */
public class RandomAccessFileUtils {
	/**
	 * Insert bytes in file from startIndex position.
	 * @param file File path.
	 * @param start Start index in file bytes array.
	 * @param bytes bytes array need to be written in file.
	 * @throws IOException When invalid startIndex or RandomAccessFile IOException take place.
	 */
	public static void insert(String file, long start, byte[] bytes) throws IOException {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
			long fileLength = randomAccessFile.length();

			if (start < 0 || start >= fileLength) {
				throw new IOException("Invalid startIndex!");
			}

			long bytesLength = bytes.length;
			randomAccessFile.setLength(fileLength + bytesLength);

			for (long i = fileLength - 1; i > start - 1; i--) {
				randomAccessFile.seek(i);
				byte temp = randomAccessFile.readByte();
				randomAccessFile.seek(i + bytesLength);
				randomAccessFile.write(temp);
			}

			randomAccessFile.seek(start);
			randomAccessFile.write(bytes);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Append bytes array at the end of file.
	 * @param file File path.
	 * @param bytes bytes array.
	 * @throws IOException When RandomAccessFile IOException take place.
	 */
	public static void append(String file, byte[] bytes) throws IOException {
		append(file, ByteBuffer.wrap(bytes));
	}

	/**
	 * Append ByteBuffer object at the end of file.
	 * @param file File path.
	 * @param byteBuffer ByteBuffer object.
	 * @throws IOException When RandomAccessFile IOException take place.
	 */
	public static void append(String file, ByteBuffer byteBuffer) throws IOException {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw"); FileChannel fileChannel = randomAccessFile.getChannel()) {
			fileChannel.position(randomAccessFile.length());
			fileChannel.write(byteBuffer);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Asynchronous append ByteBuffer object in file.
	 * @param path Path object.
	 * @param byteBuffer ByteBuffer object.
	 * @throws IOException From AsynchronousFileChannel.
	 */
	public static void asyncAppend(Path path, ByteBuffer byteBuffer) throws IOException {
		long length = FileUtils.getBytesLength(path);

		try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
			asynchronousFileChannel.write(byteBuffer, length);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Asynchronous append ByteBuffer object in file.
	 * @param file File path.
	 * @param byteBuffer ByteBuffer object.
	 * @throws IOException From AsynchronousFileChannel.
	 */
	public static void asyncAppend(String file, ByteBuffer byteBuffer) throws IOException {
		Path path = Paths.get(file);
		asyncAppend(path, byteBuffer);
	}

	/**
	 * Asynchronous append ByteBuffer object in file.
	 * @param file File path.
	 * @param byteBuffer ByteBuffer object.
	 * @param handler The handler for consuming the IO operation result.
	 * @throws IOException From AsynchronousFileChannel.
	 */
	public static void asyncAppend(String file, ByteBuffer byteBuffer, CompletionHandler<Integer, ByteBuffer> handler) throws IOException {
		Path path = Paths.get(file);
		long length = FileUtils.getBytesLength(file);

		try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE)) {
			asynchronousFileChannel.write(byteBuffer, length, byteBuffer, handler);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Save ByteBuffer object into file.
	 * @param file File path.
	 * @param byteBuffer ByteBuffer object.
	 * @throws IOException From RandomAccessFile or FileChannel.
	 */
	public static void save(String file, ByteBuffer byteBuffer) throws IOException {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw"); FileChannel fileChannel = randomAccessFile.getChannel()) {
			fileChannel.write(byteBuffer);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Save bytes into file.
	 * @param file File path.
	 * @param bytes bytes array.
	 * @throws IOException From save method.
	 */
	public static void save(String file, byte[] bytes) throws IOException {
		save(file, ByteBuffer.wrap(bytes));
	}
}
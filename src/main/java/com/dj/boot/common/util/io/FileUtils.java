package com.dj.boot.common.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

/**
 * 
 * FileUtils
 * 
 * @author: Aldwin Su
 */
public class FileUtils {
	public static boolean exists(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	public static long getBytesLength(File file) {
		if (!file.exists()) {
			return 0;
		}

		return file.length();
	}

	public static long getBytesLength(Path path) {
		File file = path.toFile();
		return getBytesLength(file);
	}

	public static long getBytesLength(String fileName) {
		File file = new File(fileName);
		return getBytesLength(file);
	}

	public static String getHourLogFileName(int hour) {
		return String.format("%d.log", hour);
	}

	/**
	 * Get file extension name.
	 * @param fileName File name or file path.
	 * @return String
	 */
	public static String getExtension(String fileName) {
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index);
	}

	/**
	 * Create an new File object.
	 * @param filePath File path.
	 * @return File File object.
	 * @throws FileNotFoundException When file not found.
	 */
	public static File create(String filePath) throws FileNotFoundException {
		File file = new File(filePath);

		if (!file.exists()) {
			throw new FileNotFoundException(String.format("File %s not found!", filePath));
		}

		return file;
	}
}
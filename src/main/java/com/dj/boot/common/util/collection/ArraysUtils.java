package com.dj.boot.common.util.collection;

import java.util.LinkedList;
import java.util.List;

public class ArraysUtils {
	/**
	 * If the arrays is null or empty.
	 * @param arrays: String arrays.
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String[][] arrays) {
		return arrays == null || arrays.length == 0;
	}

	/**
	 * Page array
	 * @param arrays: Arrays
	 * @param pageIndex: Page index, from 1
	 * @param pageSize: Page size
	 * @return String[][]
	 */
	public static String[][] pageArray(String[][] arrays, int pageIndex, int pageSize) {
		if (isNullOrEmpty(arrays) || pageIndex < 1 || pageSize < 1) {
			return new String[0][];
		}

		List<String[]> list = new LinkedList<>();
		int start = (pageIndex - 1) * pageSize;
		int end = Math.min(start + pageSize, arrays.length);

		for (int index = start; index < end; index++) {
			list.add(arrays[index]);
		}

		return list.toArray(new String[0][]);
	}
}
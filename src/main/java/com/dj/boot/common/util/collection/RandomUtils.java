package com.dj.boot.common.util.collection;

import java.util.Random;

public class RandomUtils {
	/**
	 * Random instance
	 */
	public static final Random RANDOM = new Random();

	/**
	 * Chars for generate random string.
	 */
	public static final char[] CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * Get random string
	 * @param length Chars length
	 * @return String
	 */
	public static String getString(int length) {
		return getString(CHARS, length);
	}

	/**
	 * Get random string
	 * @param chars Chars pool which will generate random string.
	 * @param length Chars length
	 * @return String
	 */
	public static String getString(char[] chars, int length) {
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < length; i++) {
			buffer.append(chars[RANDOM.nextInt(chars.length)]);
		}

		return buffer.toString();
	}

	/**
	 * Get random number
	 * @param bound Scope for random number.
	 * @return int
	 */
	public static int getNumber(int bound) {
		return RANDOM.nextInt(bound);
	}

	/**
	 * Get a int number between lower and upper.
	 * @param lower Range lower (inclusive).
	 * @param upper Range upper (inclusive).
	 * @return int
	 */
	public static int getNumber(int lower, int upper) {
		int bound = upper - lower + 1;
		return RANDOM.nextInt(bound) + lower;
	}
}
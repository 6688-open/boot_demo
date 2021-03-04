package com.dj.boot.common.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {
	public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
		return value1.add(value2);
	}

	public static BigDecimal add(double value1, double value2) {
		return add(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal add(float value1, float value2) {
		return add(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal add(float value1, double value2) {
		return add(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal add(int value1, double value2) {
		return add(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal add(int value1, float value2) {
		return add(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}
	public static BigDecimal add(int value1, int value2) {
		return add(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal sub(BigDecimal value1, BigDecimal value2) {
		return value1.subtract(value2);
	}

	public static BigDecimal sub(Double value1, Double value2) {
		return sub(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal sub(float value1, float value2) {
		return sub(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal sub(Double value1, float value2) {
		return sub(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal sub(float value1, Double value2) {
		return sub(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal sub(int value1, float value2) {
		return sub(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal sub(float value1, int value2) {
		return sub(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal sub(int value1, Double value2) {
		return sub(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal sub(Double value1, int value2) {
		return sub(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal mul(BigDecimal value1, BigDecimal value2) {
		return value1.multiply(value2);
	}

	public static BigDecimal mul(double value1, double value2) {
		return mul(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal mul(float value1, float value2) {
		return mul(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal mul(int value1, int value2) {
		return mul(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal mul(double value1, float value2) {
		return mul(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal mul(double value1, int value2) {
		return mul(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal mul(float value1, int value2) {
		return mul(BigDecimal.valueOf(value1), BigDecimal.valueOf(value2));
	}

	public static BigDecimal div(double value1, double value2, int scale, RoundingMode mode) throws IllegalAccessException {
		if (scale < 0) {
			throw new IllegalAccessException("scale cannot less than 0!");
		}

		return BigDecimal.valueOf(value1).divide(BigDecimal.valueOf(value2), scale, mode);
	}
}
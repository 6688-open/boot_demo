package com.dj.boot.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * PatternCheckUtil正则验证工具类
 *
 * @author: Lenovo
 * @date: 2019-04-11 17:51:00
 * @Copyright: 2019 www.lenovo.com Inc. All rights reserved.
 */
public class PatternCheckUtil {


	/**
	 * 校验年月日
	 */
	public  static  final String REGEX_YEAR_MONTH_DAY="^(^(\\d{4}|\\d{2})(\\-|\\/|\\.)\\d{1,2}\\3\\d{1,2}$)|(^\\d{4}年\\d{1,2}月\\d{1,2}日$)$";
	/***
	 *校验电话补充
	 *
	 */
	 public  static  final String REGEX_PHONE_NUMBER="^(\\d{3,4}-)|(\\d{3,4}-)+\\d{7,8}||[1-9][0-9]{8,11}$";

	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 正则表达式：固话及手机
	 */
	public static final String REGEX_PHOME_NUMBER = "^(\\+\\d{2}-)?(\\d{2,3}-)?([1][3,4,5,6,7,8,9][0-9]\\d{8})|\\d{3}-\\d{8,20}|\\d{4}-\\d{8,20}|\\d{8,20}";
	/**
	 * 正则表达式：验证正整数
	 */
	public static final String REGEX_POSITIVE_INTEGER="^[1-9]\\d*$";
	/**
	 *校验数字  整数  小数  负数  正数
	 */
	public static final String IS_NUMBER = "^(\\-|\\+)?\\d+(\\.\\d+)?$";
	/**
	 * 大于0的数  正数  包含小数
	 */
	public static final String IS_POSITIVE_NUMBER = "^([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])$";





	/**
	 * 校验年月日
	 */
	public static boolean isYearMonthDay(String positiveInt) {
		return Pattern.matches(REGEX_YEAR_MONTH_DAY, positiveInt);
	}
	/**
	 * 校验正整数
	 *
	 * @param positiveInt
	 * @return
	 */
	public static boolean isPositiveInt(String positiveInt) {
		return Pattern.matches(REGEX_POSITIVE_INTEGER, positiveInt);
	}

	/**
	 *   整数  小数  负数  正数
	 */
	public static boolean isNumeric(String str) {
		return Pattern.matches(IS_NUMBER, str);
	}
	/**
	 * 大于0的数  正数  包含小数
	 */
	public static boolean isPositiveNumber(String str) {
		return Pattern.matches(IS_POSITIVE_NUMBER, str);
	}

	/**
	 * 校验邮箱
	 *
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 校验固话及手机
	 *
	 * @param phoneNumber
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPhoneNumber(String phoneNumber) {
		return Pattern.matches(REGEX_PHOME_NUMBER, phoneNumber);
	}
	/**
	 * 校验固话及手机
	 *
	 * @param phoneNumber
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPhoneNumberTwo(String phoneNumber) {
		return Pattern.matches(REGEX_PHONE_NUMBER, phoneNumber);
	}


	 public static void main(String[] args) {
		//boolean bo= isEmail("aa@qq..111com");
		 //boolean numeric = isNumeric("0.2t342345");
		 boolean positiveNumber = isPositiveNumber("0");

		 System.out.println(222);

	}
}
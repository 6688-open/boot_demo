package com.dj.boot.common.util.md5;

import java.security.MessageDigest;
import java.util.Random;

/**
 * 密码安全工具类
 * 
 * @author turingdj
 *
 */
public class PasswordSecurityUtil {

	private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	/**
	 * MD5算法
	 * 
	 * @param bytes
	 * @return
	 */
	private static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		int t;
		for (int i = 0; i < 16; i++) {// 16 == bytes.length;
			t = bytes[i];
			if (t < 0)
				t += 256;
			sb.append(hexDigits[(t >>> 4)]);
			sb.append(hexDigits[(t % 16)]);
		}
		return sb.toString();
	}

	/**
	 * 32位MD5
	 * 
	 * @param str
	 *            待加密字符串
	 * @return
	 * @throws Exception
	 */
	public static String enCode32(String str) throws Exception {
		MessageDigest md = MessageDigest.getInstance(System.getProperty("MD5.algorithm", "MD5"));
		return bytesToHex(md.digest(str.getBytes("utf-8")));
	}

	/**
	 * 16位MD5
	 * 
	 * @param str
	 *            待加密字符串
	 * @return
	 * @throws Exception
	 */
	public static String enCode16(String str) throws Exception {
		MessageDigest md = MessageDigest.getInstance(System.getProperty("MD5.algorithm", "MD5"));
		return bytesToHex(md.digest(str.getBytes("utf-8"))).substring(8, 24);
	}

	/**
	 * 生成密码盐 （时间毫秒值+随机数[6位]）
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String generateSalt() throws Exception {
		// 时间戳+随机数
		return enCode32(System.currentTimeMillis() + generateRandom(6));
	}

	/**
	 * 生成随机数字符串（0-9）
	 * 
	 * @param index
	 *            长度
	 * @return
	 */
	public static String generateRandom(int index) {
		Random random = new Random();
		StringBuffer randomStr = new StringBuffer();
		for (int i = 0; i < index; i++) {
			randomStr.append(random.nextInt(10));
		}
		return randomStr.toString();
	}

	/**
	 * 生成密码 规则：md5（md5密码 + slat）
	 * 
	 * @param passwordMd5Str
	 * @return
	 * @throws Exception
	 */
	public static String generatePassword(String passwordMd5Str, String salt) throws Exception {
		return enCode32(passwordMd5Str + salt);
	}

	/**
	 * 检查密码是否合法
	 * 
	 * @param password
	 *            待检测密码
	 * @param checkPassword
	 *            数据库存入的密码
	 * @param slat
	 *            盐值
	 * @return
	 * @throws Exception
	 */
	public static Boolean checkPassword(String password, String checkPassword, String slat) throws Exception {
		boolean flag = false;
		if (checkPassword.equals(enCode32(password + slat))) {
			flag = true;
		}
		return flag;
	}
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		//1515206640731
		//1515206651144
	}
}

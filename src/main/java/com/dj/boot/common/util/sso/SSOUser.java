package com.dj.boot.common.util.sso;

import java.io.Serializable;

public class SSOUser implements Serializable {
	// 当前登录用户ID
	private String userId;
	// 当前登录用户口令（加密保存）
	private String encryptedPwd;
	// 当前登录用户通过Cookie形式保存的Token
	private String dominoToken;
	// 是否已登录
	private boolean isLogin;

	/**
	 * 
	 */
	public SSOUser() {
		this.userId = null;
		this.encryptedPwd = null;
		this.dominoToken = null;
		this.isLogin = false;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDominoToken() {
		return dominoToken;
	}

	public void setDominoToken(String dominoToken) {
		this.dominoToken = dominoToken;
	}

	/**
	 * @return the isLogin
	 */
	public boolean isLogin() {
		return isLogin;
	}

	/**
	 * @param isLogin
	 *            the isLogin to set
	 */
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * @return the decrypted password 将口令解密并返回
	 */
	public String getDecryptedPwd() {
		if (encryptedPwd == null) {
			return null;
		}

		String value;
		char[] A1 = new char[100];
		char[] fname1 = new char[100];
		char[] A2 = new char[100];
		int t2[] = { 5, 14, 12, 9, 11, 2, 0, 15, 7, 3, 4, 13, 1, 6, 10, 8 };
		char t3[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };

		int i = 0, j = 0, k, p, q;
		int key = 32;
		int temp;
		fname1 = encryptedPwd.toCharArray();
		k = encryptedPwd.length();
		while ((i < k) && (fname1[i] != '\0')) {
			A1[j] = fname1[i];
			p = 0;
			while ((p < 16) && (A1[j] != t3[p++]))
				;
			A2[j] = fname1[i + 1];
			q = 0;
			while ((q < 16) && (A2[j] != t3[q++]))
				;
			A1[j] = (char) (t2[p - 1] * 16 + t2[q - 1]);
			temp = (int) A1[j];
			temp = temp ^ key;
			A1[j] = (char) temp;
			i = i + 2;
			j++;
		}
		A1[j] = 0;
		value = String.valueOf(A1).trim();
		return value;
	}

	/**
	 * @param encryptedPwd
	 *            the encrypted password to set
	 */
	public void setEncryptedPwd(String encryptedPwd) {
		this.encryptedPwd = encryptedPwd;
	}
}

package com.dj.boot.common.util.security;


import com.dj.boot.common.util.codec.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class EccUtils {
	/**
	 * Charset name
	 */
	private final static String CHARSET = "UTF-8";

	/**
	 * algorithm the name of the requested key algorithm.
	 */
	private final static String ALGORITHM = "EC";

	/**
	 * provider the name of the provider.
	 */
	private final static String KEYPROVIDER = "SunEC";

	/**
	 * the standard name of the to-be-generated EC
	 */
	private final static String STANDARDNAMEFOREC = "secp384r1";

	/**
	 * Get public key string for ECC encryption.
	 * @param publicKey PublicKey object.
	 * @return String
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 */
	public static String getPublicKey(PublicKey publicKey) throws GeneralSecurityException, UnsupportedEncodingException {
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, KEYPROVIDER);
		X509EncodedKeySpec encodedKeySpec = keyFactory.getKeySpec(publicKey, X509EncodedKeySpec.class);
		return Base64Utils.encodeToString(encodedKeySpec.getEncoded());
	}

	/**
	 * Get public key string for ECC encryption.
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @throws GeneralSecurityException
	 */
	public static String getPublicKey() throws UnsupportedEncodingException, GeneralSecurityException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, KEYPROVIDER);
		ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec(STANDARDNAMEFOREC);
		keyPairGenerator.initialize(ecGenParameterSpec);
		KeyPair keyPair = keyPairGenerator.genKeyPair();
		return getPublicKey(keyPair.getPublic());
	}

	public static String getPrivateKey(String key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, UnsupportedEncodingException {
		byte[] keyBytes = key.getBytes(Charset.forName(CHARSET));
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		return Base64Utils.encodeToString(privateKey.getEncoded());
	}
}
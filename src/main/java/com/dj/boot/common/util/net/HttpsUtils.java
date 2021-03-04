package com.dj.boot.common.util.net;


import com.dj.boot.common.util.codec.Utf8Utils;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class HttpsUtils extends BaseHttpUtils {
	/**
	 * Get data from Url.
	 * @param url Request Url.
	 * @param header Key value in request header.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static HttpResponse get(String url, Map<String, String> header, String protocol, int timeout) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		URL urlObj = new URL(url);
		HttpsURLConnection httpsURLConnection = httpsURLConnection(urlObj, null, header, null, sslSocketFactory(protocol), timeout);
		return httpResponse(httpsURLConnection);
	}

	/**
	 * Get data from Url.
	 * @param url Request Url.
	 * @param contentType Content-Type in http request header.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public static HttpResponse get(String url, String contentType, String protocol, int timeout) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", contentType);
		return get(url, header, protocol, timeout);
	}

	/**
	 * Get data from Url.
	 * @param url Request Url.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return String
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public static String get(String url, String protocol, int timeout) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		HttpResponse httpResponse = get(url, "", protocol, timeout);
		return httpResponse.getResponse();
	}

	/**
	 * Get data from Url.
	 * @param url Request Url.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @return String
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String get(String url, String protocol) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return get(url, protocol, 0);
	}

	/**
	 * Get Json data from Url.
	 * @param url: Request Url.
	 * @param protocol: Protocol for SSLSocketFactory, SSL or TLS.
	 * @return HttpResponse
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static HttpResponse getJson(String url, String protocol) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		return getJson(url, protocol, 0);
	}

	/**
	 * Get Json data from Url.
	 * @param url Request Url.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public static HttpResponse getJson(String url, String protocol, int timeout) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		return get(url, "application/json", protocol, timeout);
	}

	/**
	 * Send a post request to provided url.
	 * @param url Request Url.
	 * @param header Key value in request header.
	 * @param data Request data while post method.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static HttpResponse post(String url, Map<String, String> header, byte[] data, String protocol, int timeout) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		URL urlObj = new URL(url);
		HttpsURLConnection httpsURLConnection = httpsURLConnection(urlObj, "POST", header, data, sslSocketFactory(protocol), timeout);
		send(httpsURLConnection, data);
		return httpResponse(httpsURLConnection);
	}

	/**
	 * Send a post request to provided url.
	 * @param url Request Url.
	 * @param contentType Content type in request header.
	 * @param data Request data while post method.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static HttpResponse post(String url, String contentType, byte[] data, String protocol, int timeout) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", contentType);
		return post(url, header, data, protocol, timeout);
	}

	/**
	 * Send a post request to provided url.
	 * @param url Request Url.
	 * @param contentType Content type in request header.
	 * @param data Request data while post method.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static HttpResponse post(String url, String contentType, String data, String protocol, int timeout) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
		return post(url, contentType, Utf8Utils.encode(data), protocol, timeout);
	}

	/**
	 * Post form data to Url.
	 * @param url Request Url.
	 * @param data Form data.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static HttpResponse postForm(String url, String data, String protocol, int timeout) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
		return post(url, "application/x-www-form-urlencoded", Utf8Utils.encode(data), protocol, timeout);
	}

	/**
	 * Post form data to Url.
	 * @param url Request Url.
	 * @param data Form data.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @return HttpResponse
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static HttpResponse postForm(String url, String data, String protocol) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
		return postForm(url, data, protocol, 0);
	}

	/**
	 * Post Json data to Url.
	 * @param url Request Url.
	 * @param data Json data.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static HttpResponse postJson(String url, String data, String protocol, int timeout) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
		return post(url, "application/json", data, protocol, timeout);
	}

	/**
	 * Post Json data to Url.
	 * @param url Request Url.
	 * @param data Json data.
	 * @param protocol Protocol for SSLSocketFactory, SSL or TLS.
	 * @return HttpResponse
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static HttpResponse postJson(String url, String data, String protocol) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
		return postJson(url, data, protocol, 0);
	}

	/**
	 * Create an HttpsURLConnection object.
	 * @param url Request Url.
	 * @param method Request method GET or POST, default is GET.
	 * @param header Key value in request header.
	 * @param data Request data while post method.
	 * @param sslSocketFactory: SSLSocketFactory object.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpsURLConnection
	 * @throws IOException
	 */
	public static HttpsURLConnection httpsURLConnection(URL url, String method, Map<String, String> header, byte[] data, SSLSocketFactory sslSocketFactory, int timeout) throws IOException {
		HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
		setHttpURLConnection(httpsURLConnection, method, header, data, timeout);
		httpsURLConnection.setSSLSocketFactory(sslSocketFactory);

		httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		});

		return httpsURLConnection;
	}

	/**
	 * Create an SslSocketFactory object.
	 * @param protocol: Protocol for SSLSocketFactory, SSL or TLS.
	 * @return SSLSocketFactory
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLSocketFactory sslSocketFactory(String protocol) throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance(protocol);
		sslContext.init(new KeyManager[0], DefaultTrustManager.trustManagers(), null);
		return sslContext.getSocketFactory();
	}
}
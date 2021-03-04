package com.dj.boot.common.util.net;


import com.dj.boot.common.util.codec.Utf8Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils extends BaseHttpUtils {
	/**
	 * Get data from url.
	 * @param url Request Url.
	 * @param header Key value list in request.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse get(String url, Map<String, String> header, int timeout) throws IOException {
		HttpURLConnection httpURLConnection = httpURLConnection(url, "GET", header, null, timeout);
		return httpResponse(httpURLConnection);
	}

	/**
	 * Get data from url.
	 * @param url Request Url.
	 * @param contentType Content-Type in http request header.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse get(String url, String contentType, int timeout) throws IOException {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", contentType);
		return get(url, header, timeout);
	}

	/**
	 * Get data from url.
	 * @param url Request Url.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse get(String url, int timeout) throws IOException {
		return get(url, "", timeout);
	}

	/**
	 * Get data from url.
	 * @param url Request Url.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse get(String url) throws IOException {
		return get(url, 0);
	}

	/**
	 * Get Json data from url.
	 * @param url Request Url.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse getJson(String url, int timeout) throws IOException {
		return get(url, "application/json", timeout);
	}

	/**
	 * Get Json data from url.
	 * @param url Request Url.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse getJson(String url) throws IOException {
		return getJson(url, 0);
	}

	/**
	 * Post data to Url.
	 * @param url Request Url.
	 * @param header Key value in request.
	 * @param data Request data in byte array.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse post(String url, Map<String, String> header, byte[] data, int timeout) throws IOException {
		HttpURLConnection httpURLConnection = httpURLConnection(url, "POST", header, data, timeout);
		send(httpURLConnection, data);
		return httpResponse(httpURLConnection);
	}

	/**
	 * Post data to Url.
	 * @param url Request Url.
	 * @param contentType Content-Type in http request header.
	 * @param data Request data.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse post(String url, String contentType, byte[] data, int timeout) throws IOException {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", contentType);
		return post(url, header, data, timeout);
	}

	/**
	 * Post data to Url.
	 * @param url Request Url.
	 * @param contentType Content-Type in http request header.
	 * @param data Request data.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static HttpResponse post(String url, String contentType, String data, int timeout) throws UnsupportedEncodingException, IOException {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", contentType);
		return post(url, header, Utf8Utils.encode(data), timeout);
	}

	/**
	 * Post form data to Url.
	 * @param url Request Url.
	 * @param data Form data
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse postForm(String url, String data, int timeout) throws IOException {
		return post(url, "application/x-www-form-urlencoded", data, timeout);
	}

	/**
	 * Post form data to Url.
	 * @param url Request Url.
	 * @param data Form data.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse postForm(String url, String data) throws IOException {
		return postForm(url, data, 0);
	}

	/**
	 * Post Json data to Url.
	 * @param url Request Url.
	 * @param data Json data.
	 * @param timeout Request timeout.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse postJson(String url, String data, int timeout) throws IOException {
		return post(url, "application/json", data, timeout);
	}

	/**
	 * Post Json data to Url.
	 * @param url: Request Url.
	 * @param data: Json data.
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse postJson(String url, String data) throws IOException {
		return postJson(url, data, 0);
	}

	/**
	 * Create a HttpURLConnection instance.
	 * @param url Request Url.
	 * @param method Request method, GET or POST.
	 * @param header Key value list in request.
	 * @param data Request data while method is "POST".
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @return HttpURLConnection
	 * @throws IOException
	 */
	public static HttpURLConnection httpURLConnection(String url, String method, Map<String, String> header, byte[] data, int timeout) throws IOException {
		URL urlObj = new URL(url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) urlObj.openConnection();
		setHttpURLConnection(httpURLConnection, method, header, data, timeout);
		return httpURLConnection;
	}
}
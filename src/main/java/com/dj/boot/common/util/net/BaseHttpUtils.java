package com.dj.boot.common.util.net;


import com.dj.boot.common.util.StringUtils;
import com.dj.boot.common.util.codec.Utf8Utils;
import com.dj.boot.common.util.io.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseHttpUtils {
	/**
	 * Send request data through HttpURLConnection object.
	 * @param httpURLConnection HttpURLConnection object.
	 * @param data Data in byte array.
	 * @throws IOException
	 */
	protected static void send(HttpURLConnection httpURLConnection, byte[] data) throws IOException {
		if (httpURLConnection == null || data == null) {
			throw new IllegalArgumentException("httpURLConnection and data cannot be null!");
		}

		try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
			outputStream.write(data);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Set HttpURLConnection object.
	 * @param httpURLConnection HttpURLConnection object.
	 * @param method Request method GET or POST, default is GET.
	 * @param header Key value in request header.
	 * @param data Request data while post method.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @throws ProtocolException If the method cannot be reset or if the requested method isn't valid for HTTP.
	 */
	protected static void setHttpURLConnection(HttpURLConnection httpURLConnection, String method, Map<String, String> header, byte[] data, int timeout) throws ProtocolException {
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setRequestProperty("charset", "utf-8");

		if (StringUtils.isNullOrEmpty(method)) {
			method = "GET";
		}

		httpURLConnection.setRequestMethod(method);

		if (header != null && header.size() > 0) {
			for (Map.Entry<String, String> item : header.entrySet()) {
				httpURLConnection.setRequestProperty(item.getKey(), item.getValue());
			}
		}

		if (method.equals("POST") && data != null) {
			httpURLConnection.setRequestProperty("Content-length", String.valueOf(data.length));
		}

		if (timeout > 0) {
			httpURLConnection.setConnectTimeout(timeout);
			httpURLConnection.setReadTimeout(timeout);
		}
	}

	/**
	 * Set HttpURLConnection object.
	 * @param httpURLConnection HttpURLConnection object.
	 * @param method Request method GET or POST, default is GET.
	 * @param contentType Content type in request header.
	 * @param data Request data while post method.
	 * @param timeout Timeout in milliseconds, a timeout of zero is interpreted as an infinite timeout.
	 * @throws ProtocolException If the method cannot be reset or if the requested method isn't valid for HTTP.
	 */
	protected static void setHttpURLConnection(HttpURLConnection httpURLConnection, String method, String contentType, byte[] data, int timeout) throws ProtocolException {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", contentType);
		setHttpURLConnection(httpURLConnection, method, header, data, timeout);
	}

	/**
	 * Get a HttpResponse object with provided HttpURLConnection object.
	 * @param httpURLConnection HttpURLConnection object.
	 * @return HttpResponse HttpResponse object.
	 * @throws IOException
	 */
	protected static HttpResponse httpResponse(HttpURLConnection httpURLConnection) throws IOException {
		if (httpURLConnection == null) {
			return null;
		}

		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setCode(httpURLConnection.getResponseCode());
		httpResponse.setMessage(httpURLConnection.getResponseMessage());

		byte[] bytes = null;
		InputStream inputStream = null;

		try {
			if (httpResponse.getCode() >= 400) {
				inputStream = httpURLConnection.getErrorStream();
			} else {
				inputStream = httpURLConnection.getInputStream();
			}

			bytes = StreamUtils.read(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}

		if (bytes != null) {
			httpResponse.setBytes(bytes);
			httpResponse.setResponse(Utf8Utils.decode(bytes));
		}

		return httpResponse;
	}
}
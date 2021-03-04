package com.dj.boot.common.util.codec.uri;

/**
 * 
 * The object contains header information of base64 string.
 * 
 * @author: Aldwin Su
 * @version: 2019-04-10 15:50:53
 */
public class UriHeader {
	private String header;
	private String mimeType;
	private String charset;
	private String encoding;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
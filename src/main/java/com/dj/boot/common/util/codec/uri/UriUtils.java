package com.dj.boot.common.util.codec.uri;

import com.dj.boot.common.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * DataURI utils.
 * 
 * @author: Aldwin Su
 * @version: 2019-04-10 15:55:24
 */
public class UriUtils {
	/**
	 * Regular expression for data URI scheme header.
	 */
	public final static String REGEXP_DATAURISCHEME_HEADER = "data:(\\w+/\\w+)?(;charset=\\w+;)?(;\\w+)?,";

	/**
	 * Match the string value with DataURI format and wrap it in UriHeader object.
	 * @param value DataURI string value.
	 * @return UriHeader
	 */
	public static UriHeader uriHeader(String value) {
		Pattern pattern = Pattern.compile(REGEXP_DATAURISCHEME_HEADER);
		Matcher matcher = pattern.matcher(value);

		if (!matcher.find() || matcher.groupCount() != 3) {
			return null;
		}

		UriHeader header = new UriHeader();
		header.setHeader(matcher.group(0));
		header.setMimeType(matcher.group(1));
		header.setCharset(matcher.group(2));
		header.setEncoding(matcher.group(3));

		if (StringUtils.isNotEmpty(header.getEncoding())) {
			header.setEncoding(header.getEncoding().replace(";", "").replace(",", ""));
		}

		return header;
	}
}
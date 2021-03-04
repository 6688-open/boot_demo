package com.dj.boot.common.util.converter;

/**
 * 
 * Converter.
 * 
 * @author: Aldwin Su
 */
public interface Converter<S, T> {
	T convert(S value);
}
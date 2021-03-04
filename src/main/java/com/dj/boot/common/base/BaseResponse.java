package com.dj.boot.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;

/**
 * 
 * BaseResponse
 */
@Data
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int SUCCESS_CODE = 200;
	public static final int ERROR_PARAM = 300;
	public static final int ERROR_BUSINESS = 400;
	public static final int ERROR_SYSTEM = 500;
	public static final int ERROR_NOPERMIT = 600;
	public static final int ALREADY_EXIST = 700;
	public static final String DEFAULT_MESSAGE = "服务开小差，请稍后再试！";
	public static final String NOPERMIT_MESSAGE = "权限不足！";
	public static final String SUCCESS_MESSAGE = "SUCCESS！";
	private Integer code;
	private String msg;
	private Integer subCode;
	private String subMsg;
	private T data;
	private Throwable exception;

	public BaseResponse() {
		this.code = 200;
		this.msg = "成功";
	}

	public BaseResponse(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public BaseResponse(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public BaseResponse(Integer code, String msg, T data, Throwable exception) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.exception = exception;
	}

	public BaseResponse(T data) {
		this.data = data;
		this.code = 200;
		this.msg = "成功";
	}



	public boolean isSuccess() {
		return 200 == this.code;
	}



}
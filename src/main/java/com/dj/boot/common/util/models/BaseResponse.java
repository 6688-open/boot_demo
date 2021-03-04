package com.dj.boot.common.util.models;

public class BaseResponse<T> {
	private boolean succeeded;
	private StringBuffer message = new StringBuffer("");
	private T data;
	private int code;

	public BaseResponse() {
		succeeded = true;
	}

	public void appendMessage(String message) {
		this.message.append(message);
	}

	public void appendErrorMessage(String message) {
		setSucceeded(false);
		appendMessage(message);
	}

	public void setErrorMessage(String message) {
		appendErrorMessage(message);
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}

	public StringBuffer getMessage() {
		return message;
	}

	public void setMessage(String message) {
		appendMessage(message);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
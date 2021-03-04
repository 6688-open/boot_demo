package com.dj.boot.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultModel<T> {

	private Integer code = 200;
	private boolean result;
	private String msg = "请求成功";
	private T data;

	
	public ResultModel<T> success(String msg){
		this.msg = msg;
		this.result = true;
		return this;
	}

	public ResultModel<T> success(Integer code, String msg){
		this.code = code;
		this.msg = msg;
		this.result = true;
		return this;
	}

	public ResultModel<T> success( String msg, T data){
		this.msg = msg;
		this.data = data;
		this.result = true;
		return this;
	}

	public ResultModel<T> success(Integer code, String msg, T data){
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.result = true;
		return this;
	}
	
	public ResultModel<T> success(T data){
		this.data = data;
		this.result = true;
		return this;
	}
	
	public ResultModel<T> error(String msg){
		this.code = -1;
		this.msg = msg;
		this.result = false;
		return this;
	}
	
	public ResultModel<T> error(Integer code, String msg){
		this.code = code;
		this.msg = msg;
		this.result = false;
		return this;
	}
	public ResultModel<T> error(Integer code, String msg, T data){
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.result = false;
		return this;
	}
}

package com.dj.boot.common.util.models;

import java.util.List;

public class BaseListResponse<T> extends BaseResponse<List<T>> {
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
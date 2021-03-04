package com.dj.boot.common.util.sso;

public class BPEngineUser extends SSOUser {
	private boolean accessable;
	private String adminCorper;

	public String getAdminCorper() {
		return adminCorper;
	}

	public void setAdminCorper(String adminCorper) {
		this.adminCorper = adminCorper;
	}

	/**
	 * 
	 */
	public BPEngineUser() {
		super();
		accessable = false;
	}

	public boolean isAccessable() {
		return accessable;
	}

	public void setAccessable(boolean accessable) {
		this.accessable = accessable;
	}

}

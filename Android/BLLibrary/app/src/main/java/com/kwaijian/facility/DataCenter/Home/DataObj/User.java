package com.kwaijian.facility.DataCenter.Home.DataObj;

import org.json.JSONObject;

public class User extends JsonObj {

	private boolean isLogin;
	private String uid;
	private String userName;
	private String loginTime;
	private String loginIp;
	private String mobile;
	private String token;

	public User() {
		
	}

	@Override
	public void setData(JSONObject json) {
		super.setData(json);
		uid = getValue("uid");
		userName = getValue("username");
		loginTime = getValue("logintime");
		loginIp = getValue("loginip");
		mobile = getValue("mobile");
		token = getValue("token");
		
		isLogin = true;
	}
	
	public String getUid() {
		return uid;
	}

	public String getUserName() {
		return userName;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public String getMobile() {
		return mobile;
	}

	public String getToken() {
		return token;
	}
	
	public boolean isLogin() {
		return isLogin;
	}
	
}

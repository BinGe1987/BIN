package com.kwaijian.facility.DataCenter.Home.DataObj;

import org.json.JSONObject;

public class JsonObj {
	private JSONObject json;


	public JsonObj() {

	}

	public void setData(JSONObject json) {
		this.json = json;
	}

	public String getValue(String key) {
		try {
			return json.getString(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean contain(String keyword){
		return json.toString().contains(keyword);
	}
	
	
	

}

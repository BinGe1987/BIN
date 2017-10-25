package com.kwaijian.facility.DataCenter.Home.DataObj;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;

import org.json.JSONException;
import org.json.JSONObject;

public class Contacts extends HTData {
	public final static int STATUS_UNDOME = 1;
	public final static int STATUS_DOME = 0;

	public String id;
	public String name;
	public String mobile;
	public String address;
	public String companyId;
	public String department;
	public String post;
	public String fax;
	public String email;
	public String birth;
	public String tel;
	public int status;
	public String remark;

	public Contacts() {
		
	}
	
	@Override
	public void setData(JSONObject json) {
		super.setData(json);
		id = stringByKey("id");
		name = stringByKey("lm_realname");
		mobile = stringByKey("lm_mobile");
		address = stringByKey("lm_address");
		companyId = stringByKey("company_id");
		department = stringByKey("lm_department");
		post = stringByKey("lm_post");
		fax = stringByKey("lm_fax");
		email = stringByKey("lm_email");
		birth = stringByKey("lm_birth");
		tel = stringByKey("lm_tel");
		status = integerByKey("status");
		remark = stringByKey("lm_remark");
	}
	
	public JSONObject contactsToJson(){
		JSONObject json=new JSONObject();
		try {
			json.put("lm_realname", name)
				.put("id", id)
				.put("lm_mobile", mobile)
				.put("lm_address", address)
				.put("company_id", companyId)
				.put("lm_department", department)
				.put("lm_post", post)
				.put("lm_fax", fax)
				.put("lm_email", email)
				.put("lm_birth", birth)
				.put("lm_tel", tel)
				.put("status", status)
				.put("lm_remark", remark);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}


	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getTel() {
		return tel;
	}

	public String getMobile() {
		return mobile;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getId() {
		return id;
	}
	
	
}

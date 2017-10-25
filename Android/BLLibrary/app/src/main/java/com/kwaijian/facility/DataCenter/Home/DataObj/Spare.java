package com.kwaijian.facility.DataCenter.Home.DataObj;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.OldSource.http.HttpConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Spare extends HTData implements Serializable{
	private static final long serialVersionUID = 6956445226524109612L;
	
	public String id;
	public String name;
	public String type;
	public String manufacturer;
	public String model;
	public String[] imageId;
	public String remark;
	public String num;
	public String brand;
	public String ON;
	public String SN;

	public Spare() {
	}

	public Spare(String name, String type, String manufacturer, String model, String[] imageId, String remark) {
		this.name = name;
		this.type = type;
		this.manufacturer = manufacturer;
		this.model = model;
		this.imageId = imageId;
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setData(JSONObject json) {
		super.setData(json);
		id = stringByKey(HttpConst.Sparepart.ID);
		name = stringByKey(HttpConst.Sparepart.NAME);
		type = stringByKey(HttpConst.Sparepart.TYPE);
		manufacturer = stringByKey(HttpConst.Sparepart.MANUFACTURER);
		model = stringByKey(HttpConst.Sparepart.MODEL);
		imageId = stringByKey(HttpConst.Sparepart.IMAGE).split(",");
		remark = stringByKey(HttpConst.Sparepart.REMARK);
		num = stringByKey(HttpConst.Sparepart.COUNT);
		brand = stringByKey(HttpConst.Sparepart.BRAND);
		if (stringByKey("cs_ON")!=null) {
			ON = stringByKey("cs_ON");
		}
		if (stringByKey("cs_SN")!=null) {
			SN = stringByKey("cs_SN");
		}
	}
	
	public JSONObject sparepartToJson(){
		JSONObject json = new JSONObject();
		try {
			json.put(HttpConst.Sparepart.NAME, name)
				.put(HttpConst.Sparepart.ID, id)
				.put(HttpConst.Sparepart.TYPE, type)
				.put(HttpConst.Sparepart.MANUFACTURER, manufacturer)
				.put(HttpConst.Sparepart.MODEL, model)
				.put(HttpConst.Sparepart.REMARK, remark)
				.put(HttpConst.Sparepart.COUNT, num)
				.put(HttpConst.Sparepart.BRAND, brand)
				.put("cs_ON", ON)
				.put("cs_SN", SN);
			if(imageId!=null){
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < imageId.length; i++) {
					jsonArray.put(imageId[i]);
				}
				json.put(HttpConst.Sparepart.IMAGE, jsonArray);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String[] getImageId() {
		return imageId;
	}

	public void setImageId(String[] imageId) {
		this.imageId = imageId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getON() {
		return ON;
	}

	public void setON(String oN) {
		ON = oN;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getNum() {
		return num;
	}

	public String getBrand() {
		return brand;
	}
	
	
}

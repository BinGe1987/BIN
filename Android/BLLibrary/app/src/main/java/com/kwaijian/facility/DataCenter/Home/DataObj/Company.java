package com.kwaijian.facility.DataCenter.Home.DataObj;

import com.kwaijian.facility.BaseClasses.Objects.HTObject;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Company extends HTData implements Serializable {

    private static final long serialVersionUID = -7582220030413305459L;

    public String name;
    public String address;
    public String tel;
    public String type;
    public String fax;
    public String id;
    public String zipcode;
    public String bank;
    public String bankAccount;
    public String taxpayerId;
    public String phone;
    public String createTime;
    public String updateTime;
    public String status;
    public String token;
    public String registeredAddress;

    public List<Facility> facilityList = new ArrayList<>();

    public Company() {
    }

    @Override
    public void onData() {
        createTime = stringByKey("create_time");
        updateTime = stringByKey("update_time");
        token = stringByKey("token");
        status = stringByKey("status");
        id = stringByKey("id");
        type = stringByKey("c_type");
        zipcode = stringByKey("c_zipcode");
        fax = stringByKey("c_fax");
        bank = stringByKey("c_bank");
        bankAccount = stringByKey("c_bankAccount");
        name = stringByKey("c_name");
        address = stringByKey("c_address");
        phone = stringByKey("c_phone");
        tel = stringByKey("c_tel");
        taxpayerId = stringByKey("c_taxpayerId");
        registeredAddress = stringByKey("c_registeredAddress");
    }

    public JSONObject companyToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("c_name", name)
                    .put("id", id)
                    .put("c_address", address)
                    .put("c_tel", tel)
                    .put("c_type", type)
                    .put("c_fax", fax)
                    .put("c_zipcode", zipcode)
                    .put("c_bank", bank)
                    .put("c_bankAccount", bankAccount)
                    .put("c_taxpayerId", taxpayerId)
                    .put("c_phone", phone)
                    .put("c_registeredAddress", registeredAddress);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void setFacilityListData(HTData data) {
        facilityList.clear();
        JSONArray array = data.JSONArrayByKey("list_facility");
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject f = array.getJSONObject(i);
                Facility facility = new Facility();
                facility.setData(f);
                facilityList.add(facility);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getFax() {
        return fax;
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

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public String getToken() {
        return token;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getBank() {
        return bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }


}

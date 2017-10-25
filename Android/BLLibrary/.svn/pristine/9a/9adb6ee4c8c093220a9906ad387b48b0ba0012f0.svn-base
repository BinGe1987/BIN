package com.kwaijian.facility.DataCenter.BaseClasses;


import com.kwaijian.facility.BaseClasses.Objects.HTJSONObject;

import org.json.JSONObject;

/**
 * Created by BinGe on 2017/8/31.
 * 此类为数据中心所有数据的基本类型
 */

public class HTData extends HTJSONObject {

    public HTData() {

    }

    public HTData(String jsonString) {
        super(jsonString);
    }

    public HTData(JSONObject jsonObject) {
        super(jsonObject);
    }

    public void setData(String jsonString) {
        setJSONString(jsonString);
        onData();
    }

    public void setData(JSONObject jsonObject) {
        setJSONObject(jsonObject);
        onData();
    }

    public void onData() {

    }

    public boolean isDataNormal() {
        return integerByKey("errCode", -1) == 0;
    }

    public int errCode() {
        return integerByKey("errCode", -1);
    }

    public String errInfo() {
        return stringByKey("errMsg", "unknown error!");
    }

}

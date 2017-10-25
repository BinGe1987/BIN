package com.kwaijian.facility.DataCenter.Home;


import com.kwaijian.facility.BaseClasses.Objects.HTJSONObject;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.Home.Facility.FacilityData;
import com.kwaijian.facility.DataCenter.Home.Repair.RepairData;
import com.kwaijian.facility.DataCenter.Home.Server.ServerData;
import com.kwaijian.facility.OldSource.tools.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LooooG on 2017/9/15.
 * 首页数据
 */
public class HomeData extends HTData {

    /**
     * 服务列表数据
     */
    public ServerData serverData = new ServerData();
    public RepairData repairData = new RepairData();
    public FacilityData facilityData = new FacilityData();

    public void onData() {

    }


    public void setServerData(HTData data) {
        serverData.setData(data.getJSONObject());
    }

    public void setRepairData(HTData data) {
        repairData.setData(data.getJSONObject());
    }

    public void setFacilityData(HTData data) {
        facilityData.setData(data.getJSONObject());
    }

}

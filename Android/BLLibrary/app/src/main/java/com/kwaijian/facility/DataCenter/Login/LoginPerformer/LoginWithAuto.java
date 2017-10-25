package com.kwaijian.facility.DataCenter.Login.LoginPerformer;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.UI.Launch.LaunchActivity;
import com.kwaijian.facility.Utils.Localize.Saver;
import com.kwaijian.facility.Utils.Net.Http;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinGe on 2017/9/14.
 * 自动登录处理器
 */

public class LoginWithAuto extends LoginPerformer<String> {
    @Override
    public HTData performOperation(String operation, String params, DataCenter.Callback callback) {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("interface", "checkLogin");
        JSONObject response = Http.post(HttpConst.Request.URL, requestParams);
        HTData data = new HTData(response);
        return data;
    }
}

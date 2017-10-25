package com.kwaijian.facility.DataCenter.Login.LoginPerformer;

import com.kwaijian.facility.BaseClasses.Objects.HTJSONObject;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.Utils.Localize.Saver;
import com.kwaijian.facility.Utils.Net.Http;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinGe on 2017/9/4.
 * 手机登录
 */

public class LoginWithPwd extends LoginPerformer<Map<String, Object>> {

    @Override
    public HTData performOperation(String operation, Map<String, Object> params, DataCenter.Callback callback) {
        params = mergeLoginParams(params);
        JSONObject json = new JSONObject(params);

        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("interface", "login");
        requestParams.put("param", json);

        Http.removeCookie();
        JSONObject response = Http.post(HttpConst.Request.URL, requestParams);
        HTData data = new HTData(response);
        return data;
    }

}

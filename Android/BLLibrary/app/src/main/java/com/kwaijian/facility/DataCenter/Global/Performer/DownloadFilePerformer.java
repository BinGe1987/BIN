package com.kwaijian.facility.DataCenter.Global.Performer;


import com.kwaijian.facility.BaseClasses.OS.SystemInfo;
import com.kwaijian.facility.BaseClasses.Objects.HTJSONObject;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.BaseClasses.HTOperationPerformer;
import com.kwaijian.facility.DataCenter.DataCenter;
import com.kwaijian.facility.Utils.Net.Http;

import org.json.JSONObject;

/**
 * Created by BinGe on 2017/10/24.
 */

public class DownloadFilePerformer implements HTOperationPerformer<String> {
    @Override
    public HTData performOperation(String operation, String params, DataCenter.Callback callback) {
        HTData data = new HTData();
        if (params != null && params.startsWith("http")) {
            String path = Http.downloadImage(params, SystemInfo.ImageCacheDir);
            if (path != null) {
                JSONObject dataJson = HTJSONObject.make("path", path);
                JSONObject jsonObject = HTJSONObject.make("status", "success", "data", dataJson);
                data.setData(jsonObject);
            }
        }
        return data;
    }

    @Override
    public boolean isAsynchronous() {
        return false;
    }
}

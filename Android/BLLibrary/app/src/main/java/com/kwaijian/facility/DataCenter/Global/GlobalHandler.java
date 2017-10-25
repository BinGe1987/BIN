package com.kwaijian.facility.DataCenter.Global;


import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.BaseClasses.HTDataHandler;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.Global.Performer.DownloadFilePerformer;

/**
 * Created by BinGe on 2017/10/24.
 */

public class GlobalHandler extends HTDataHandler {

    public GlobalHandler() {
        // 文件下载
        bindOperation(Operations.Global.DownloadImage, new DownloadFilePerformer());
    }

    @Override
    public HTData getData() {
        return null;
    }
}

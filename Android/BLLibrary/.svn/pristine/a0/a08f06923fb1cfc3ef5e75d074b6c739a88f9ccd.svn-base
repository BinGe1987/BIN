package com.kwaijian.facility.BaseClasses.Objects;

import android.util.Log;

/**
 * Created by BinGe on 2017/8/31.
 * 预留空的父类，方便后期添加公用功能
 */

public class HTObject {

    private boolean logSwitch = false;

    protected void openLog(boolean open) {
        logSwitch = open;
    }

    protected void log(String log) {
        if (logSwitch) {
            Log.d(this.getClass().getSimpleName() + "Log", log);
        }
    }

}

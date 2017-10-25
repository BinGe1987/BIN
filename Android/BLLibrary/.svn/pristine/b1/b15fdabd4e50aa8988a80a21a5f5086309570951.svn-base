package com.kwaijian.facility.DataCenter.Config;


import com.kwaijian.facility.DataCenter.BaseClasses.HTDataHandler;
import com.kwaijian.facility.DataCenter.Home.HomeHandler;
import com.kwaijian.facility.DataCenter.User.UserHandler;

/**
 * Created by BinGe on 2017/9/12.
 * 数据中心获取相应数据
 */

public enum  DataType {
    /**
     * 用户数据
     */
    UserData(UserHandler.class),

    /**
     * 首页数据
     */
    HomeData(HomeHandler.class);

    private Class<? extends HTDataHandler> handlerClass;

    DataType(Class<? extends HTDataHandler> cls) {
        handlerClass = cls;
    }

    public Class<? extends HTDataHandler> getHandlerClass() {
        return handlerClass;
    }
}


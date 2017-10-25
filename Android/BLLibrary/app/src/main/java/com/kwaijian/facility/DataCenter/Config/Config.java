package com.kwaijian.facility.DataCenter.Config;


import com.kwaijian.facility.DataCenter.BaseClasses.HTDataHandler;
import com.kwaijian.facility.DataCenter.Global.GlobalHandler;
import com.kwaijian.facility.DataCenter.Home.HomeHandler;
import com.kwaijian.facility.DataCenter.Login.LoginHandler;
import com.kwaijian.facility.DataCenter.User.UserHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2017/9/29.
 * 配置数据中心的DataHandler数据
 */

public class Config {

    /**
     * 这里配置DataCenter初始化DataHandler的列表
     */
    public static final List<Class<? extends HTDataHandler>> HANDLERS =
            new ArrayList<Class<? extends HTDataHandler>>() {
        {
            /**
             * 公共处理器
             */
            add(GlobalHandler.class);
            /**
             * 登录处理器
             */
            add(LoginHandler.class);
            /**
             * 用户处理器
             */
            add(UserHandler.class);
            /**
             * 首页处理器
             */
            add(HomeHandler.class);
        }
    };
}

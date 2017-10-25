package com.kwaijian.facility.DataCenter.Login;

import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.BaseClasses.HTDataHandler;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.DataCenter.Login.LoginPerformer.LoginOut;
import com.kwaijian.facility.DataCenter.Login.LoginPerformer.LoginWithPwd;
import com.kwaijian.facility.DataCenter.Login.LoginPerformer.LoginWithAuto;

/**
 * Created by BinGe on 2017/9/4.
 * 不同的登录方式返回对应的登录实例
 */

public class LoginHandler extends HTDataHandler {

    public LoginHandler() {
        //登录
        bindOperation(Operations.Login.LoginWithPwd, new LoginWithPwd());
        //自动登录
        bindOperation(Operations.Login.LoginWithAuto, new LoginWithAuto());
        //退出登录
        bindOperation(Operations.Login.LoginOut, new LoginOut());
    }

    @Override
    public HTData getData() {
        return null;
    }
}

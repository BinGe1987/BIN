package com.kwaijian.facility.DataCenter.User;

import android.widget.Switch;

import com.kwaijian.facility.BaseClasses.Objects.HTJSONObject;
import com.kwaijian.facility.Constants.EventConst;
import com.kwaijian.facility.DataCenter.BaseClasses.HTData;
import com.kwaijian.facility.DataCenter.BaseClasses.HTDataHandler;
import com.kwaijian.facility.DataCenter.BaseClasses.HTDataParser;
import com.kwaijian.facility.DataCenter.Config.Operations;
import com.kwaijian.facility.EventCenter.EventBus;
import com.kwaijian.facility.Utils.Localize.Saver;

import org.json.JSONObject;

/**
 * Created by BinGe on 2017/9/4.
 * 不同的登录方式返回对应的登录实例
 */

public class UserHandler extends HTDataHandler implements HTDataParser<UserData> {

    /**
     * 用户的数据
     */
    private UserData data = new UserData();

    public UserHandler() {
        //处理自动登录的数据
        bindDataParser(Operations.Login.LoginWithPwd, this);
        //处理自动登录的数据
        bindDataParser(Operations.Login.LoginWithAuto, this);
        //处理退出登录操作
        bindDataParser(Operations.Login.LoginOut, this);
    }

    @Override
    public HTData getData() {
        return data;
    }

    @Override
    public UserData parse(String operation, HTData data) {
        switch (operation) {
            case Operations.Login.LoginOut:
                this.data.setData("");
                EventBus.getInstance().postEvent(EventConst.Login.OnLoggedOut, this.data); // 成功登录命令通知
                break;
            case Operations.Login.LoginWithPwd:
            case Operations.Login.LoginWithAuto:
                JSONObject json = data.getJSONObject();
                this.data.setData(json);
                if (this.data.isLogin()) {
                    EventBus.getInstance().postEvent(EventConst.Login.OnLoggedIn, this.data); // 成功登录命令通知
                }
                break;
        }

        try {

        } catch (Exception e) {

        }
        return this.data;
    }
}
